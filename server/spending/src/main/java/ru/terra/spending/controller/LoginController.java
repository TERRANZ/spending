package ru.terra.spending.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.terra.spending.ResponceUtils;
import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.dto.LoginDTO;
import ru.terra.spending.engine.UsersEngine;
import flexjson.JSONSerializer;

@Controller
public class LoginController
{
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Inject
	private UsersEngine le;

	@Resource(name = "passwordEncoder")
	private PasswordEncoder passwordEncoder;

	private HttpURLConnection prepareConnection(URL url) throws IOException
	{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setConnectTimeout(Integer.MAX_VALUE);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		conn.setRequestProperty("Referer", "http://localhost:8080/spending/login");
		conn.setRequestProperty("Accept",
				"text/html, application/xml;q=0.9, application/xhtml+xml, image/png, image/webp, image/jpeg, image/gif, image/x-xbitmap, */*;q=0.1");
		conn.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en;q=0.8");
		conn.setRequestProperty("User-Agent", "Opera/9.80 (X11; Linux x86_64; U; ru) Presto/2.10.229 Version/11.60");
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		return conn;
	}

	private String prepareData(String user, String pass) throws UnsupportedEncodingException
	{
		String data = URLEncoder.encode("j_username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
		data += "&" + URLEncoder.encode("j_password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
		return data;
	}

	@RequestMapping(value = URLConstants.DoJson.LOGIN_DO_LOGIN_JSON, method = RequestMethod.POST)
	public ResponseEntity<String> mobileLoginPost(HttpServletRequest request, @RequestParam(required = true, defaultValue = "") String user,
			@RequestParam(required = true, defaultValue = "") String pass)
	{
		return mobileLogin(request, user, pass);
	}

	@RequestMapping(value = URLConstants.DoJson.LOGIN_DO_LOGIN_JSON, method = RequestMethod.GET)
	public ResponseEntity<String> mobileLoginGet(HttpServletRequest request, @RequestParam(required = true, defaultValue = "") String user,
			@RequestParam(required = true, defaultValue = "") String pass)
	{
		return mobileLogin(request, user, pass);
	}

	public ResponseEntity<String> mobileLogin(HttpServletRequest request, @RequestParam(required = true, defaultValue = "") String user,
			@RequestParam(required = true, defaultValue = "") String pass)
	{
		LoginDTO ret = new LoginDTO();
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			final String address = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ URLConstants.Pages.SPRING_LOGIN;
			URL url = new URL(address);
			HttpURLConnection conn = prepareConnection(url);
			conn.connect();

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(prepareData(user, pass));
			wr.flush();

			String location = conn.getHeaderField("Location");
			String headerName = null;
			String cookie = "";
			for (int i = 1; (headerName = conn.getHeaderFieldKey(i)) != null; i++)
			{
				if (headerName.equals("Set-Cookie"))
				{
					cookie = conn.getHeaderField(i);
					headers.add("Set-Cookie", cookie);
				}
			}

			if (location != null)
			{
				if (location.contains("banned"))
				{
					// user banned
				}
				else if (location.contains("/login"))
				{
					// user invalid
					ret.logged = false;
				}
				else
				{
					// user valid!
					// code 200
					logger.info("cookie = " + cookie);
					ret.session = cookie.length() > 0 ? cookie.substring(cookie.indexOf("JSESSIONID=") + 11, cookie.indexOf(";")) : "";
					ret.logged = true;
				}
			}
			else
			{
				// user valid!
				// code 200
				logger.info("cookie = " + cookie);
				ret.session = cookie.length() > 0 ? cookie.substring(cookie.indexOf("JSESSIONID=") + 11, cookie.indexOf(";")) : "";
				ret.logged = true;
			}
			try
			{
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null)
				{
					// logger.info("HTTP POST respone: " + line);
				}
				wr.close();
				rd.close();
			} catch (IOException e)
			{
				ret.session = "";
				ret.logged = false;
			}
		} catch (IOException e)
		{
			logger.info("Exception while reading responce from server " + e.getMessage());
			// e.printStackTrace();
		}
		String json = new JSONSerializer().serialize(ret);
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.DoJson.LOGIN_DO_LOGIN_JSON, method = RequestMethod.POST)
	public ResponseEntity<String> register(HttpServletRequest request)
	{
		LoginDTO ret = new LoginDTO();
		String login = request.getParameter("login");
		if (login != null && le.findUserByName(login) == null)
		{
			String pass = request.getParameter("pass");
			if (login != null && pass != null)
			{
				Integer retId = le.registerUser(login, pass);
				ret.logged = true;
				ret.id = retId;
				User u = le.getUser(retId);
				u.setPassword(passwordEncoder.encodePassword(pass, u.getId()));
				le.saveUser(u);
			}
			else
			{
				ret.logged = false;
			}
		}
		else
		{
			ret.logged = false;
			ret.message = "User already exists";
		}
		String json = new JSONSerializer().serialize(ret);
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.Pages.LOGIN, method = RequestMethod.GET)
	public String login(Locale locale, Model model)
	{
		return URLConstants.Views.LOGIN;
	}
}

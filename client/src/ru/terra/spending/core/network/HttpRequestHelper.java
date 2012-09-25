package ru.terra.spending.core.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import ru.terra.spending.R;
import ru.terra.spending.util.Logger;
import android.app.Activity;

//Синхронные вызовы rest сервисов, вызывать только внутри асинхронной таски
public class HttpRequestHelper
{
	private static final int IMAGE_MAX_SIZE = 1024;
	private final String TAG = "HttpRequestHelper";
	private HttpClient hc;
	private String baseAddress = "";
	private Activity cntx;

	public HttpRequestHelper(Activity c)
	{
		this.cntx = c;
		if (c != null)
			this.baseAddress = c.getString(R.string.server_address);
		else
			Logger.i(TAG, "activity is null");
		hc = new DefaultHttpClient();
		hc.getParams().setParameter("http.protocol.content-charset", "UTF-8");
	}

	public String runSimpleJsonRequest(String uri) throws UnableToLoginException
	{
		HttpGet httpGet = new HttpGet(baseAddress + uri);
		return runRequest(httpGet);
	}

	private String runRequest(HttpUriRequest httpRequest)
	{
		StringBuilder builder = new StringBuilder();
		try
		{
			// httpRequest.setHeader("Cookie", new UserProvider(cntx).getSessionId());
			HttpResponse response = null;
			try
			{
				response = hc.execute(httpRequest);
			} catch (ConnectException e)
			{
				Logger.w("HttpRequestHelper", "Connect exception " + e.getMessage());
				return null;
			} catch (IllegalStateException e)
			{
				Logger.w("HttpRequestHelper", "IllegalStateException " + e.getMessage());
				return null;
			}
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			// Logger.i("HttpRequestHelper", "Received status code " + statusCode);
			if (HttpStatus.SC_OK == statusCode)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line;
				while ((line = reader.readLine()) != null)
				{
					builder.append(line);
				}
			}
			else if (HttpStatus.SC_FORBIDDEN == statusCode)
			{
				Logger.i("HttpRequestHelper", "Received status code FORBIDDEN! invocating relogin and doing runRequest");
				// if (new UserProvider(cntx).doStoredLogin())
				// {
				// return runRequest(httpRequest);
				// }
				// else
				// {
				// Logger.w(TAG, "Unable to login, need to enter valid login and password");
				// throw new UnableToLoginException();
				// }
			}
			else
			{
				Logger.w("HttpRequestHelper", statusLine.toString() + "at" + httpRequest.getURI());
			}
		} catch (ConnectException e)
		{
			Logger.w("HttpRequestHelper", "runRequest", e);
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e)
		{
			Logger.w("HttpRequestHelper", "runRequest", e);
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			Logger.w("HttpRequestHelper", "runRequest", e);
			e.printStackTrace();
			return null;
		}
		return builder.toString();
	}

	public String runJsonRequest(String uri, NameValuePair... params) 
	{
		HttpPost request = new HttpPost(baseAddress + uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (int i = 0; i < params.length; ++i)
		{
			nameValuePairs.add(params[i]);
		}

		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		UrlEncodedFormEntity entity;
		try
		{
			entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			entity.setContentType("appplication/x-www-form-urlencoded");
			request.setEntity(entity);

			return runRequest(request);
		} catch (UnsupportedEncodingException e)
		{
			Logger.w("HttpRequestHelper", "Failed to form request content" + e.getMessage());
			return "";
		}
	}

	// public String uploadImage(String title, String fileName, String type, String id) throws UnableToLoginException
	// {
	// MultipartEntity reqEntity = new MultipartEntity();
	// // TODO: mb we can do it in another way?
	// HttpPost post = new HttpPost(baseAddress + "/icity/uploadfile");
	// try
	// {
	// Logger.i("uploadImage", "Starting to upload image: " + title + " with type: " + type + " : " + id);
	// reqEntity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
	// reqEntity.addPart("return", new StringBody("json"));
	// reqEntity.addPart(type, new StringBody(id));
	// } catch (UnsupportedEncodingException e)
	// {
	// e.printStackTrace();
	// }
	// reqEntity.addPart("fileData", new FileBody(new File(resizeImage(fileName))));
	//
	// post.setEntity(reqEntity);
	// return runRequest(post);
	// }
	//
	// public Bitmap getImageBitmap(String localUrl, ImageType type)
	// {
	// if (localUrl == null)
	// return getNoPhotoBitmap(type);
	//
	// String url = baseAddress + localUrl;
	// switch (type)
	// {
	// case thumbnail:
	// url += "?width=50";
	// break;
	// case preview:
	// url += "?width=100";
	// break;
	// case avatar:
	// url += "?width=300";
	// break;
	// case full:
	// break;
	// default:
	// return null;
	// }
	//
	// Bitmap b;
	// try
	// {
	// b = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
	// } catch (MalformedURLException e)
	// {
	// Logger.w(TAG, "Failed to download image", e);
	// return null;
	// } catch (FileNotFoundException e)
	// {
	// Logger.w("HttpRequestHelper", "getImageBitmap file not found exception" + e.getMessage());
	// return getNoPhotoBitmap(type);
	// } catch (IOException e)
	// {
	// Logger.w(TAG, "Failed to download image", e);
	// return null;
	// } catch (Exception e)
	// {
	// Logger.w(TAG, "Failed to download image", e);
	// return null;
	// }
	//
	// if (b != null)// request.addHeader("Accept",
	//
	// b.setDensity(Bitmap.DENSITY_NONE);
	// return b == null ? getNoPhotoBitmap(type) : b;
	// }
	//
	// private Bitmap getNoPhotoBitmap(ImageType type)
	// {
	// Logger.i("HttpRequestHelper", "Returning stub bitmap");
	// return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.no_photo2);
	// }
	//
	// public Long createContentForObject(String title, String contentData, String contentType, String objectId, String category, String signalType)
	// throws UnableToCreateContentException, UnableToLoginException
	// {
	// OperationResultDTO res = new com.google.gson.Gson().fromJson(
	// runJsonRequest("/icity/do.createcontent.json", null, new BasicNameValuePair("iObject", objectId), new BasicNameValuePair(
	// "contentType", contentType), new BasicNameValuePair("title", title), new BasicNameValuePair("contentData", contentData),
	// new BasicNameValuePair("published", "on"), new BasicNameValuePair("category", category), new BasicNameValuePair("signaltype",
	// signalType)), OperationResultDTO.class);
	// if (res != null)
	// {
	// if (res.error != null)
	// {
	// Logger.w(TAG, "Failed to create content of type " + contentType + " " + res.error);
	// throw new UnableToCreateContentException(res.error);
	// }
	// else
	// return Long.parseLong(res.createdId);
	// }
	// return 0L;
	// }
	//
	// public Long createContentOnMap(String title, String contentData, String contentType, String category, String signalType, Double lon, Double
	// lat)
	// throws UnableToCreateContentException, UnableToLoginException
	// {
	// String coords = "{\"geometry\" : { \"type\" : \"Point\", \"coordinates\" : [ " + lon.toString() + " , " + lat.toString() + " ]}}";
	// OperationResultDTO res = new com.google.gson.Gson().fromJson(
	// runJsonRequest("/icity/do.createcontent.json", null, new BasicNameValuePair("contentType", contentType), new BasicNameValuePair(
	// "title", title), new BasicNameValuePair("contentData", contentData), new BasicNameValuePair("published", "on"),
	// new BasicNameValuePair("category", category), new BasicNameValuePair("signaltype", signalType), new BasicNameValuePair(
	// "coordsJSON", coords)), OperationResultDTO.class);
	// if (res != null)
	// {
	// if (res.error != null)
	// {
	// Logger.w(TAG, "Failed to create content of type " + contentType + " " + res.error);
	// throw new UnableToCreateContentException(res.error);
	// }
	// return Long.parseLong(res.createdId);
	// }
	// return 0L;
	// }
	//
	// private String resizeImage(String path)
	// {
	// BitmapFactory.Options bfOptions = new BitmapFactory.Options();
	// bfOptions.inDither = false; // Disable Dithering mode
	// bfOptions.inPurgeable = true; // Tell to gc that whether it needs free memory, the Bitmap can be cleared
	// bfOptions.inInputShareable = true; // Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used
	// // in the future
	// bfOptions.inTempStorage = new byte[32 * 1024];
	//
	// File file = new File(path);
	// FileInputStream fs = null;
	// try
	// {
	// fs = new FileInputStream(file);
	// } catch (FileNotFoundException e)
	// {
	// Logger.w(TAG, "Error while reading " + path + " :" + e.getMessage());
	// e.printStackTrace();
	// }
	// Bitmap bm = null;
	//
	// try
	// {
	// if (fs != null)
	// bm = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
	// } catch (IOException e)
	// {
	// Logger.w(TAG, "Error while reading " + path + " :" + e.getMessage());
	// e.printStackTrace();
	// } finally
	// {
	// if (fs != null)
	// {
	// try
	// {
	// fs.close();
	// } catch (IOException e)
	// {
	// Logger.w(TAG, "Error while closing file " + path + " :" + e.getMessage());
	// e.printStackTrace();
	// }
	// }
	// }
	// FileOutputStream out;
	// File root = new File(Environment.getExternalStorageDirectory() + File.separator + "icity" + File.separator);
	// root.mkdirs();
	// try
	// {
	// int divScale = 4;
	// if (cntx != null)
	// divScale = PreferenceManager.getDefaultSharedPreferences(cntx).getInt(cntx.getString(R.string.upload_image_size), 1);
	// out = new FileOutputStream(new File(root, "photo.jpg"));
	// Bitmap outBmp = Bitmap.createScaledBitmap(bm, bm.getWidth() / divScale, bm.getHeight() / divScale, false);
	// outBmp.compress(Bitmap.CompressFormat.JPEG, 65, out);
	// outBmp.recycle();
	// } catch (FileNotFoundException e)
	// {
	// Logger.w(TAG, "Error while saving " + path + " :" + e.getMessage());
	// e.printStackTrace();
	// }
	// return root.getPath() + "/photo.jpg";
	// }
	//
	// public String socialLogin(String token) throws UnableToLoginException
	// {
	// runJsonRequest("/icity/social_login", null, new BasicNameValuePair("token", token));
	//
	// return "";
	// }

}
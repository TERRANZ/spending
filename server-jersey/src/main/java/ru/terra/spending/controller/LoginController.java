package ru.terra.spending.controller;

import com.sun.jersey.api.core.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.server.controller.AbstractResource;
import ru.terra.server.dto.LoginDTO;
import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.engine.CaptchaEngine;
import ru.terra.spending.engine.UsersEngine;
import ru.terra.spending.engine.YandexCaptcha;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path(URLConstants.Login.LOGIN)
public class LoginController extends AbstractResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UsersEngine usersEngine = new UsersEngine();
    private CaptchaEngine captchaEngine = new YandexCaptcha();


    @GET
    @Path(URLConstants.Login.LOGIN_DO_LOGIN_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginDTO login(@Context HttpContext hc,
                          @QueryParam(URLConstants.Login.LOGIN_PARAM_USER) String user,
                          @QueryParam(URLConstants.Login.LOGIN_PARAM_PASS) String pass) {
        logger.info("User requests login with user = " + user + " and pass = " + pass);

        LoginDTO ret = new LoginDTO();
        ret.logged = false;
        if (user != null && pass != null) {
            User u = usersEngine.login(user, pass);
            if (u != null) {
                ret.id = u.getId();
                ret.session = sessionsHolder.registerUserSession(u);
                ret.logged = true;
            } else {
                ret.message = "user not found";
            }
        } else {
            ret.message = "invalid parameters";
        }

        return ret;
    }

    @GET
    @Path(URLConstants.Login.LOGIN_DO_REGISTER_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginDTO reg(@Context HttpContext hc,
                        @QueryParam(URLConstants.Login.LOGIN_PARAM_USER) String user,
                        @QueryParam(URLConstants.Login.LOGIN_PARAM_PASS) String pass,
                        @QueryParam(URLConstants.Login.LOGIN_PARAM_CAPTCHA) String captcha,
                        @QueryParam(URLConstants.Login.LOGIN_PARAM_CAPVAL) String capval) {
        logger.info("User requests reg with user = " + user + " and pass = " + pass);

        LoginDTO ret = new LoginDTO();
        if (user != null && usersEngine.findUserByName(user) == null) {
            if (user != null && pass != null) {
                if (captchaEngine.checkCaptcha(capval, captcha)) {
                    Integer retId = usersEngine.registerUser(user, pass);
                    ret.logged = true;
                    ret.id = retId;
                } else {
                    ret.logged = false;
                    ret.message = "Invalid captcha";
                }
            } else {
                ret.logged = false;
            }
        } else {
            ret.logged = false;
            ret.message = "User already exists";
        }
        return ret;
    }
}

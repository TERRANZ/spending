package ru.terra.spending.controller;


import com.sun.jersey.api.core.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.server.controller.AbstractResource;
import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.dto.captcha.CaptchDTO;
import ru.terra.spending.engine.CaptchaEngine;
import ru.terra.spending.engine.YandexCaptcha;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path(URLConstants.Captcha.CAPTCHA)
public class CaptchaController extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CaptchaEngine captchaEngine = new YandexCaptcha();

    @GET
    @Path(URLConstants.Captcha.CAP_GET)
    public CaptchDTO get(@Context HttpContext hc) {
        return captchaEngine.getCaptcha();
    }
}

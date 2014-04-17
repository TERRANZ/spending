package ru.terra.spending.engine;


import ru.terra.spending.dto.captcha.CaptchDTO;

public interface CaptchaEngine
{
	public CaptchDTO getCaptcha();

	public Boolean checkCaptcha(String val, String cid);
}

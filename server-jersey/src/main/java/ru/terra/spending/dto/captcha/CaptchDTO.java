package ru.terra.spending.dto.captcha;

public class CaptchDTO extends CaptchaCommonDTO
{
	public String image = "";

	@Override
	public String toString()
	{
		return "CaptchDTO [image=" + image + ", cid=" + cid + ", text=" + text + "]";
	}
}

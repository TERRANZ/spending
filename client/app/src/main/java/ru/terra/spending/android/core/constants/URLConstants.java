package ru.terra.spending.android.core.constants;

public class URLConstants {

    public class DoJson {
        public static final String DO_LIST = "do.list.json";
        public static final String DO_GET = "do.get.json";
        public static final String DO_DEL = "do.delete.json";
    }

    public class Login {
        public static final String LOGIN = "/login/";
        public static final String LOGIN_DO_LOGIN_JSON = "do.login.json";
        public static final String LOGIN_DO_REGISTER_JSON = "do.register.json";
        public static final String LOGIN_PARAM_USER = "user";
        public static final String LOGIN_PARAM_PASS = "pass";
        public static final String LOGIN_PARAM_CAPTCHA = "captcha";
        public static final String LOGIN_PARAM_CAPVAL = "capval";
    }


    public class MobileTransactions {
        public static final String MOBILE_TRANSACTIONS = "/mobiletransaction/";
        public static final String DO_REGISTER = "do.transaction.register.json";
        public static final String DO_LIST_FROMDATE_JSON = "do.list.fromdate.json";
        public static final String PARAM_TYPE = "type";
        public static final String PARAM_MONEY = "money";
        public static final String PARAM_DATE = "date";
    }

    public class Types {
        public static final String TYPES = "/types/";
    }

    public class Captcha {
        public static final String CAPTCHA = "/captcha/";
        public static final String CAP_GET = "do.get.json";
    }

    public class UI {
        public static final String UI = "/ui/";
        public static final String MAIN = "main";
        public static final String LOGIN = "login";
        public static final String REG = "reg";
        public static final String TRANSACTIONS = "transactions";
    }

    public class Resources {
        public static final String RESOURCES = "/resources/";
    }

    public class Yandex {
        public static final String PARAM_KEY = "$key";
        public static final String PARAM_CID = "$id";
        public static final String PARAM_CVAL = "$val";
        public static final String CAPTCHA_GET_URL = "http://cleanweb-api.yandex.ru/1.0/get-captcha?key="
                + PARAM_KEY;
        public static final String CAPTCHA_CHECK_URL = "http://cleanweb-api.yandex.ru/1.0/check-captcha?key="
                + PARAM_KEY + "&captcha=" + PARAM_CID + "&value=" + PARAM_CVAL;
    }
}

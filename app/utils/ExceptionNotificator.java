package utils;

import play.Logger;

public class ExceptionNotificator {

    private static final Logger.ALogger log = Logger.of("application");

    public static void notify(Throwable e) {
        log.error("Exception has happend: " + e.getMessage());
    }

    public static void notify(String requestParams) {
        log.debug("request params: " + requestParams);
    }
}

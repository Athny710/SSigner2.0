package org.oefa.com.ssigner.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oefa.com.ssigner.domain.util.Log;

public class LogUtil {
    public static void setInfo(Log log) {
        Logger logger = LogManager.getLogger(log.getClassName());
        logger.info(log.getMessage());
    }

    public static void setWarn(Log log) {
        Logger logger = LogManager.getLogger(log.getClassName());
        logger.warn(log.getMessage());
    }

    public static void setError(Log log) {
        Logger logger = LogManager.getLogger(log.getClassName());
        String message = log.getMessage() + "\n" + ExceptionUtils.getStackTrace(log.getException());
        logger.error(message);
    }
}

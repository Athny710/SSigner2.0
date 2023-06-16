package org.oefa.com.ssigner.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("_________________________________");
        System.out.println(ExceptionUtils.getStackTrace(e));
    }
}

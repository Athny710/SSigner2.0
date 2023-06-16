package org.oefa.com.ssigner.domain.util;

public class Log {

    private String message;
    private String className;
    private Exception exception;

    public Log(String message, String className) {
        this.message = message;
        this.className = className;
    }

    public Log(String message, String className, Exception exception) {
        this.message = message;
        this.className = className;
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}

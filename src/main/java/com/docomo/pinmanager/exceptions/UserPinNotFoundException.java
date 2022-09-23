/**
 *
 */
package com.docomo.pinmanager.exceptions;

/**
 * @author virupaksha.kuruva
 *
 */
public class UserPinNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ErrorInfo errorInfo;

    public UserPinNotFoundException(int errorCode, String errorMessage) {
        super();
        this.errorInfo = new ErrorInfo(errorMessage, errorCode);
    }

    public UserPinNotFoundException() {
        super();

    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

}

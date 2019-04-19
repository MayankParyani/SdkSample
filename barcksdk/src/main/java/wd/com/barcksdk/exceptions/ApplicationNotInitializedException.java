/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:21 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:17 PM
 *
 */

package wd.com.barcksdk.exceptions;

import wd.com.barcksdk.crashlytics.exception.CrashReporterException;

public class ApplicationNotInitializedException extends CrashReporterException {

    static final long serialVersionUID = 1;

    /**
     * Constructs a Exception with no additional information.
     */
    public ApplicationNotInitializedException() {
        super();
    }

    /**
     * Constructs a Exception with a message.
     *
     * @param message A String to be returned from getMessage.
     */
    public ApplicationNotInitializedException(String message) {
        super(message);
    }

    /**
     * Constructs a Exception with a message and inner error.
     *
     * @param message   A String to be returned from getMessage.
     * @param throwable A Throwable to be returned from getCause.
     */
    public ApplicationNotInitializedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a Exception with an inner error.
     *
     * @param throwable A Throwable to be returned from getCause.
     */
    public ApplicationNotInitializedException(Throwable throwable) {
        super(throwable);
    }
}
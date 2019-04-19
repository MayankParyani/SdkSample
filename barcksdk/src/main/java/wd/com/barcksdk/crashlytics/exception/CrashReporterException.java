/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:23 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:17 PM
 *
 */

package wd.com.barcksdk.crashlytics.exception;

/**
 * Represents an error condition specific to the Crash Reporter for Android.
 */
public class CrashReporterException extends RuntimeException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a new CrashReporterException.
     */
    public CrashReporterException() {
        super();
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param message the detail message of this exception
     */
    public CrashReporterException(String message) {
        super(message);
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param format the format string (see {@link java.util.Formatter#format})
     * @param args   the list of arguments passed to the formatter.
     */
    public CrashReporterException(String format, Object... args) {
        this(String.format(format, args));
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param message   the detail message of this exception
     * @param throwable the cause of this exception
     */
    public CrashReporterException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param throwable the cause of this exception
     */
    public CrashReporterException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        // Throwable.toString() returns "CrashReporterException:{message}". Returning just "{message}"
        // should be fine here.
        return getMessage();
    }
}

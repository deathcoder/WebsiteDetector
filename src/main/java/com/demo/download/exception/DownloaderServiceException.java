package com.demo.download.exception;

/**
 * Created by davide on 21/10/16.
 */
public class DownloaderServiceException extends Exception {
    public DownloaderServiceException( final String message, final Throwable cause ) {
        super( message, cause );
    }
}

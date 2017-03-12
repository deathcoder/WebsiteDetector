package com.demo.download;

import com.demo.download.exception.DownloaderServiceException;

/**
 * Created by davide on 20/10/16.
 */
public interface DownloaderService {
    String download( String url ) throws DownloaderServiceException;
}

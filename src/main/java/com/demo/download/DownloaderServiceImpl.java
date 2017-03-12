package com.demo.download;

import com.demo.download.exception.DownloaderServiceException;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by davide on 21/10/16.
 */
public class DownloaderServiceImpl implements DownloaderService {
    private static Logger logger = LoggerFactory.getLogger( DownloaderServiceImpl.class );

    private UrlFixer urlFixer;

    @Override
    public String download( final String url ) throws DownloaderServiceException {
        try {
            String fixedUrl = urlFixer.fix( url );
            logger.debug( "starting download of [{}]", fixedUrl );
            return Jsoup.connect( fixedUrl ).execute().body();
        } catch ( Exception e ) {
            logger.debug( "Could not download from url: {}", url );
            throw new DownloaderServiceException( "Could not download from url: " + url, e );
        }
    }


    public DownloaderServiceImpl urlFixer( UrlFixer urlFixer ) {
        this.urlFixer = urlFixer;
        return this;
    }
}

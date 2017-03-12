package com.demo.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by davide on 12/03/17.
 */
public class UrlFixerImpl implements UrlFixer {
    private static Logger logger = LoggerFactory.getLogger( UrlFixerImpl.class );

    @Override
    public String fix( final String url ) throws MalformedURLException {
        try {
            URI uri = new URI( url );
            return uri.toURL().toString();
        } catch ( Exception e ) {
            logger.debug( "got malformed url [{}], attempting fix", url );
            return new URL("http", url, "" ).toString();
        }
    }
}

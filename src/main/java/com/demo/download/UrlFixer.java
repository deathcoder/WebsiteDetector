package com.demo.download;

import java.net.MalformedURLException;

/**
 * Created by davide on 12/03/17.
 */
public interface UrlFixer {
    String fix( String url ) throws MalformedURLException;
}

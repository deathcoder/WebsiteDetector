package com.demo.deathcoder.detector.impl;

import com.demo.deathcoder.detector.WebsiteDetector;
import org.jsoup.Jsoup;

/**
 * Created by davide on 20/10/16.
 */
public class TitleWebsiteDetector implements WebsiteDetector {
    private String title;

    @Override public Boolean detect( final String htmlBody ) {
        return Jsoup.parse( htmlBody ).title().toLowerCase().contains( title.toLowerCase() );
    }
    public TitleWebsiteDetector title( final String title ) {
        this.title = title;
        return this;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle( final String title ) {
        this.title = title;
    }
}

package com.demo.deathcoder.detector.impl;

import com.demo.deathcoder.detector.WebsiteDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by davide on 20/10/16.
 */
public class ChainWebsiteDetector implements WebsiteDetector {
    private static Logger logger = LoggerFactory.getLogger( ChainWebsiteDetector.class );

    private List<WebsiteDetector> websiteDetectors;

    @Override public Boolean detect( final String htmlBody ) {
        return websiteDetectors.stream()
                .filter( detector -> detector.detect( htmlBody ) )
                .findFirst().isPresent();
    }

    public List<WebsiteDetector> getWebsiteDetectors() {
        return websiteDetectors;
    }
    public void setWebsiteDetectors( final List<WebsiteDetector> websiteDetectors ) {
        this.websiteDetectors = websiteDetectors;
    }
    public ChainWebsiteDetector websiteDetectors( final List<WebsiteDetector> websiteDetectors ) {
        this.websiteDetectors = websiteDetectors;
        return this;
    }
}

package com.demo.deathcoder.detector.impl;

import com.demo.deathcoder.detector.WebsiteDetector;
import org.junit.Test;

import static com.demo.deathcoder.detector.impl.util.DetectorTestUtil.generateHtmlWithTitle;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by davide on 21/10/16.
 */
public class TitleWebsiteEntityDetectorTest {
    @Test
    public void isDetected() throws Exception {
        WebsiteDetector titleDetector = new TitleWebsiteDetector().title( "news" );

        Boolean actualNews = titleDetector.detect( generateHtmlWithTitle( "news" ) );
        assertThat( actualNews, is( true ) );

        Boolean actualGenericTitle = titleDetector.detect( "any title" );
        assertThat( actualGenericTitle, is( false ) );
    }

}
package com.demo.deathcoder.detector.impl;

import com.demo.deathcoder.detector.WebsiteDetector;
import org.junit.Test;

import java.util.Arrays;

import static com.demo.deathcoder.detector.impl.util.DetectorTestUtil.generateHtmlWithTitle;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by davide on 20/10/16.
 */
public class ChainWebsiteEntityDetectorTest {

    @Test
    public void isDetected() throws Exception {
        // Create WebsiteDetector Mock
        final WebsiteDetector detectorMock = mock( WebsiteDetector.class );
        WebsiteDetector chainWebsiteDetector = buildChainDetector( detectorMock );

        when( detectorMock.detect( generateHtmlWithTitle( "noticias" ) ) ).thenReturn( true );
        Boolean actualEsNews = chainWebsiteDetector.detect( generateHtmlWithTitle( "noticias" ) );
        assertThat( actualEsNews, is( true ) );

        when( detectorMock.detect( generateHtmlWithTitle( "any title" ) ) ).thenReturn( false );
        Boolean actualGenericTitle = chainWebsiteDetector.detect( "any title" );
        assertThat( actualGenericTitle, is( false ) );
    }

    @Test
    public void isDetectedEmptyChain() throws Exception {
        WebsiteDetector chainWebsiteDetector = buildChainDetector();
        Boolean actualNews = chainWebsiteDetector.detect( generateHtmlWithTitle( "news" ) );
        assertThat( actualNews, is( false ) );
    }

    private WebsiteDetector buildChainDetector( WebsiteDetector... detectors ) {
        return new ChainWebsiteDetector().websiteDetectors( Arrays.asList( detectors ));
    }
}
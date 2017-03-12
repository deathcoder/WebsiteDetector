package com.demo.deathcoder.crawler;

import com.demo.download.DownloaderService;
import com.demo.deathcoder.model.Website;
import com.demo.deathcoder.persistency.WebsiteEntityManager;
import com.demo.deathcoder.detector.WebsiteDetector;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by davide on 20/10/16.
 */
public class CrawlerServiceImplTest {
    @Test
    public void processUrls() throws Exception {
        // Create DownloaderService Mock
        final DownloaderService downloaderServiceMock = mock( DownloaderService.class );
        // Create WebsiteDetector Mock
        final WebsiteDetector websiteDetectorMock = mock( WebsiteDetector.class );
        // Create WebsiteEntityManager Mock
        final WebsiteEntityManager websiteEntityManagerMock = mock( WebsiteEntityManager.class );

        CrawlerService crawlerService = new CrawlerServiceImpl()
                .downloaderService( downloaderServiceMock )
                .websiteDetector( websiteDetectorMock )
                .websiteEntityManager( websiteEntityManagerMock );

        String testHtml = "<html><body>aaa</body></html>";
        when( downloaderServiceMock.download( "url1" ) ).thenReturn( testHtml );
        when( websiteDetectorMock.detect( testHtml ) ).thenReturn( false );


        List<Website> actualResult = crawlerService.scan( "url1" );
        Website expected = new Website().url( "url1" ).body( testHtml ).detected( false );
        assertThat(actualResult, containsInAnyOrder( expected ) );

        verify( websiteEntityManagerMock ).save( eq( expected ) );
    }
}
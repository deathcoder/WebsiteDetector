package com.demo.download;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by davide on 21/10/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( Jsoup.class )
public class DownloaderServiceImplTest {
    private static Logger logger = LoggerFactory.getLogger( DownloaderServiceImplTest.class );

    @Test
    public void download() throws Exception {
        mockStatic( Jsoup.class );

        // Create Connection Mock
        final Connection connectionMock = mock( Connection.class );
        when( Jsoup.connect( "url" ) ).thenReturn( connectionMock );

        // Create Response Mock
        HttpConnection.Response responseMock = mock( HttpConnection.Response.class );
        when( connectionMock.execute() ).thenReturn( responseMock );

        String expected = "<html><body></body></html>";
        when( responseMock.body() ).thenReturn( expected );

        UrlFixer urlFixerMock = mock( UrlFixer.class );
        when( urlFixerMock.fix( "url" ) ).thenReturn( "url" );
        DownloaderServiceImpl downloaderService = new DownloaderServiceImpl().urlFixer( urlFixerMock );
        String actual = downloaderService.download( "url" );

        assertThat( actual, is( expected ) );
    }

    /**
     * this test actually needs a working connection and the site to be available
     * it just tests the fixUrl logic and is now ignored
     * */
    @Test
    @Ignore
    public void testUri() throws URISyntaxException, IOException {
        String input = "viralmediabar.com/text";
        URI uri = new URI( input );
        String output = null;
        try {
            output = uri.toURL().toString();
            logger.debug( "no fix: {}", output );
        } catch ( Exception e ) {
            logger.debug( "attempting fix of url: {}", input );
            output = new URL("http", input, "" ).toString();
        }
        logger.debug( "out: {} ", output );
        String body = Jsoup.connect( output ).execute().body();
        logger.debug( "body: {}", body );
    }

}
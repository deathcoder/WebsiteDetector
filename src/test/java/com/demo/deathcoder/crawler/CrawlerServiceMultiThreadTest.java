package com.demo.deathcoder.crawler;

import com.demo.deathcoder.persistency.WebsiteEntityManager;
import com.demo.download.DownloaderService;
import com.demo.deathcoder.detector.WebsiteDetector;
import com.demo.deathcoder.model.Website;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by davide on 22/10/16.
 */
public class CrawlerServiceMultiThreadTest {
    private static Logger logger = LoggerFactory.getLogger( CrawlerServiceMultiThreadTest.class );

    @Test
    public void scan() throws Exception {
        // Create DownloaderService Mock
        final DownloaderService downloaderServiceMock = mock( DownloaderService.class );
        // Create WebsiteDetector Mock
        final WebsiteDetector websiteDetectorMock = mock( WebsiteDetector.class );
        // Create WebsiteEntityManager Mock
        final WebsiteEntityManager websiteEntityManagerMock = mock( WebsiteEntityManager.class );

        // Create AsyncListenableTaskExecutor Mock
        final AsyncListenableTaskExecutor taskExecutorMock = mock( AsyncListenableTaskExecutor.class );

        CrawlerService crawlerService = new CrawlerServiceMultiThread()
                .taskExecutor( taskExecutorMock )
                .downloaderService( downloaderServiceMock )
                .websiteDetector( websiteDetectorMock )
                .websiteEntityManager( websiteEntityManagerMock );

        String testHtml = "<html><body>aaa</body></html>";

        SettableListenableFuture<Object> listenableFuture = new SettableListenableFuture<>();

        when( taskExecutorMock.submitListenable( any( Callable.class ) ) ).thenReturn( listenableFuture );

        doAnswer( this::answerToExecute ).when( taskExecutorMock ).execute( any( Runnable.class ) );
        when( downloaderServiceMock.download( "url1" ) ).thenReturn( testHtml );
        when( websiteDetectorMock.detect( testHtml ) ).thenReturn( false );

        /* this blocks and the test never gets to the future set instruction */
        /* should be executed in different thread */
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Website>> actualResult = executorService.submit( () -> crawlerService.scan( "url1" ) );

        Website expected = new Website().url( "url1" ).body( testHtml ).detected( false );
        listenableFuture.set( Optional.of( expected ) );

        await( listenableFuture );
        verify( websiteEntityManagerMock ).save( eq( expected ) );

        assertThat( actualResult.get(), containsInAnyOrder( expected) );
    }

    private Void answerToExecute( final InvocationOnMock invocationOnMock ) {
        Runnable lambda = ( Runnable ) invocationOnMock.getArguments()[0];
        logger.debug( "argument: {}", lambda );
        lambda.run();
        return null;
    }

    private void await( Future future ) {
        try {
            logger.debug( "waiting..." );
            Object obj = future.get( 1000, TimeUnit.MILLISECONDS );
            logger.debug( "future was: [{}]", obj );
        } catch ( Exception e ) {
            fail();
        }
    }

    private void await( Future... future ) throws ExecutionException, InterruptedException {
        Arrays.stream( future ).forEach( this::await );
    }
}
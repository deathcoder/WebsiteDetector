package com.demo.deathcoder.crawler;

import com.demo.deathcoder.model.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by davide on 22/10/16.
 */
public class CrawlerServiceMultiThread extends CrawlerServiceImpl {
    private static Logger logger = LoggerFactory.getLogger( CrawlerServiceMultiThread.class );

    @Autowired
    private AsyncListenableTaskExecutor taskExecutor;

    @Override public List<Website> scan( @NotNull final List<String> urls ) {
        List<Optional<Website>> results = new ArrayList<>();
        List<ListenableFuture<Optional<Website>>> futureList = urls.stream().map( this::asyncDownload ).collect( Collectors.toList() );

        futureList.forEach( listenableFuture -> listenableFuture.addCallback(
                        success -> results.add( onDownloadSuccess( success ) ),
                        this::onDownloadError ) );

        return futureList.stream().map( this::awaitFutures )
                .filter( Optional::isPresent )
                .map( Optional::get )
                .collect( Collectors.toList() );
    }
    private Optional<Website> awaitFutures( final ListenableFuture<Optional<Website>> optionalListenableFuture ) {
        try {
            return optionalListenableFuture.get();
        } catch ( Exception e ) {
            logger.debug( "Failed to scan url", e );
        }
        return Optional.empty();
    }

    private Optional<Website> onDownloadSuccess( final Optional<Website> optionalWebSite ) {
        if( optionalWebSite.isPresent() ) {
            Website website = optionalWebSite.get();
            detectorTest( website );
            persist( website );
            return Optional.of( website );
        }
        return Optional.empty();
    }

    private void onDownloadError( final Throwable throwable ) {
        logger.debug( throwable.getMessage(), throwable );
    }

    private ListenableFuture<Optional<Website>> asyncDownload( final String url ) {
        return taskExecutor.submitListenable( () -> super.download( url ) );
    }

    @Override protected void persist( final Website website ) {
        taskExecutor.execute( () -> super.persist( website ) );
    }

    @Override protected Website detectorTest( final Website website ) {
        /* this could also use the task executor but for now the check is really fast */
        return super.detectorTest( website );
    }

    public AsyncListenableTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }
    public void setTaskExecutor( final AsyncListenableTaskExecutor taskExecutor ) {
        this.taskExecutor = taskExecutor;
    }
    public CrawlerServiceMultiThread taskExecutor( final AsyncListenableTaskExecutor taskExecutor ) {
        this.taskExecutor = taskExecutor;
        return this;
    }
}

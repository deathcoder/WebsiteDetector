package com.demo.deathcoder.crawler;

import com.demo.deathcoder.persistency.WebsiteEntityManager;
import com.demo.download.DownloaderService;
import com.demo.deathcoder.detector.WebsiteDetector;
import com.demo.deathcoder.model.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by davide on 19/10/16.
 */
public class CrawlerServiceImpl implements CrawlerService {
    private static Logger logger = LoggerFactory.getLogger( CrawlerServiceImpl.class );

    @Autowired
    private DownloaderService downloaderService;

    @Autowired
    private WebsiteDetector websiteDetector;

    @Autowired
    private WebsiteEntityManager websiteEntityManager;


    @Override
    public List<Website> scan( final @NotNull List<String> urls ) {
        /* download, validate and persist */
        List<Website> results = urls.stream()
                .map( this::download )
                .filter( Optional::isPresent )
                .map( Optional::get )
                .map( this::detectorTest )
                .collect( Collectors.toList() );
        results.forEach( this::persist );
        return results;
    }

    @Override
    public List<Website> scan( final @NotNull String... urls ) {
        return scan( Arrays.asList( urls ) );
    }

    protected void persist( Website website ) {
        websiteEntityManager.save( website );
    }

    protected Website detectorTest( Website website ) {
        return website.detected( websiteDetector.detect( website.getBody() ) );
    }

    protected Optional<Website> download( String url ) {
        try {
            return Optional.of( new Website().url( url ).body( downloaderService.download( url ) ) );
        } catch ( Exception e ) {
            logger.debug( String.format( "[skipping] Error during download of [%s]", url ), e);
        }
        return Optional.empty();
    }

    public DownloaderService getDownloaderService() {
        return downloaderService;
    }
    public void setDownloaderService( final DownloaderService downloaderService ) {
        this.downloaderService = downloaderService;
    }
    public WebsiteDetector getWebsiteDetector() {
        return websiteDetector;
    }
    public void setWebsiteDetector( final WebsiteDetector websiteDetector ) {
        this.websiteDetector = websiteDetector;
    }
    public CrawlerServiceImpl downloaderService( final DownloaderService downloaderService ) {
        this.downloaderService = downloaderService;
        return this;
    }
    public CrawlerServiceImpl websiteDetector( final WebsiteDetector websiteDetector ) {
        this.websiteDetector = websiteDetector;
        return this;
    }

    public WebsiteEntityManager getWebsiteEntityManager() {
        return websiteEntityManager;
    }
    public void setWebsiteEntityManager( final WebsiteEntityManager websiteEntityManager ) {
        this.websiteEntityManager = websiteEntityManager;
    }
    public CrawlerServiceImpl websiteEntityManager( final WebsiteEntityManager websiteEntityManager ) {
        this.websiteEntityManager = websiteEntityManager;
        return this;
    }
}

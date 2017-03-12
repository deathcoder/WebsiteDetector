package com.demo.config;

import com.demo.deathcoder.detector.impl.TitleWebsiteDetector;
import com.demo.deathcoder.persistency.WebsiteEntityManager;
import com.demo.download.DownloaderService;
import com.demo.download.DownloaderServiceImpl;
import com.demo.deathcoder.crawler.CrawlerService;
import com.demo.deathcoder.crawler.CrawlerServiceImpl;
import com.demo.deathcoder.crawler.CrawlerServiceMultiThread;
import com.demo.deathcoder.detector.WebsiteDetector;
import com.demo.deathcoder.detector.impl.ChainWebsiteDetector;
import com.demo.deathcoder.persistency.impl.WebsiteEntityManagerImpl;
import com.demo.download.UrlFixer;
import com.demo.download.UrlFixerImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide on 21/10/16.
 */
@Configuration
public class CrawleerConfig {
    @Bean
    public AsyncListenableTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize( 1 );
        threadPoolTaskExecutor.setMaxPoolSize( 1 );
        threadPoolTaskExecutor.setQueueCapacity( 50 );
        return threadPoolTaskExecutor;
    }

    @Bean
    @Qualifier("crawlerService")
    public CrawlerService crawlerService() {
        return new CrawlerServiceImpl();
    }

    @Bean
    @Qualifier
    public CrawlerService asyncCrawlerService() {
        return new CrawlerServiceMultiThread();
    }

    @Bean
    public DownloaderService downloaderService( UrlFixer urlFixer ) {
        return new DownloaderServiceImpl().urlFixer( urlFixer );
    }

    @Bean
    public UrlFixer urlFixer() {
        return new UrlFixerImpl();
    }

    @Bean
    public WebsiteDetector websiteDetector() {
        List<WebsiteDetector> detectors = new ArrayList<>(  );
        detectors.add( new TitleWebsiteDetector().title( "news" ) );
        detectors.add( new TitleWebsiteDetector().title( "tech" ) );

        return new ChainWebsiteDetector().websiteDetectors( detectors );
    }

    @Bean
    public WebsiteEntityManager websiteEntityManager(){ return new WebsiteEntityManagerImpl(); }
}

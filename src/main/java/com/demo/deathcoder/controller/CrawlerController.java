package com.demo.deathcoder.controller;

import com.demo.deathcoder.crawler.CrawlerService;
import com.demo.deathcoder.model.ScanRequest;
import com.demo.deathcoder.model.Website;
import com.demo.deathcoder.persistency.WebsiteEntityManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api( value = "home controller" )
public class CrawlerController {
    private static Logger logger = LoggerFactory.getLogger( CrawlerController.class );

    @Autowired
    private AsyncListenableTaskExecutor taskExecutor;

    @Autowired
    WebsiteEntityManager websiteEntityManager;

    @Autowired
    @Qualifier( "crawlerService" )
    private CrawlerService crawlerService;

    @Autowired
    @Qualifier( "asyncCrawlerService" )
    private CrawlerService asyncCrawlerService;

    @RequestMapping( value = "/asyncScan", method = RequestMethod.POST )
    @ApiOperation( "[NON-BLOCKING] Tests if the given urls are detected and persists the result" )
    public @ResponseBody
    DeferredResult<List<Website>> asyncScanUrls( @ApiParam( "The urls to be tested" ) @RequestBody List<ScanRequest> urls ) throws
            ControllerException {
        final DeferredResult<List<Website>> deferredResult = new DeferredResult<>( );

        deferredResult.onTimeout( () -> {
            // Retry on timeout
            deferredResult.setErrorResult( ResponseEntity.status( HttpStatus.REQUEST_TIMEOUT ).body( "Request timeout occurred." ) );
        } );

        ListenableFuture<List<Website>> future = taskExecutor.submitListenable( () -> {
            logger.trace( "Processing urls: {}", urls );
            List<String> stringUrls = urls.stream().map( ScanRequest::getUrl ).collect( Collectors.toList() );
            return crawlerService.scan( stringUrls );
        } );
        future.addCallback(
                deferredResult::setResult,
                error -> deferredResult.setErrorResult( ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( error ) ) );
        return deferredResult;
    }

    @RequestMapping( value = "/scan", method = RequestMethod.POST )
    @ApiOperation( "Tests if the given urls are detected and persists the result" )
    public @ResponseBody
    List<Website> scanUrls( @ApiParam( "The urls to be tested" ) @RequestBody List<ScanRequest> urls ) throws ControllerException {
        logger.trace( "Processing urls: {}", urls );
        List<String> stringUrls = urls.stream().map( ScanRequest::getUrl ).collect( Collectors.toList() );
        return crawlerService.scan( stringUrls );
    }

    @RequestMapping( value = "/db", method = RequestMethod.GET )
    public @ResponseBody List<Website> getDb() {
        return websiteEntityManager.findAll();
    }
}

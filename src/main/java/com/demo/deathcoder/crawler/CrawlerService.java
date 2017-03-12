package com.demo.deathcoder.crawler;

import com.demo.deathcoder.model.Website;

import java.util.List;

/**
 * Created by davide on 19/10/16.
 */
public interface CrawlerService {
    List<Website> scan( List<String> urls);
    List<Website> scan( String... urls);
}

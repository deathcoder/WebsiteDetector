package com.demo.deathcoder.persistency.impl;

import com.demo.deathcoder.model.Website;
import com.demo.deathcoder.persistency.WebsiteEntityManager;
import com.demo.deathcoder.persistency.WebSiteAdapter;
import com.demo.deathcoder.persistency.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davide on 21/10/16.
 */
public class WebsiteEntityManagerImpl implements WebsiteEntityManager {
    @Autowired
    private WebsiteRepository websiteRepository;

    @Override public void save( final Website website ) {
        websiteRepository.save( WebSiteAdapter.toWebsiteEntity( website ) );
    }

    @Override public List<Website> findAll() {
        return websiteRepository.findAll().stream()
                .map( WebSiteAdapter::toWebSite )
                .collect( Collectors.toList() );
    }

    public WebsiteEntityManagerImpl websiteRepository( final WebsiteRepository websiteRepository ) {
        this.websiteRepository = websiteRepository;
        return this;
    }
    public WebsiteRepository getWebsiteRepository() {
        return websiteRepository;
    }
    public void setWebsiteRepository( final WebsiteRepository websiteRepository ) {
        this.websiteRepository = websiteRepository;
    }
}

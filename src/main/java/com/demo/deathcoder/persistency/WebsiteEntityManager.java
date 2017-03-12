package com.demo.deathcoder.persistency;

import com.demo.deathcoder.model.Website;

import java.util.List;

/**
 * Created by davide on 20/10/16.
 */
public interface WebsiteEntityManager {
    void save( Website website );
    List<Website> findAll();
}

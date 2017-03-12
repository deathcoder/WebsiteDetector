package com.demo.deathcoder.persistency.repository;

import com.demo.deathcoder.persistency.entity.WebsiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by davide on 18/10/16.
 */
@Repository
public interface WebsiteRepository extends JpaRepository<WebsiteEntity, Long> { }

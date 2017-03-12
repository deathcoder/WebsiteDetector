package com.demo.deathcoder.persistency.repository;

import com.demo.config.EmbeddedDataSourceConfig;
import com.demo.config.WebAppConfigurationAware;
import com.demo.deathcoder.persistency.entity.WebsiteEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by davide on 18/10/16.
 */
@Import( EmbeddedDataSourceConfig.class )
public class WebsiteEntityRepositoryTest extends WebAppConfigurationAware {
    @Autowired
    private WebsiteRepository websiteRepository;

    @Test
    public void testConfiguration() {
        WebsiteEntity websiteEntityEntry = websiteRepository.findOne( 1000L );

        assertNotNull( websiteEntityEntry );
        WebsiteEntity expected = new WebsiteEntity().id( 1000L )
                .url( "testurl" ).detected( true )
                .scanDate( LocalDate.of( 2012, 1, 2 ) );
        assertThat( websiteEntityEntry, is( expected ) );
    }
}
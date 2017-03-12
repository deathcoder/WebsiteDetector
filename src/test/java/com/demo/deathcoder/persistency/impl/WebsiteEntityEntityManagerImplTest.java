package com.demo.deathcoder.persistency.impl;

import com.demo.config.WebAppConfigurationAware;
import com.demo.deathcoder.model.Website;
import com.demo.deathcoder.persistency.WebsiteEntityManager;
import com.demo.deathcoder.persistency.entity.WebsiteEntity;
import com.demo.deathcoder.persistency.repository.WebsiteRepository;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by davide on 21/10/16.
 */
public class WebsiteEntityEntityManagerImplTest extends WebAppConfigurationAware {
    @Test
    public void save() throws Exception {
        // Create WebsiteRepository Mock
        final WebsiteRepository websiteRepositoryMock = mock( WebsiteRepository.class );

        WebsiteEntityManager websiteEntityManager = new WebsiteEntityManagerImpl().websiteRepository( websiteRepositoryMock );

        Website website = new Website().body( "aaaa" ).url( "url" ).detected( false );
        websiteEntityManager.save( website );
        final WebsiteEntity expected = new WebsiteEntity().url( "url" ).detected( false );
        verify( websiteRepositoryMock ).save( eq( expected ) );
    }
}
package com.demo.deathcoder.persistency;

import com.demo.deathcoder.model.Website;
import com.demo.deathcoder.persistency.entity.WebsiteEntity;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by davide on 21/10/16.
 */
public class WebsiteAdapterTest {
    @Test
    public void toWebsiteEntity() throws Exception {
        Website source = new Website().body( "aaaaa" ).url( "url" ).detected( true );
        WebsiteEntity adapted = WebSiteAdapter.toWebsiteEntity( source );
        WebsiteEntity expected = new WebsiteEntity().detected( true ).url( "url" );
        assertThat( adapted, is( expected ) );
    }

    @Test
    public void toWebsite() throws Exception {
        WebsiteEntity websiteEntity = new WebsiteEntity()
                .id( 1L ).url( "url" )
                .detected( true )
                .scanDate( LocalDate.of( 2012, 1, 2 ) );
        Website adapted = WebSiteAdapter.toWebSite( websiteEntity );
        Website expected = new Website().url( "url" ).detected( true );
        assertThat( adapted, is( expected ) );
    }
}
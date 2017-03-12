package com.demo.deathcoder.persistency;

import com.demo.deathcoder.model.Website;
import com.demo.deathcoder.persistency.entity.WebsiteEntity;
import org.springframework.beans.BeanUtils;

/**
 * Created by davide on 21/10/16.
 */
public class WebSiteAdapter {
    public static WebsiteEntity toWebsiteEntity( Website website ) {
        WebsiteEntity websiteEntity = new WebsiteEntity();
        BeanUtils.copyProperties( website, websiteEntity );
        return websiteEntity;
    }

    public static Website toWebSite( WebsiteEntity websiteEntity ) {
        Website website = new Website();
        BeanUtils.copyProperties( websiteEntity, website );
        return website;
    }

}

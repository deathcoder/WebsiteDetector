package com.demo.deathcoder.controller;

import com.demo.config.WebAppConfigurationAware;
import com.demo.deathcoder.controller.util.JsonUtil;
import com.demo.deathcoder.crawler.CrawlerService;
import com.demo.deathcoder.model.ScanRequest;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by davide on 18/10/16.
 */
@Configuration
@ContextConfiguration( classes = { CrawlerControllerTest.class } )
public class CrawlerControllerTest extends WebAppConfigurationAware {
    @Bean
    public CrawlerService crawlerService() {
        return mock( CrawlerService.class );
    }

    @Test
    @DirtiesContext
    public void testScanUrlConfiguration() throws Exception {
        List<ScanRequest> urls = Arrays.asList( generateRequest( "url1" ), generateRequest( "url2" ) );

        this.mockMvc.perform( post( "/scan" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) )
                .contentType( MediaType.APPLICATION_JSON )
                .content( JsonUtil.jsonize( urls ) ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( "application/json;charset=UTF-8" ) );

    }


    private ScanRequest generateRequest( String url ) {
        return new ScanRequest().url( url );
    }
}
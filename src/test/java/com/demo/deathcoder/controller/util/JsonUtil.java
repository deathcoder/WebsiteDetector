package com.demo.deathcoder.controller.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by davide on 21/10/16.
 */
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger( JsonUtil.class );

    public static String jsonize( final Object object ) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final StringWriter stringWriter = new StringWriter();
        mapper.writeValue( stringWriter, object );
        final String json = stringWriter.toString();
        logger.debug( "JSON is {}", json );
        return json;
    }
}


package com.demo.deathcoder.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by davide on 22/10/16.
 */
public class ScanRequest {
    private String url;

    public String getUrl() {
        return url;
    }
    public void setUrl( final String url ) {
        this.url = url;
    }
    public ScanRequest url( final String url ) {
        this.url = url;
        return this;
    }
    @Override
    public boolean equals( final Object o ) {
        return EqualsBuilder.reflectionEquals( this, o );
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode( this );
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this, ToStringStyle.SHORT_PREFIX_STYLE );
    }
}

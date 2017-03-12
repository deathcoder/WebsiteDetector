package com.demo.deathcoder.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by davide on 20/10/16.
 */
public class Website {
    private String url;
    private String body;
    private Boolean detected;

    public String getUrl() {
        return url;
    }
    public void setUrl( final String url ) {
        this.url = url;
    }
    public String getBody() {
        return body;
    }
    public void setBody( final String body ) {
        this.body = body;
    }
    public Website url( final String url ) {
        this.url = url;
        return this;
    }
    public Website body( final String body ) {
        this.body = body;
        return this;
    }
    public Boolean getDetected() {
        return detected;
    }
    public void setDetected( final Boolean detected ) {
        this.detected = detected;
    }
    public Website detected( final Boolean detected ) {
        this.detected = detected;
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

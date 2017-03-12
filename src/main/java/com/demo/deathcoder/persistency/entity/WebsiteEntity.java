package com.demo.deathcoder.persistency.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by davide on 18/10/16.
 */
@Entity
@Table(name = "WEBSITE")
public class WebsiteEntity {
    @Column( name = "ID" )
    @Id
    @GeneratedValue
    private Long id;

    @Column( name = "URL")
    private String url;

    @Column( name = "DETECTED")
    private Boolean detected;

    @Column( name = "SCAN_DATE")
    private LocalDate scanDate;

    public Long getId() {
        return id;
    }
    public void setId( final Long id ) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl( final String url ) {
        this.url = url;
    }
    public Boolean getDetected() {
        return detected;
    }
    public void setDetected( final Boolean detected ) {
        this.detected = detected;
    }
    public LocalDate getScanDate() {
        return scanDate;
    }
    public void setScanDate( final LocalDate scanDate ) {
        this.scanDate = scanDate;
    }
    public WebsiteEntity id( final Long id ) {
        this.id = id;
        return this;
    }
    public WebsiteEntity url( final String url ) {
        this.url = url;
        return this;
    }
    public WebsiteEntity detected( final Boolean detected ) {
        this.detected = detected;
        return this;
    }
    public WebsiteEntity scanDate( final LocalDate scanDate ) {
        this.scanDate = scanDate;
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

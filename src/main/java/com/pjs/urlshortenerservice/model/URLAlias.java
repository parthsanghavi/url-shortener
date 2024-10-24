package com.pjs.urlshortenerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class URLAlias {

    private String alias;
    private String shortUrl;
    private String longUrl;
    private int timeToLive;

}

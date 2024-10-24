package com.pjs.urlshortenerservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class URLAliasUpdateRequest {

    private String customAlias;
    private int ttlSeconds;

}

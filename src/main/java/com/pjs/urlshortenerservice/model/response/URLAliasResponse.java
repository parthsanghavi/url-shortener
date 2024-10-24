package com.pjs.urlshortenerservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class URLAliasResponse {

    private String shortUrl;

}

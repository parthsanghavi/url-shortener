package com.pjs.urlshortenerservice.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class URLAnalytics {

    private String alias;
    private String longUrl;
    private int accessCount;
    private List<String> accessTime;
}

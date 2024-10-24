package com.pjs.urlshortenerservice.service.impl;

import com.pjs.urlshortenerservice.datastore.URLAnalyticsDatastore;
import com.pjs.urlshortenerservice.model.URLAnalytics;
import com.pjs.urlshortenerservice.service.RedisService;
import com.pjs.urlshortenerservice.service.URLAliasAnalyticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class URLAliasAnalyticsServiceImpl implements URLAliasAnalyticsService {

    private final URLAnalyticsDatastore urlAnalyticsDatastore;
    private final RedisService redisService;

    @Autowired
    public URLAliasAnalyticsServiceImpl(URLAnalyticsDatastore urlAnalyticsDatastore, RedisService redisService) {
        this.urlAnalyticsDatastore = urlAnalyticsDatastore;
        this.redisService = redisService;
    }

    @Override
    public URLAnalytics getURLAnalytics(String alias) {
        URLAnalytics urlAnalytics = redisService.get(alias, URLAnalytics.class);
        if (urlAnalytics != null) {
            log.info("Got the url analytics from the cache...");
            return urlAnalytics;
        }
        urlAnalytics = urlAnalyticsDatastore.findByAlias(alias);
        redisService.set(alias, urlAnalytics, 60L);
        return urlAnalytics;
    }
}

package com.pjs.urlshortenerservice.service;

import com.pjs.urlshortenerservice.model.URLAnalytics;
import org.springframework.stereotype.Service;

@Service
public interface URLAliasAnalyticsService {

    URLAnalytics getURLAnalytics(String alias);
}

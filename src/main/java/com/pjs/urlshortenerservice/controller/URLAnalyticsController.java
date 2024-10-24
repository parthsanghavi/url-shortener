package com.pjs.urlshortenerservice.controller;

import com.pjs.urlshortenerservice.model.URLAnalytics;
import com.pjs.urlshortenerservice.service.URLAliasAnalyticsService;
import com.pjs.urlshortenerservice.service.impl.URLAliasAnalyticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/url-analytics")
public class URLAnalyticsController {

    private final URLAliasAnalyticsService urlAliasAnalyticsService;

    @Autowired
    public URLAnalyticsController(URLAliasAnalyticsServiceImpl urlAliasAnalyticsService) {
        this.urlAliasAnalyticsService = urlAliasAnalyticsService;
    }

    @GetMapping(value = "/{alias}")
    public ResponseEntity<URLAnalytics> getURLAnalytics(@PathVariable String alias) {
        return ResponseEntity.ok(urlAliasAnalyticsService.getURLAnalytics(alias));
    }
}

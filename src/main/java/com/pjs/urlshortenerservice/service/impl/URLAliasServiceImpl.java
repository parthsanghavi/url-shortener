package com.pjs.urlshortenerservice.service.impl;

import com.pjs.urlshortenerservice.datastore.URLAliasDatastore;
import com.pjs.urlshortenerservice.datastore.URLAnalyticsDatastore;
import com.pjs.urlshortenerservice.exception.InvalidInputException;
import com.pjs.urlshortenerservice.model.URLAlias;
import com.pjs.urlshortenerservice.model.URLAnalytics;
import com.pjs.urlshortenerservice.model.request.URLAliasRequest;
import com.pjs.urlshortenerservice.model.request.URLAliasUpdateRequest;
import com.pjs.urlshortenerservice.service.URLAliasService;
import com.pjs.urlshortenerservice.util.RestApiHandler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLAliasServiceImpl implements URLAliasService {

    private final URLAliasDatastore urlAliasDatastore;
    private final URLAnalyticsDatastore urlAnalyticsDatastore;
    private final RestApiHandler restApiHandler;

    @Autowired
    public URLAliasServiceImpl(URLAliasDatastore urlAliasDatastore, URLAnalyticsDatastore urlAnalyticsDatastore,
          RestApiHandler restApiHandler) {
        this.urlAliasDatastore = urlAliasDatastore;
        this.urlAnalyticsDatastore = urlAnalyticsDatastore;
        this.restApiHandler = restApiHandler;
    }

    @Override
    public URLAlias saveUrlAlias(URLAliasRequest urlAliasRequest) {
        URLAlias existingAlias = urlAliasDatastore.findByAlias(urlAliasRequest.getCustomAlias());
        if (existingAlias != null) {
            throw new InvalidInputException("URL Alias already in use.");
        }
        URLAlias urlAlias = new URLAlias();
        urlAlias.setAlias(urlAliasRequest.getCustomAlias());
        urlAlias.setShortUrl(String.format("http://localhost:8080/url-shortener/%s", urlAliasRequest.getCustomAlias()));
        urlAlias.setLongUrl(urlAliasRequest.getLongUrl());
        urlAlias.setTimeToLive(urlAliasRequest.getTtlSeconds());

        URLAnalytics urlAnalytics = new URLAnalytics();
        urlAnalytics.setAlias(urlAliasRequest.getCustomAlias());
        urlAnalytics.setLongUrl(urlAliasRequest.getLongUrl());
        urlAnalytics.setAccessCount(0);
        urlAnalytics.setAccessTime(new ArrayList<>());

        urlAliasDatastore.saveUrlAlis(urlAlias);
        urlAnalyticsDatastore.saveUrlAliasAnalytics(urlAnalytics);

        return urlAlias;
    }

    @Override
    public String redirectShortUrl(String alias) {
        URLAlias urlAlias = urlAliasDatastore.findByAlias(alias);
        if (urlAlias == null) {
            throw new InvalidInputException("URL Alias does not exist");
        }

        String response = restApiHandler.get(urlAlias.getLongUrl());

        //update analytics as access is made
        URLAnalytics urlAnalytics = urlAnalyticsDatastore.findByAlias(alias);
        urlAnalytics.setAccessCount(urlAnalytics.getAccessCount() + 1);
        urlAnalytics.getAccessTime().add(String.valueOf(LocalDateTime.now()));
        urlAnalyticsDatastore.updateUrlAliasAnalytics(urlAnalytics);
        return response;
    }

    @Override
    public URLAlias updateUrlAlias(String alias, URLAliasUpdateRequest urlAliasUpdateRequest) {
        URLAlias urlAlias = urlAliasDatastore.findByAlias(alias);
        if (urlAlias == null) {
            throw new InvalidInputException("URL Alias does not exist");
        }
        URLAlias temp = urlAliasDatastore.findByAlias(urlAliasUpdateRequest.getCustomAlias());
        if (temp != null) {
            throw new InvalidInputException("URL Alias already in use.");
        }
        urlAlias.setAlias(urlAliasUpdateRequest.getCustomAlias());
        urlAlias.setShortUrl(String.format("http://localhost:8080/url-shortener/%s", urlAliasUpdateRequest.getCustomAlias()));
        urlAlias.setTimeToLive(urlAliasUpdateRequest.getTtlSeconds());

        URLAnalytics urlAnalytics = new URLAnalytics();
        urlAnalytics.setAlias(urlAliasUpdateRequest.getCustomAlias());
        urlAnalytics.setLongUrl(urlAlias.getLongUrl());
        urlAnalytics.setAccessCount(0);
        urlAnalytics.setAccessTime(new ArrayList<>());

        urlAliasDatastore.deleteURLAlias(alias);
        urlAliasDatastore.saveOrUpdateUrlAlias(urlAlias);
        urlAnalyticsDatastore.deleteUrlAliasAnalytics(alias);
        urlAnalyticsDatastore.saveUrlAliasAnalytics(urlAnalytics);
        return urlAlias;
    }

    @Override
    public void deleteAlias(String alias) {
        URLAlias urlAlias = urlAliasDatastore.findByAlias(alias);
        if (urlAlias == null) {
            return;
        }
        urlAliasDatastore.deleteURLAlias(alias);
        urlAnalyticsDatastore.deleteUrlAliasAnalytics(alias);
    }
}

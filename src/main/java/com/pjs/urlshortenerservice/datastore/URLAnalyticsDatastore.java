package com.pjs.urlshortenerservice.datastore;

import com.pjs.urlshortenerservice.model.URLAnalytics;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class URLAnalyticsDatastore {

    private static final Map<String, URLAnalytics> urlAnalyticsData = new HashMap<>();

    public void saveUrlAliasAnalytics(URLAnalytics urlAnalytics) {
        urlAnalyticsData.put(urlAnalytics.getAlias(), urlAnalytics);
    }

    public URLAnalytics findByAlias(String alias) {
        return urlAnalyticsData.get(alias);
    }

    public URLAnalytics updateUrlAliasAnalytics(URLAnalytics urlAnalytics) {
        return urlAnalyticsData.put(urlAnalytics.getAlias(), urlAnalytics);
    }

    public void deleteUrlAliasAnalytics(String alias) {
        urlAnalyticsData.remove(alias);
    }

}

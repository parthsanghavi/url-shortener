package com.pjs.urlshortenerservice.datastore;

import com.pjs.urlshortenerservice.model.URLAlias;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class URLAliasDatastore {

    private static final Map<String, URLAlias> urlAliasData = new HashMap<>();

    public void saveUrlAlis(URLAlias urlAlias) {
        urlAliasData.put(urlAlias.getAlias(), urlAlias);
    }

    public URLAlias findByAlias(String alias) {
        return urlAliasData.get(alias);
    }

    public URLAlias saveOrUpdateUrlAlias(URLAlias urlAlias) {
        return urlAliasData.put(urlAlias.getAlias(), urlAlias);
    }

    public void deleteURLAlias(String alias) {
        urlAliasData.remove(alias);
    }
}

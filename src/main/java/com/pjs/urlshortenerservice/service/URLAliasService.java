package com.pjs.urlshortenerservice.service;

import com.pjs.urlshortenerservice.model.URLAlias;
import com.pjs.urlshortenerservice.model.request.URLAliasRequest;
import com.pjs.urlshortenerservice.model.request.URLAliasUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface URLAliasService {

    URLAlias saveUrlAlias(URLAliasRequest urlAliasRequest);
    String redirectShortUrl(String alias);
    URLAlias updateUrlAlias(String alias, URLAliasUpdateRequest urlAliasUpdateRequest);
    void deleteAlias(String alias);
}

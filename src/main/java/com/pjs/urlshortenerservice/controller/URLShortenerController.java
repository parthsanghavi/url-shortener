package com.pjs.urlshortenerservice.controller;

import com.pjs.urlshortenerservice.model.URLAlias;
import com.pjs.urlshortenerservice.model.request.URLAliasRequest;
import com.pjs.urlshortenerservice.model.request.URLAliasUpdateRequest;
import com.pjs.urlshortenerservice.service.URLAliasService;
import com.pjs.urlshortenerservice.service.impl.URLAliasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/url-shortener")
public class URLShortenerController {

    private final URLAliasService urlAliasService;

    @Autowired
    public URLShortenerController(URLAliasServiceImpl urlAliasService) {
        this.urlAliasService = urlAliasService;
    }

    @PostMapping(value = "/shorten")
    public ResponseEntity<URLAlias> createURLAlias(@RequestBody URLAliasRequest urlAliasRequest) {
        try {
            URLAlias urlAlias = urlAliasService.saveUrlAlias(urlAliasRequest);
            return ResponseEntity.ok(urlAlias);
        } catch (Exception e) {
            System.out.println("Exception occurred " +  e);
            throw new RuntimeException("Exception occurred");
        }
    }

    @GetMapping(value = "/{alias}")
    public ResponseEntity<String> getURLAlias(@PathVariable String alias) {
        try {
            String response = urlAliasService.redirectShortUrl(alias);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Exception occurred " +  e);
            throw new RuntimeException("Exception occurred");
        }
    }

    @PutMapping(value = "/{alias}")
    public ResponseEntity<URLAlias> updateAlias(@PathVariable String alias,
          @RequestBody URLAliasUpdateRequest urlAliasUpdateRequest) {
        try {
            URLAlias urlAlias = urlAliasService.updateUrlAlias(alias, urlAliasUpdateRequest);
            return ResponseEntity.ok(urlAlias);
        } catch (Exception e) {
            System.out.println("Exception occurred " +  e);
            throw new RuntimeException("Exception occurred");
        }
    }

    @DeleteMapping(value = "/{alias}")
    public void deleteAlias(@PathVariable String alias) {
        try {
            urlAliasService.deleteAlias(alias);
        } catch (Exception e) {
            System.out.println("Exception occurred " +  e);
            throw new RuntimeException("Exception occurred");
        }
    }
}

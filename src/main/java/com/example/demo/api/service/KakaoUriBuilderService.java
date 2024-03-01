package com.example.demo.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddress(String address) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriComponentsBuilder.queryParam("query", address);

        URI uri = uriComponentsBuilder.encode().build().toUri();
        log.info("KakaoUriBuilderService buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }
}

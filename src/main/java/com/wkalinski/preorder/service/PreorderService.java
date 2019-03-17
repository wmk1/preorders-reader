package com.wkalinski.preorder.service;


import com.fasterxml.jackson.databind.ObjectReader;
import com.wkalinski.preorder.domain.Preorder;
import com.wkalinski.preorder.domain.PreorderPage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PreorderService {

    private static final String PREORDERS_API_URL = "https://api.kinguin.net/v1/catalog/preorders/";

    private List<Preorder> preordersList;

    public List<Preorder> getPreordersList(int page, int size, String sortingParam) {
            preordersList = new ArrayList<>();
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PREORDERS_API_URL)
                    .queryParam("page", page)
                    .queryParam("limit", size);
            ResponseEntity<List<Preorder>> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Preorder>>() {});
            preordersList = response.getBody();
        return preordersList;
    }

    public Preorder showDetailedPreorder(int preorderId) {
       if (preordersList == null) {
           preordersList = getPreordersList(1, 49, "");
       }
       return preordersList.stream().filter(preorder -> preorderId == preorder.getPreorderId())
                .findAny()
                .orElse(null);
    }

    static {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> hostname.equals("127.0.0.1"));
    }

}

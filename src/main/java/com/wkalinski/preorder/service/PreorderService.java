package com.wkalinski.preorder.service;

import com.wkalinski.preorder.PreorderApplication;
import com.wkalinski.preorder.domain.Preorder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PreorderService {

  private static final String PREORDERS_API_URL = "https://api.kinguin.net/v1/catalog/preorders/";

  private List<Preorder> preordersList;

  private RabbitTemplate rabbitTemplate;

  private static final Logger log = LoggerFactory.getLogger(PreorderService.class);

  @Autowired
  public PreorderService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = checkNotNull(rabbitTemplate);
  }


  public List<Preorder> getPreordersList(int page, int size, String sortingParam) {
    String order = verifySortingParam(sortingParam);
    preordersList = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder;
    if (order.equals("name") || order.equals("regular_price")) {
      builder =
          UriComponentsBuilder.fromHttpUrl(PREORDERS_API_URL)
              .queryParam("page", page)
              .queryParam("limit", size)
              .queryParam("order", order);
       } else {
      builder =
          UriComponentsBuilder.fromHttpUrl(PREORDERS_API_URL)
              .queryParam("page", page)
              .queryParam("limit", size);
      }
    ResponseEntity<List<Preorder>> response =
        restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Preorder>>() {});
    preordersList = response.getBody();

    for (Preorder p  : response.getBody() ) {
        sendProductMessage("" + p.getPreorderId());
    }
    return preordersList;
  }

  public Preorder showDetailedPreorder(int preorderId) {
    if (preordersList == null) {
      preordersList = getPreordersList(1, 49, "");
    }
    return preordersList.stream()
        .filter(preorder -> preorderId == preorder.getPreorderId())
        .findAny()
        .orElse(null);
  }

  private void sendProductMessage(String id) {
    Map<String, String> actionmap = new HashMap<>();
    actionmap.put("id", id);
    log.info("Getting information about preorder id: " + id);
    rabbitTemplate.convertAndSend(PreorderApplication.TOPIC_EXCHANGE_NAME, actionmap);
  }

  static {
    HttpsURLConnection.setDefaultHostnameVerifier(
        (hostname, session) -> hostname.equals("127.0.0.1"));
  }

  private String verifySortingParam(String sortingParam) {
    if (!(sortingParam.equals("name")) && !(sortingParam.equals("regular_price"))) return "dupa";
    return sortingParam;
  }

}

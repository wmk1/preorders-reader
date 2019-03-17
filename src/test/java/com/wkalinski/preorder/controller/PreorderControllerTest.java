package com.wkalinski.preorder.controller;

import com.wkalinski.preorder.domain.Preorder;
import com.wkalinski.preorder.service.PreorderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan
@SpringBootTest
@WebAppConfiguration
public class PreorderControllerTest {

  private MockMvc mockMvc;

  @InjectMocks private PreorderController preorderController;

  @Mock private PreorderService preorderService;

  private static final String PREORDER_API_URL = "https://api.kinguin.net/v1/catalog/preorders/";

  @Autowired private WebApplicationContext context;

  private Preorder preorder;

  private List<Preorder> preorders;

  @Before
  public void setup() throws Exception {

    this.mockMvc = MockMvcBuilders.standaloneSetup(preorderController).build();

    Preorder preorder3 =
        new Preorder.PreorderBuilder()
            .imageUrl("https://cdns.kinguin.net/media/region/europe.jpg")
            .regularPrice(new BigDecimal("300"))
            .minPrice(new BigDecimal("400"))
            .preorderId(13)
            .name("David")
            .build();
    Preorder preorder4 =
        new Preorder.PreorderBuilder()
            .imageUrl("https://cdns.kinguin.net/media/region/asia.jpg")
            .regularPrice(new BigDecimal("150"))
            .minPrice(new BigDecimal("101"))
            .preorderId(14)
            .name("Carol")
            .build();
    Preorder preorder2 =
        new Preorder.PreorderBuilder()
            .imageUrl("https://cdns.kinguin.net/media/region/africa.jpg")
            .regularPrice(new BigDecimal("800"))
            .minPrice(new BigDecimal("200"))
            .preorderId(12)
            .name("Alice")
            .build();
    preorder =
        new Preorder.PreorderBuilder()
            .imageUrl("https://cdns.kinguin.net/media/region/america.jpg")
            .regularPrice(new BigDecimal("2"))
            .minPrice(new BigDecimal("2"))
            .preorderId(11)
            .name("Bob")
            .build();
    List<Preorder> preorders = new ArrayList<Preorder>();
    preorders.add(preorder);
    preorders.add(preorder2);
    preorders.add(preorder3);
    preorders.add(preorder4);

    URL preorderApiUrl = new URL(PREORDER_API_URL);
    HttpURLConnection connection = (HttpURLConnection) preorderApiUrl.openConnection();
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(3000);
    connection.connect();
    assertEquals(connection.getResponseCode(), 200);
  }

  @Test
  public void givenControllerWhenAskingForPreordersThenSuccess() throws Exception {
    // given

    // when

    when(preorderService.getPreordersList(1, 4, "")).thenReturn(preorders);
    // then
    mockMvc
        .perform(get("/preorders/1/4/").accept(MediaTypes.HAL_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"));
  }

  @Test
  public void givenControllerWhenAskingForPreordersWithoutParam() throws Exception {
    // given
    int pageSize = 1;
    int pageLimitNumber = 1;
    String sortingParam = "";

    // when then
    mockMvc
        .perform(get("/preorders/1/2/").accept(MediaTypes.HAL_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"));
  }

  @Test
  public void givenControllerWhenAskingForDetailedExistingPreorderThenSuccess() throws Exception {
    // given
    int providerId = 2314;

    // when then
    mockMvc
        .perform(get("/preorders/preorder/" + providerId).accept(MediaTypes.HAL_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"));
  }

  @Test
  public void givenControllerWhenAskingForNonExistingPreorderThen404() throws Exception {
    // given
    int nonExistingProviderId = 87621379;

    mockMvc
        .perform(get("/preorders/preorder/" + nonExistingProviderId).accept(MediaTypes.HAL_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isNoContent());
  }
}

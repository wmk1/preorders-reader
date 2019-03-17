package com.wkalinski.preorder.controller;

import com.wkalinski.preorder.PreorderApplication;
import com.wkalinski.preorder.controller.PreorderController;
import com.wkalinski.preorder.domain.Preorder;
import com.wkalinski.preorder.service.PreorderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;
import java.util.Arrays;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan
@SpringBootTest
@WebAppConfiguration
public class PreorderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PreorderController preorderController;

    @Mock
    private PreorderService preorderService;

    private static final String PREORDER_API_URL="https://api.kinguin.net/v1/catalog/preorders";

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(preorderController).build();
        mockMvc.perform(get(PREORDER_API_URL))
                .andExpect(status().is(200));
    }

    @Test
    public void givenControllerWhenAskingForPreordersThenSuccess() throws Exception {
        //given

        //when then
        mockMvc.perform(get("/preorders").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"));

    }

    @Test
    public void givenControllerWhenAskingForPreordersWithoutParam() throws Exception {
        //given
        int pageSize = 1;
        int pageLimitNumber = 1;
        String sortingParam = "";

        //when then
        mockMvc.perform(get("/preorders").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void givenControllerWhenAskingForDetailedPreorderThenSuccess() throws Exception {
        //given
        int providerId = 2314;

        //when then
        mockMvc.perform(get("/preorders/preorder/").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"));

    }
}

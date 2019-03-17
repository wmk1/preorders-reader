package com.wkalinski.preorder.service;

import com.wkalinski.preorder.domain.Preorder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan
@SpringBootTest
@WebAppConfiguration
public class PreorderServiceTest {

    @InjectMocks
    private PreorderService preorderService = mock(PreorderService.class);

    private Preorder preorder;

    private List<Preorder> preorders = new ArrayList<Preorder>();

    @Before
    public void setUp() {
        Preorder preorder3 = new Preorder.PreorderBuilder()
                .imageUrl("https://cdns.kinguin.net/media/region/europe.jpg")
                .regularPrice(new BigDecimal("300"))
                .minPrice(new BigDecimal("400"))
                .preorderId(13)
                .name("David")
                .build();
        Preorder preorder4 = new Preorder.PreorderBuilder()
                .imageUrl("https://cdns.kinguin.net/media/region/asia.jpg")
                .regularPrice(new BigDecimal("150"))
                .minPrice(new BigDecimal("101"))
                .preorderId(14)
                .name("Carol")
                .build();
        Preorder preorder2 = new Preorder.PreorderBuilder()
                .imageUrl("https://cdns.kinguin.net/media/region/africa.jpg")
                .regularPrice(new BigDecimal("800"))
                .minPrice(new BigDecimal("200"))
                .preorderId(12)
                .name("Alice")
                .build();
        preorder = new Preorder.PreorderBuilder()
                .imageUrl("https://cdns.kinguin.net/media/region/america.jpg")
                .regularPrice(new BigDecimal("2"))
                .minPrice(new BigDecimal("2"))
                .preorderId(11)
                .name("Bob")
                .build();
       preorders = new ArrayList<>();
       preorders.add(preorder);
       preorders.add(preorder2);
       preorders.add(preorder3);
       preorders.add(preorder4);
    }

    @Test
    public void givenServiceWhenAskingForExistingPreordersListThenPreorderIsReturned() {
        //given
        int pageNumber = 2;
        int pageSize = 2;

        //when
        when(preorderService.getPreordersList(pageNumber, pageSize, "")).thenReturn(preorders);

        //then
        assertEquals(preorders.size(), 4);
    }

    @Test
    public void givenServiceWhenFilteringPreordersByNameThenPreorderSortedByNameIsReturned() {
        //given
        int pageNumber = 1;
        int pageSize = 10;
        String sortingParam = "price";

        //when
        when(preorderService.getPreordersList(pageNumber, pageSize, sortingParam)).thenReturn(preorders);

        //then
        assertEquals(preorders.size(), 4);
    }

    @Test
    public void givenIdForExistingPreorderWhenFindingThenSinglePreorderIsReturned() {
        //given
        int existingProviderId = 11;

        when(preorderService.showDetailedPreorder(existingProviderId)).thenReturn(preorder);

        //then
        assertNotNull(preorder);
        assertEquals(preorder.getPreorderId(), existingProviderId);
        assertEquals(preorder.getImageUrl(), "https://cdns.kinguin.net/media/region/america.jpg");
        assertEquals(preorder.getMinPrice(), new BigDecimal(2));
        assertEquals(preorder.getRegularPrice(), new BigDecimal(2));

    }

    @Test
    public void givenIdForNonExistingProviderWhenFindingThenThrowIsReturned() {
        //given
        int nonExistingPreorder = 123;

        //when
        Preorder singlePreorder = preorderService.showDetailedPreorder(nonExistingPreorder);

        //then
        assertNull(singlePreorder);
    }

}
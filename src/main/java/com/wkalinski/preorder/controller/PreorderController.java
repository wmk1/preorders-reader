package com.wkalinski.preorder.controller;

import com.wkalinski.preorder.domain.Preorder;
import com.wkalinski.preorder.service.PreorderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
public class PreorderController {

    /*
    * TODO: Check sorting by regular price param and name,
    *       Check HAL format
    *       Finally run some controller methods...
    *       Write tests for service and others
    *       Perhaps clean this shit
    *
    * */

    private static final String HAL_MEDIA_TYPE = "application/hal+json";

    @Autowired
    private final PreorderService preorderService;

    public PreorderController(PreorderService preorderService) {
        this.preorderService = checkNotNull(preorderService);
    }

    @RequestMapping(value = "/preorders/{page}/{size}/", method = GET, produces =  HAL_MEDIA_TYPE)
    @ResponseBody
    public ResponseEntity listAllPreorders(@PathVariable("page") int page,
                                           @PathVariable(value = "size") int size,
                                           @PathVariable(value = "sortingParam", required = false) String sortingParam) {
        List<Preorder> preorders = preorderService.getPreordersList(page, size, sortingParam);
        for (Preorder p : preorders) {
            p.add(linkTo(methodOn(PreorderController.class).showDetailedPreorder(p.getPreorderId())).withSelfRel());
        }
        return new ResponseEntity(preorders, HttpStatus.OK);
    }


    @RequestMapping(value = "/preorders/preorder/{id}", method = GET, produces = HAL_MEDIA_TYPE)
    @ResponseBody
    public ResponseEntity showDetailedPreorder(@PathVariable("id") int preorderId) {
        return new ResponseEntity(preorderService.showDetailedPreorder(preorderId), HttpStatus.OK);
    }
}

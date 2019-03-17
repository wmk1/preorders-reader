package com.wkalinski.preorder.controller;

import com.wkalinski.preorder.domain.Preorder;
import com.wkalinski.preorder.service.PreorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping
public class PreorderController {

  private static final String HAL_MEDIA_TYPE = "application/hal+json";

  @Autowired private final PreorderService preorderService;

  public PreorderController(PreorderService preorderService) {
    this.preorderService = checkNotNull(preorderService);
  }

  @RequestMapping(
      value = "/preorders/{page}/{size}",
      method = GET,
      produces = HAL_MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity listAllPreordersWithoutSort(
      @PathVariable("page") int page,
      @PathVariable(value = "size") int size) {

    List<Preorder> preorders = preorderService.getPreordersList(page, size, "");
    for (Preorder p : preorders) {
      p.add(
          linkTo(methodOn(PreorderController.class).showDetailedPreorder(p.getPreorderId()))
              .withSelfRel());
    }
    return new ResponseEntity(preorders, HttpStatus.OK);
  }

  @RequestMapping(
          value = "/preorders/{page}/{size}/{sortingParam}",
          method = GET,
          produces = HAL_MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity listAllPreorders(
          @PathVariable("page") int page,
          @PathVariable(value = "size") int size,
          @PathVariable(value = "sortingParam", required = false) String sortingParam) {

    List<Preorder> preorders = preorderService.getPreordersList(page, size, sortingParam);
    for (Preorder p : preorders) {
      p.add(
              linkTo(methodOn(PreorderController.class).showDetailedPreorder(p.getPreorderId()))
                      .withSelfRel());
    }
    return new ResponseEntity(preorders, HttpStatus.OK);
  }


  @RequestMapping(value = "/preorders/preorder/{id}", method = GET, produces = HAL_MEDIA_TYPE)
  @ResponseBody
  public ResponseEntity showDetailedPreorder(@PathVariable("id") int preorderId) {
    Preorder preorder = preorderService.showDetailedPreorder(preorderId);
    if (preorder == null) {
      return new ResponseEntity(preorder, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(preorderService.showDetailedPreorder(preorderId), HttpStatus.OK);
  }
}

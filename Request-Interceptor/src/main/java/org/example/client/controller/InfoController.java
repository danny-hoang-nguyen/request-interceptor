package org.example.client.controller;

import org.example.client.response.model.SummaryResponse;
import org.example.client.service.ISimpleGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

  @Autowired
  ISimpleGetService iSimpleGetService;

  @GetMapping(value = "info")
  public ResponseEntity<SummaryResponse> getSummaryResponse() {
    return new ResponseEntity<>(iSimpleGetService.getSummaryResponse(), HttpStatus.OK);
  }
}

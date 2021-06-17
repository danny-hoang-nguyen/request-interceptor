package org.example.client.request;

import org.example.client.configuration.Configuration;
import org.example.client.response.model.SummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "thirdPartyClient", url = "https://api.covid19api.com", configuration = Configuration.class)
public interface ThirdPartyClient {

  String BASE_PAYMENT_URL = "/";
  String SUMMARY_PATH = BASE_PAYMENT_URL + "summary";

  @RequestMapping(method = RequestMethod.GET, path = SUMMARY_PATH)
  SummaryResponse getCovidSummary();
}

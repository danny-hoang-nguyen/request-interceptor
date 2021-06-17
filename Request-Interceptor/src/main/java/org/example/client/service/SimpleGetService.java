package org.example.client.service;

import org.example.client.request.ThirdPartyClient;
import org.example.client.response.model.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleGetService implements ISimpleGetService {

  @Autowired
  ThirdPartyClient thirdPartyClient;

  @Override
  public SummaryResponse getSummaryResponse() {
    return thirdPartyClient.getCovidSummary();
  }
}

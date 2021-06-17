package org.example.client.response.model;

import java.util.ArrayList;
import org.example.client.request.SignedRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SummaryResponse implements SignedRequest, Serializable {

  @JsonProperty("ID")
  private String id;
  @JsonProperty("Message")
  private String message;

  @JsonProperty("Global")
  private GlobalSummary global;

  @JsonProperty("Countries")
  private List<CountrySummary> countrySummaries = new ArrayList<>();

  @JsonProperty("Date")
  private String date;

  @Override
  @JsonProperty(SignedRequest.SIGNED_FIELDS_NAME)
  public List<String> getSignedFieldNames() {
    return Collections.emptyList();
  }
}

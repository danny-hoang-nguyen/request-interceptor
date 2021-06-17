package org.example.client.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigInteger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GlobalSummary implements Serializable {

  @JsonProperty("NewConfirmed")
  private BigInteger NewConfirmed;
  @JsonProperty("TotalConfirmed")
  private BigInteger TotalConfirmed;
  @JsonProperty("NewDeaths")
  private BigInteger NewDeaths;
  @JsonProperty("TotalDeaths")
  private BigInteger TotalDeaths;
  @JsonProperty("NewRecovered")
  private BigInteger NewRecovered;
  @JsonProperty("TotalRecovered")
  private BigInteger TotalRecovered;
  @JsonProperty("Date")
  private String Date;
}

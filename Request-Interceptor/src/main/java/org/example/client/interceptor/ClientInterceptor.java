package org.example.client.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.client.request.SignedRequest;
import org.example.client.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ClientInterceptor implements RequestInterceptor {

  private static final String POST_METHOD = "POST";
  private static final String GET_METHOD = "GET";

  private final static String SIGNATURE_FIELD = "signature";

  @Autowired
  private ObjectMapper objectMapper;

  @Value("client_secret_key")
  private String secretKey;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    final String requestType = requestTemplate.method();
    log.info("Starting intercept request [{}]", requestType);
    try {
      switch (requestType) {
        case GET_METHOD:
          interceptGet(requestTemplate);
          break;
        case POST_METHOD:
          interceptPost(requestTemplate);
          break;
        default: {
          log.info("Not trying to inject something");
          break;
        }
      }

    } catch (final IOException e) {
      throw new IllegalStateException("Cannot parse request to intercept", e);
    }
  }

  private void interceptGet(final RequestTemplate requestTemplate)
      throws IOException {
    final Map<String, Collection<String>> params = requestTemplate.queries();
    if (params.isEmpty() || requestTemplate.body() == null) {
      return;
    }

    final JsonNode originalRequest = objectMapper.readTree(new String(requestTemplate.body()))
        .get(SignedRequest.SIGNED_FIELDS_NAME);
    final List<String> fieldValues = new LinkedList<>();
    originalRequest.forEach(jsonNode -> {
      if (jsonNode.textValue() != null) {
        final Collection<String> inRequestBody = params.get(jsonNode.textValue());
        if (inRequestBody != null) {
          fieldValues.addAll(inRequestBody);
        }
      }
    });
    fieldValues.add(secretKey);
    final String signedDigest = SignatureUtil.signSha256Hex(fieldValues);

    requestTemplate.query(SIGNATURE_FIELD, signedDigest);
    requestTemplate.body(StringUtils.EMPTY);
  }


  private void interceptPost(final RequestTemplate requestTemplate)
      throws IOException {
    JsonNode originalRequest = objectMapper.readTree(new String(requestTemplate.body()));
    final JsonNode signedFields = originalRequest.get(SignedRequest.SIGNED_FIELDS_NAME);
    final JsonNode finalActualObj = originalRequest;

    final List<String> fieldValues = new LinkedList<>();
    signedFields.forEach(jsonNode -> {
      if (jsonNode.textValue() != null) {
        final JsonNode jsonNodeInRequestBody = finalActualObj.get(jsonNode.textValue());
        if (jsonNodeInRequestBody != null) {
          final String fieldValue = jsonNodeInRequestBody.asText();
          fieldValues.add(fieldValue);
        }
      }
    });

    fieldValues.add(secretKey);
    final String signedDigest = SignatureUtil.signSha256Hex(fieldValues);

    ((ObjectNode) originalRequest).remove(SignedRequest.SIGNED_FIELDS_NAME);
    originalRequest = ((ObjectNode) originalRequest).put(SIGNATURE_FIELD, signedDigest);

    requestTemplate
        .body(originalRequest.toString().getBytes(), StandardCharsets.UTF_8);
  }
}

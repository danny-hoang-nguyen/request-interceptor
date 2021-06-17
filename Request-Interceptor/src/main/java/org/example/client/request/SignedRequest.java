package org.example.client.request;

import java.util.List;

public interface SignedRequest {

  String SIGNED_FIELDS_NAME = "signedFields";

  List<String> getSignedFieldNames();
}

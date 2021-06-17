package org.example.client.util;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

public class SignatureUtil {

  private static final String DASH = "-";

  /**
   * @param values list of which should be consumed for signing
   * @return the final value of "signature" key
   */
  public static String signSha256Hex(final List<String> values) {
    final String unsignedDigest = getUnsignedDigest(values);
    return DigestUtils.sha256Hex(unsignedDigest);
  }

  /**
   * @param values list of fields which should be consumed for signing
   * @return correct final string including all fields and following convention
   */
  private static String getUnsignedDigest(final List<String> values) {
    final StringBuilder stringBuilder = new StringBuilder();
    for (String value : values) {
      stringBuilder.append(value);
      stringBuilder.append(DASH);
    }
    return stringBuilder.toString();
  }
}

package io.ashdavies.eternity.google;

import com.google.android.gms.common.ConnectionResult;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionFailedException extends Throwable {

  private static final String CONNECTION_RESULT = "ConnectionResult";
  private static final String STATUS_CODE = "statusCode";

  private final ConnectionResult result;

  public ConnectionFailedException(ConnectionResult result) {
    super(getStatusCode(result));
    this.result = result;
  }

  public ConnectionResult getResult() {
    return result;
  }

  private static String getStatusCode(ConnectionResult result) {
    try {
      return new JSONObject(result.toString().substring(CONNECTION_RESULT.length())).getString(STATUS_CODE);
    } catch (JSONException ignored) {
      return "PARSE_EXCEPTION";
    }
  }
}

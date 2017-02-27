package io.ashdavies.eternity.google;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class GoogleSignInException extends Throwable {

  private final GoogleSignInResult result;

  public GoogleSignInException(GoogleSignInResult result) {
    super(result.getStatus().getStatusMessage());
    this.result = result;
  }

  public GoogleSignInResult getResult() {
    return result;
  }
}

package io.ashdavies.eternity.signin;

import android.content.Intent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

class GoogleSignInResultSingle implements SingleOnSubscribe<GoogleSignInAccount> {

  private final GoogleSignInResult result;

  private GoogleSignInResultSingle(GoogleSignInResult result) {
    this.result = result;
  }

  static Single<GoogleSignInAccount> from(Intent data) {
    return Single.create(new GoogleSignInResultSingle(Auth.GoogleSignInApi.getSignInResultFromIntent(data)));
  }

  @Override
  public void subscribe(SingleEmitter<GoogleSignInAccount> emitter) throws Exception {
    if (!result.isSuccess()) {
      emitter.onError(new GoogleSignInException(result));
      return;
    }

    emitter.onSuccess(result.getSignInAccount());
  }

  private static class GoogleSignInException extends Throwable {

    private final GoogleSignInResult result;

    GoogleSignInException(GoogleSignInResult result) {
      super(result.getStatus().getStatusMessage());
      this.result = result;
    }

    public GoogleSignInResult getResult() {
      return result;
    }
  }
}

package io.ashdavies.eternity.signin;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import io.ashdavies.eternity.R;
import io.ashdavies.eternity.google.GoogleApiProcessor;
import javax.inject.Inject;

public class GoogleSignInApi extends GoogleApiProcessor {

  private final GoogleApiClient client;

  @Inject
  GoogleSignInApi(Context context) {
    client = new GoogleApiClient.Builder(context)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, createGoogleSignInOptions(context))
        .build();
  }

  private GoogleSignInOptions createGoogleSignInOptions(Context context) {
    return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
  }

  Intent getSignInIntent() {
    return Auth.GoogleSignInApi.getSignInIntent(client);
  }

  void signOut() {
    Auth.GoogleSignInApi.signOut(client);
  }

  @Override
  public void connect() {
    client.connect();
  }

  @Override
  public void disconnect() {
    client.disconnect();
  }
}

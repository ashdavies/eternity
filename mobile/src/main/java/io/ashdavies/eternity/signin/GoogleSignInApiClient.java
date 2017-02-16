package io.ashdavies.eternity.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import io.ashdavies.eternity.R;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.ReplayProcessor;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.reactivestreams.Subscription;

public class GoogleSignInApiClient implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  private final ReplayProcessor<Event> processor;
  private final GoogleApiClient client;

  @Inject
  GoogleSignInApiClient(Context context) {
    client = new GoogleApiClient.Builder(context)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, createGoogleSignInOptions(context))
        .build();

    processor = ReplayProcessor.create();
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

  public void onConnected(@Nullable Bundle bundle) {
    processor.onNext(Event.CONNECTED);
  }

  @Override
  public void onConnectionSuspended(int cause) {
    processor.onNext(Event.SUSPENDED);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult result) {
    processor.onError(new Throwable(result.getErrorMessage()));
  }

  Flowable<Event> onConnectionEvent() {
    return processor
        .doOnSubscribe(new Consumer<Subscription>() {
          @Override
          public void accept(@NonNull Subscription subscription) throws Exception {
            client.connect();
          }
        })
        .doOnCancel(new Action() {
          @Override
          public void run() throws Exception {
            client.disconnect();
          }
        });
  }

  void signOut() {
    Auth.GoogleSignInApi.signOut(client);
  }

  enum Event {
    CONNECTED,
    SUSPENDED
  }
}

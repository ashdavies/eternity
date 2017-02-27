package io.ashdavies.eternity.chat;

import android.content.Context;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.common.api.GoogleApiClient;
import io.ashdavies.eternity.google.GoogleApiProcessor;
import javax.inject.Inject;

class GoogleInviteApi extends GoogleApiProcessor {

  private final GoogleApiClient client;

  @Inject
  GoogleInviteApi(Context context) {
    client = new GoogleApiClient.Builder(context)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(AppInvite.API)
        .build();
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

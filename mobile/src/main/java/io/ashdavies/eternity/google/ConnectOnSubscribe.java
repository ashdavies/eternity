package io.ashdavies.eternity.google;

import android.support.annotation.NonNull;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscription;

class ConnectOnSubscribe implements Consumer<Subscription> {

  private final GoogleApiProcessor api;

  ConnectOnSubscribe(GoogleApiProcessor api) {
    this.api = api;
  }

  @Override
  public void accept(@NonNull Subscription subscription) throws Exception {
    api.connect();
  }
}

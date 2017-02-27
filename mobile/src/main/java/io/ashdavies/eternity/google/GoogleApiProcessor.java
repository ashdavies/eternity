package io.ashdavies.eternity.google;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import io.ashdavies.eternity.rx.ConnectOnSubscribe;
import io.ashdavies.eternity.rx.DisconnectOnCancel;
import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;
import javax.annotation.Nullable;

public abstract class GoogleApiProcessor implements ConnectOnSubscribe.Connectable, DisconnectOnCancel.Disconnectable, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

  private final ReplayProcessor<Event> processor;

  protected GoogleApiProcessor() {
    this(ReplayProcessor.<GoogleApiProcessor.Event>create());
  }

  private GoogleApiProcessor(ReplayProcessor<GoogleApiProcessor.Event> processor) {
    this.processor = processor;
  }

  public void onConnected(@Nullable Bundle bundle) {
    processor.onNext(GoogleApiProcessor.Event.CONNECTED);
  }

  @Override
  public void onConnectionSuspended(int cause) {
    processor.onNext(GoogleApiProcessor.Event.SUSPENDED);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult result) {
    processor.onError(new ConnectionFailedException(result));
  }

  public Flowable<Event> onConnectionEvent() {
    return processor
        .doOnSubscribe(new ConnectOnSubscribe(this))
        .doOnCancel(new DisconnectOnCancel(this));
  }

  public enum Event {
    CONNECTED,
    SUSPENDED
  }
}

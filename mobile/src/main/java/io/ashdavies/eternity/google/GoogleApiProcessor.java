package io.ashdavies.eternity.google;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;
import javax.annotation.Nullable;

public abstract class GoogleApiProcessor implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  private final ReplayProcessor<Event> processor;

  protected GoogleApiProcessor() {
    this(ReplayProcessor.create());
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
        .doOnSubscribe(subscription -> connect())
        .doOnCancel(this::disconnect);
  }

  protected abstract void connect();

  protected abstract void disconnect();

  public enum Event {
    CONNECTED,
    SUSPENDED
  }
}

package io.ashdavies.eternity.rx;

import android.support.annotation.NonNull;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscription;

public class ConnectOnSubscribe implements Consumer<Subscription> {

  private final Connectable connectable;

  public ConnectOnSubscribe(Connectable connectable) {
    this.connectable = connectable;
  }

  @Override
  public void accept(@NonNull Subscription subscription) throws Exception {
    connectable.connect();
  }

  public interface Connectable {

    void connect();
  }
}

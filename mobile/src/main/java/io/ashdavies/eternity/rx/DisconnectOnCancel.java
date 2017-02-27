package io.ashdavies.eternity.rx;

import io.reactivex.functions.Action;

public class DisconnectOnCancel implements Action {

  private final Disconnectable disconnectable;

  public DisconnectOnCancel(Disconnectable disconnectable) {
    this.disconnectable = disconnectable;
  }

  @Override
  public void run() throws Exception {
    disconnectable.disconnect();
  }

  public interface Disconnectable {

    void disconnect();
  }
}

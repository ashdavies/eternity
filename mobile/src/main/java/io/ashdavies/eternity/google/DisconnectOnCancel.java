package io.ashdavies.eternity.google;

import io.reactivex.functions.Action;

class DisconnectOnCancel implements Action {

  private final GoogleApiProcessor api;

  DisconnectOnCancel(GoogleApiProcessor api) {
    this.api = api;
  }

  @Override
  public void run() throws Exception {
    api.disconnect();
  }
}

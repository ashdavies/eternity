package io.ashdavies.eternity.firebase;

import io.ashdavies.eternity.Logger;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

class LoggerError implements Consumer<Throwable> {

  private final Logger logger;

  LoggerError(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void accept(@NonNull Throwable throwable) throws Exception {
    logger.error(throwable);
  }
}

package io.ashdavies.eternity;

import android.os.Bundle;

public interface Logger {

  void log(String message, Bundle bundle);

  void error(Throwable throwable, Object... args);

  interface Preparable {

    void prepare(Logger logger);
  }
}

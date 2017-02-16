package io.ashdavies.eternity;

public interface Logger {

  void error(Throwable throwable, Object... args);

  interface Preparable {

    void prepare(Logger logger);
  }
}

package io.ashdavies.eternity;

public interface Logger {

  void log(String message);

  void error(Throwable throwable, Object... args);

  interface Preparable {

    void prepare(Logger logger);
  }
}

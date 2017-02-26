package io.ashdavies.eternity;

public interface Config extends Logger.Preparable {

  boolean favouriteEnabled();

  boolean repostEnabled();

  interface State {

    boolean invalidated();

    void invalidate();

    void reset();
  }
}

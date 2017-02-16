package io.ashdavies.eternity.inject;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class TypeModule<T> {

  private final T t;

  public TypeModule(T t) {
    this.t = t;
  }

  @Provides
  public T t() {
    return t;
  }
}

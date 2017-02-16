package io.ashdavies.eternity.inject;

import dagger.MembersInjector;

public interface TypeComponent<T> extends MembersInjector<T> {

  interface Builder<T, Component extends TypeComponent<T>, Module extends TypeModule<T>> {

    TypeComponent.Builder<T, Component, Module> plus(Module module);

    Component build();
  }
}

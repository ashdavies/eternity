package io.ashdavies.eternity.inject;

import java.util.Map;

abstract class TypeComponentService<T> implements HasTypeComponent<T> {

  private final Map<Class<? extends T>, TypeComponent.Builder> builders;

  TypeComponentService(Map<Class<? extends T>, TypeComponent.Builder> builders) {
    this.builders = builders;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <F extends T> TypeComponent.Builder<F, TypeComponent<F>, TypeModule<F>> getBuilder(Class<F> kls) {
    return builders.get(kls);
  }
}

package io.ashdavies.eternity.inject;

public interface HasTypeComponent<T> {

  <F extends T> TypeComponent.Builder<F, TypeComponent<F>, TypeModule<F>> getBuilder(Class<F> kls);
}

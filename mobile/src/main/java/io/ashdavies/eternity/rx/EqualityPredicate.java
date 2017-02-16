package io.ashdavies.eternity.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

public class EqualityPredicate<T> implements Predicate<T> {

  private final T t;

  public EqualityPredicate(T t) {
    this.t = t;
  }

  @Override
  public boolean test(@NonNull T t) throws Exception {
    return this.t.equals(t);
  }
}

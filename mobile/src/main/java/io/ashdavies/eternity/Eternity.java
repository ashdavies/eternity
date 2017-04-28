package io.ashdavies.eternity;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.ashdavies.eternity.inject.DaggerApplicationComponent;

public class Eternity extends DaggerApplication {

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerApplicationComponent.builder().create(this);
  }
}

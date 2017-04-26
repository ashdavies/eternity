package io.ashdavies.eternity;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class Eternity extends DaggerApplication {

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerApplicationComponent.create();
  }
}

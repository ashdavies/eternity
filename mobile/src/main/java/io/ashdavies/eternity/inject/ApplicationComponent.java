package io.ashdavies.eternity.inject;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.ashdavies.eternity.Eternity;
import io.ashdavies.eternity.firebase.FirebaseModule;

@Component(
    modules = {
        ApplicationModule.class,
        AndroidBindingModule.class,
        AndroidSupportInjectionModule.class,
        FirebaseModule.class
    }
)
@ApplicationScope
public interface ApplicationComponent extends AndroidInjector<Eternity> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<Eternity> {
  }
}

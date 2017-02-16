package io.ashdavies.eternity.inject;

import dagger.Component;
import dagger.MembersInjector;
import io.ashdavies.eternity.Eternity;
import io.ashdavies.eternity.firebase.FirebaseModule;

@ApplicationScope
@Component(
    modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        FirebaseModule.class
    }
)
public interface ApplicationComponent extends MembersInjector<Eternity> {
}

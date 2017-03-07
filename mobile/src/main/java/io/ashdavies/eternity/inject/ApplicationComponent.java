package io.ashdavies.eternity.inject;

import dagger.Component;
import dagger.MembersInjector;
import dagger.android.AndroidInjectionModule;
import io.ashdavies.eternity.Eternity;
import io.ashdavies.eternity.chat.ChatModule;
import io.ashdavies.eternity.firebase.FirebaseModule;
import io.ashdavies.eternity.signin.SignInModule;

@ApplicationScope
@Component(
    modules = {
        ApplicationModule.class,
        AndroidInjectionModule.class,
        ChatModule.class,
        SignInModule.class,
        FirebaseModule.class
    }
)
public interface ApplicationComponent extends MembersInjector<Eternity> {
}

package io.ashdavies.eternity.signin;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = SignInComponent.class)
public abstract class SignInModule {

  @Binds
  @IntoMap
  @ActivityKey(SignInActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> signInActivityInjectorFactory(SignInComponent.Builder builder);
}

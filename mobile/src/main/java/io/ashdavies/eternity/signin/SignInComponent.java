package io.ashdavies.eternity.signin;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.ashdavies.eternity.inject.ActivityScope;

@ActivityScope
@Subcomponent
public interface SignInComponent extends AndroidInjector<SignInActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<SignInActivity> {
  }
}

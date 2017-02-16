package io.ashdavies.eternity.signin;

import dagger.Subcomponent;
import io.ashdavies.eternity.inject.ActivityScope;
import io.ashdavies.eternity.inject.TypeComponent;

@ActivityScope
@Subcomponent(modules = SignInModule.class)
public interface SignInComponent extends TypeComponent<SignInActivity> {

  @Subcomponent.Builder
  interface Builder extends TypeComponent.Builder<SignInActivity, SignInComponent, SignInModule> {
  }
}

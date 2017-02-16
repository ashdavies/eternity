package io.ashdavies.eternity.inject;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.ashdavies.eternity.chat.ChatActivity;
import io.ashdavies.eternity.chat.ChatComponent;
import io.ashdavies.eternity.signin.SignInActivity;
import io.ashdavies.eternity.signin.SignInComponent;

@Module(
    subcomponents = {
        ChatComponent.class,
        SignInComponent.class
    }
)
public abstract class ActivityBindingModule {

  @Binds
  @IntoMap
  @ActivityKey(ChatActivity.class)
  public abstract TypeComponent.Builder chatComponentBuilder(ChatComponent.Builder builder);

  @Binds
  @IntoMap
  @ActivityKey(SignInActivity.class)
  public abstract TypeComponent.Builder loginComponentBuilder(SignInComponent.Builder builder);
}

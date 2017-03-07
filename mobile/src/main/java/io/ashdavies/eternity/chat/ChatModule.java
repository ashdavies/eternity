package io.ashdavies.eternity.chat;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ChatComponent.class)
public abstract class ChatModule {

  @Binds
  @IntoMap
  @ActivityKey(ChatActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> chatActivityInjectorFactory(ChatComponent.Builder builder);
}

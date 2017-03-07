package io.ashdavies.eternity.chat;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.ashdavies.eternity.inject.ActivityScope;

@ActivityScope
@Subcomponent
public interface ChatComponent extends AndroidInjector<ChatActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<ChatActivity> {
  }
}

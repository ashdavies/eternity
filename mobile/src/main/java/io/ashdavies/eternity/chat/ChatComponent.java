package io.ashdavies.eternity.chat;

import dagger.Subcomponent;
import io.ashdavies.eternity.inject.ActivityScope;
import io.ashdavies.eternity.inject.TypeComponent;

@ActivityScope
@Subcomponent(modules = ChatModule.class)
public interface ChatComponent extends TypeComponent<ChatActivity> {

  @Subcomponent.Builder
  interface Builder extends TypeComponent.Builder<ChatActivity, ChatComponent, ChatModule> {
  }
}

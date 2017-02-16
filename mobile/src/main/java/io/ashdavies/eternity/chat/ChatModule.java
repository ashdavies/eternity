package io.ashdavies.eternity.chat;

import dagger.Module;
import io.ashdavies.eternity.inject.TypeModule;

@Module
public class ChatModule extends TypeModule<ChatActivity> {

  ChatModule(ChatActivity activity) {
    super(activity);
  }
}

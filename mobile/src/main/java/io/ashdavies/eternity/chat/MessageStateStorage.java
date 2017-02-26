package io.ashdavies.eternity.chat;

import android.content.Context;
import android.content.SharedPreferences;
import io.ashdavies.commons.storage.Storage;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

class MessageStateStorage implements Storage<String, MessageState> {

  private static final String SHARED_PREFERENCES_NAME = "io.ashdavies.eternity.message.state";

  private final SharedPreferences preferences;

  @Inject
  MessageStateStorage(Context context) {
    this(context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE));
  }

  private MessageStateStorage(SharedPreferences preferences) {
    this.preferences = preferences;
  }

  @Override
  public MessageState get(String uuid) throws IndexNotFoundException {
    return MessageState.create(uuid, preferences.getBoolean(uuid, false));
  }

  @Override
  public Collection<MessageState> getAll() {
    return Collections.emptyList();
  }

  @Override
  public void put(MessageState state, Resolver<String, MessageState> resolver) {
    preferences.edit().putBoolean(resolver.resolve(state), state.favourite()).apply();
  }
}

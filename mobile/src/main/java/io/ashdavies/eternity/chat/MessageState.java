package io.ashdavies.eternity.chat;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class MessageState {

  public abstract String uuid();
  public abstract boolean favourite();

  public static MessageState create(String uuid, boolean favourite) {
    return new AutoValue_MessageState(uuid, favourite);
  }
}

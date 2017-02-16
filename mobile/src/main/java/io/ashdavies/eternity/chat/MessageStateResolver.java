package io.ashdavies.eternity.chat;

import io.ashdavies.commons.storage.Storage;

class MessageStateResolver implements Storage.Resolver<String, MessageState> {

  @Override
  public String resolve(MessageState value) {
    return value.uuid();
  }
}

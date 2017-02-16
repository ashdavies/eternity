package io.ashdavies.eternity.chat;

import javax.annotation.Nullable;

interface MessageListener {

  void post(String string, @Nullable Message message);

  void favourite(Message message, boolean favourite);
}

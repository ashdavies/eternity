package io.ashdavies.eternity.chat;

import javax.annotation.Nullable;

interface MessageListener {

  boolean favouriteEnabled();

  void favourite(Message message, boolean favourite);

  boolean repostEnabled();

  void post(String string, @Nullable Message message);
}

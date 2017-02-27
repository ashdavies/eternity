package io.ashdavies.eternity.chat;

import io.ashdavies.eternity.domain.Message;
import javax.annotation.Nullable;

interface MessageListener {

  boolean favouriteEnabled();

  void favourite(Message message, boolean favourite);

  boolean repostEnabled();

  void post(String string, @Nullable Message message);
}

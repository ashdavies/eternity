package io.ashdavies.eternity.chat;

import io.ashdavies.eternity.domain.Message;

interface MessageListener {

  boolean favouriteEnabled();

  void favourite(Message message, boolean favourite);

  boolean repostEnabled();

  void post(String string);

  void repost(Message original);
}

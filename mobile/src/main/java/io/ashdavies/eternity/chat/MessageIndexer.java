package io.ashdavies.eternity.chat;

import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

class MessageIndexer implements Consumer<Message> {

  private static final String MESSAGE_URL = "https://eternity.ashdavies.io/message";

  private final FirebaseAppIndex index;
  private final FirebaseUser user;

  @Inject
  MessageIndexer() {
    this(FirebaseAppIndex.getInstance(), FirebaseAuth.getInstance().getCurrentUser());
  }

  private MessageIndexer(FirebaseAppIndex index, FirebaseUser user) {
    this.index = index;
    this.user = user;
  }

  @Override
  public void accept(@NonNull Message message) throws Exception {
    index.update(getIndexable(message));
  }

  private Indexable getIndexable(Message message) {
    return Indexables.messageBuilder()
        .setName(message.text())
        .setUrl(String.format("%s/%s", MESSAGE_URL, message.uuid()))
        .setRecipient(getRecipient(message))
        .setSender(getSender(message))
        .build();
  }

  private PersonBuilder getRecipient(Message message) {
    return Indexables.personBuilder()
        .setName(message.author().name())
        .setUrl(String.format("%s/%s/recipient", MESSAGE_URL, message.uuid()));
  }

  private PersonBuilder getSender(Message message) {
    return Indexables.personBuilder()
        .setIsSelf(user.getUid().equals(message.author().uuid()))
        .setName(message.author().name())
        .setUrl(String.format("%s/%s/sender", MESSAGE_URL, message.uuid()));
  }
}

package io.ashdavies.eternity.chat;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.auth.FirebaseAuth;
import io.ashdavies.commons.util.StringUtils;
import io.ashdavies.eternity.domain.Message;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

class MessageIndexer implements Consumer<Message> {

  private static final String MESSAGE_URL = "https://eternity.ashdavies.io/message";

  @Override
  public void accept(@NonNull Message message) throws Exception {
    FirebaseAppIndex.getInstance().update(getIndexable(message));
    FirebaseUserActions.getInstance().end(getAction(message));
  }

  private Indexable getIndexable(Message message) {
    return Indexables.messageBuilder()
        .setName(message.text())
        .setUrl(getUrl(message.uuid()))
        .setRecipient(getRecipient(message))
        .setSender(getSender(message))
        .build();
  }

  private String getUrl(String... queries) {
    return MESSAGE_URL.concat(StringUtils.join(queries, "/"));
  }

  private Action getAction(Message message) {
    return new Action.Builder(Action.Builder.VIEW_ACTION)
        .setObject(message.author().name(), getUrl(message.uuid()))
        .setMetadata(new Action.Metadata.Builder().setUpload(false))
        .build();
  }

  private PersonBuilder getRecipient(Message message) {
    return Indexables.personBuilder()
        .setName(message.author().name())
        .setUrl(getUrl(message.uuid(), "recipient"));
  }

  private PersonBuilder getSender(Message message) {
    String uid = FirebaseAuth.getInstance()
        .getCurrentUser()
        .getUid();

    return Indexables.personBuilder()
        .setIsSelf(uid.equals(message.author().uuid()))
        .setName(message.author().name())
        .setUrl(getUrl(message.uuid(), "sender"));
  }
}

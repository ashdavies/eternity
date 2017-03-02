package io.ashdavies.eternity.chat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import io.ashdavies.commons.util.StringUtils;
import io.ashdavies.eternity.domain.Message;
import io.ashdavies.rx.repository.Repository;
import io.ashdavies.rx.rxfirebase.ChildEvent;
import io.ashdavies.rx.rxfirebase.RxFirebaseDatabase;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import javax.inject.Inject;

class MessageRepository implements Repository<Message, String> {

  private static final String CHILD_MESSAGES = "messages";
  private static final String CHILD_DIVIDER = "/";

  private static final String QUERY_ORDER = "created";

  private static final int QUERY_LIMIT = 100;

  @Inject
  MessageRepository() {
  }

  @Override
  public Flowable<Message> get(String id) {
    return getInstance(CHILD_MESSAGES, id)
        .onSingleValueEvent()
        .map(new SnapshotMapper())
        .toFlowable();
  }

  @Override
  public Flowable<Message> getAll() {
    Query query = FirebaseDatabase.getInstance()
        .getReference(CHILD_MESSAGES)
        .orderByChild(QUERY_ORDER)
        .limitToFirst(QUERY_LIMIT);

    return RxFirebaseDatabase.with(query)
        .onChildEvent(ChildEvent.Type.CHILD_ADDED)
        .map(new ChildEventMapper());
  }

  @Override
  public Completable put(Message message, Resolver<Message, String> resolver) {
    return getInstance(CHILD_MESSAGES, message.uuid()).setValue(message.toFirebaseValue());
  }

  private RxFirebaseDatabase getInstance(CharSequence... args) {
    return RxFirebaseDatabase.getInstance(StringUtils.join(args, CHILD_DIVIDER));
  }

  private static class ChildEventMapper implements Function<ChildEvent, Message> {

    @Override
    public Message apply(@NonNull ChildEvent event) throws Exception {
      return Message.create(event.snapshot());
    }
  }

  private static class SnapshotMapper implements Function<DataSnapshot, Message> {

    @Override
    public Message apply(DataSnapshot snapshot) throws Exception {
      return Message.create(snapshot);
    }
  }
}

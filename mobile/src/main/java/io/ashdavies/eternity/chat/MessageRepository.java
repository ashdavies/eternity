package io.ashdavies.eternity.chat;

import io.ashdavies.rx.repository.Repository;
import io.ashdavies.rx.rxfirebase.ChildEvent;
import io.ashdavies.rx.rxfirebase.RxFirebaseDatabase;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import javax.inject.Inject;

class MessageRepository implements Repository<Message, String> {

  @Inject
  MessageRepository() {
  }

  @Override
  public Flowable<Message> get(String id) {
    return getInstance("messages/%s", id)
        .onChildEvent(ChildEvent.Type.CHILD_ADDED)
        .map(new Mapper());
  }

  @Override
  public Flowable<Message> getAll() {
    return getInstance("messages")
        .onChildEvent(ChildEvent.Type.CHILD_ADDED)
        .map(new Mapper());
  }

  @Override
  public Completable put(Message message, Resolver<Message, String> resolver) {
    return getInstance("messages/%s", message.uuid()).setValue(message.toFirebaseValue());
  }

  private RxFirebaseDatabase getInstance(String path, Object... args) {
    return RxFirebaseDatabase.getInstance(String.format(path, args));
  }

  private static class Mapper implements Function<ChildEvent, Message> {

    @Override
    public Message apply(ChildEvent event) throws Exception {
      return Message.create(event.snapshot());
    }
  }
}

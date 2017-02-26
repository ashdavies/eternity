package io.ashdavies.eternity.chat;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.ashdavies.commons.presenter.AbstractViewPresenter;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.rx.AbstractViewError;
import io.ashdavies.rx.repository.Repository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.inject.Inject;

class ChatPresenter extends AbstractViewPresenter<ChatPresenter.View> implements MessageListener {

  @Inject MessageRepository messages;
  @Inject MessageStateStorage storage;
  @Inject MessageIndexer indexer;
  @Inject Config config;

  private CompositeDisposable disposables;

  @Inject
  ChatPresenter() {
  }

  @Override
  protected void onViewAttached(@NonNull View view) {
    super.onViewAttached(view);

    initCurrentUser();
    initDisposables();
    initMessages();
  }

  private void initCurrentUser() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user == null) {
      getView().startSignInActivity();
      return;
    }

    getView().setAvatar(user.getPhotoUrl());
  }

  private void initDisposables() {
    if (disposables != null && !disposables.isDisposed()) {
      disposables.dispose();
    }

    disposables = new CompositeDisposable();
  }

  private void initMessages() {
    Disposable disposable = messages.getAll()
        .doOnNext(indexer)
        .subscribe(new Consumer<Message>() {

          @Override
          public void accept(Message message) throws Exception {
            getView().add(new Pair<>(message, storage.get(message.uuid())));
            getView().scrollToTop();
          }
        }, new AbstractViewError<>(getView()));

    disposables.add(disposable);
  }

  @Override
  protected void onViewDetached() {
    disposables.dispose();
    disposables = null;
  }

  @Override
  public boolean favouriteEnabled() {
    return config.favouriteEnabled();
  }

  @Override
  public void favourite(Message message, boolean favourite) {
    if (!favouriteEnabled()) {
      return;
    }

    storage.put(MessageState.create(message.uuid(), favourite), new MessageStateResolver());
  }

  @Override
  public boolean repostEnabled() {
    return config.repostEnabled();
  }

  @Override
  public void post(String string, @Nullable Message original) {
    if (original != null && !repostEnabled()) {
      return;
    }

    Message message = Message.builder()
        .uuid(UUID.randomUUID().toString())
        .text(string)
        .author(Author.from(FirebaseAuth.getInstance().getCurrentUser()))
        .created(System.currentTimeMillis())
        .original(original)
        .build();

    Disposable disposable = messages
        .put(message, new Repository.Resolver<Message, String>() {
          @Override
          public String resolve(Message message) {
            return message.uuid();
          }
        })
        .subscribe(new Action() {
          @Override
          public void run() throws Exception {
            getView().hideProgress();
          }
        }, new AbstractViewError<>(getView()));

    disposables.add(disposable);
  }

  public interface View extends ListView<Pair<Message, MessageState>> {

    void setAvatar(Uri uri);

    void startSignInActivity();

    void scrollToTop();
  }
}

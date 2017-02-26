package io.ashdavies.eternity.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.ashdavies.commons.presenter.AbstractViewPresenter;
import io.ashdavies.commons.util.BundleUtils;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.Logger;
import io.ashdavies.eternity.R;
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

  private CompositeDisposable disposables;

  @Inject Config config;
  @Inject Logger logger;

  @Inject MessageRepository messages;
  @Inject MessageStateStorage storage;
  @Inject StringResolver resolver;

  @Inject
  ChatPresenter() {
  }

  @Override
  protected void onViewAttached(@NonNull View view) {
    super.onViewAttached(view);

    initCurrentUser(view);
    initDisposables(view);
    initMessages(view);
  }

  private void initCurrentUser(View view) {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user == null) {
      view.startSignInActivity();
      return;
    }

    view.setAvatar(user.getPhotoUrl());
  }

  private void initDisposables(View view) {
    if (disposables != null && !disposables.isDisposed()) {
      disposables.dispose();
    }

    disposables = new CompositeDisposable();
  }

  private void initMessages(final View view) {
    Disposable disposable = messages.getAll()
        .doOnNext(new MessageItemLogger(logger))
        .doOnNext(new MessageIndexer())
        .subscribe(new Consumer<Message>() {

          @Override
          public void accept(Message message) throws Exception {
            view.add(new Pair<>(message, storage.get(message.uuid())));
            view.collapseActions();
            view.scrollToTop();
          }
        }, new AbstractViewError<>(view));

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

    Bundle bundle = new Bundle();
    bundle.putBoolean("favourite", favourite);
    logger.log("favourite", bundle);

    storage.put(MessageState.create(message.uuid(), favourite), new MessageStateResolver());
  }

  @Override
  public boolean repostEnabled() {
    return config.repostEnabled();
  }

  @Override
  public void post(final String string, @Nullable final Message original) {
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
        .doOnComplete(new Action() {
          @Override
          public void run() throws Exception {
            if (original != null) {
              logger.log("repost", BundleUtils.create("text", original.text()));
              return;
            }

            logger.log("post", BundleUtils.create("text", string));
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

  Intent getInviteIntent() {
    return new AppInviteInvitation.IntentBuilder(resolver.get(R.string.invitation_title))
        .setMessage(resolver.get(R.string.invitation_message))
        .setCallToActionText(resolver.get(R.string.invitation_cta))
        .build();
  }

  public interface View extends ListView<Pair<Message, MessageState>> {

    void collapseActions();

    void setAvatar(Uri uri);

    void startSignInActivity();

    void scrollToTop();
  }
}

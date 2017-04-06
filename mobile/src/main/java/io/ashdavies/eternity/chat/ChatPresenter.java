package io.ashdavies.eternity.chat;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.ashdavies.commons.presenter.AbstractViewPresenter;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.R;
import io.ashdavies.eternity.android.StringResolver;
import io.ashdavies.eternity.domain.Author;
import io.ashdavies.eternity.domain.Message;
import io.ashdavies.eternity.rx.AbstractViewError;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.inject.Inject;

class ChatPresenter extends AbstractViewPresenter<ChatPresenter.View> implements MessageListener {

  private CompositeDisposable disposables;

  @Inject Config config;

  @Inject MessageIndexer indexer;
  @Inject MessageRepository messages;
  @Inject MessageReporting reporting;
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
        .doOnNext(reporting)
        .doOnNext(indexer)
        .subscribe(message -> {
          view.add(new Pair<>(message, storage.get(message.uuid())));
          view.collapseActions();
          view.scrollToTop();
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

    reporting.favourite(message.text(), favourite);
    storage.put(MessageState.create(message.uuid(), favourite), MessageState::uuid);
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
        .put(message, Message::uuid)
        .doOnComplete(() -> {
          if (original != null) {
            reporting.repost(original.text());
            return;
          }

          reporting.post(string);
        })
        .subscribe(() -> getView().hideProgress(), new AbstractViewError<>(getView()));

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

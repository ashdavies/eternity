package io.ashdavies.eternity.signin;

import android.content.Intent;
import android.support.annotation.NonNull;
import io.ashdavies.commons.presenter.AbstractViewPresenter;
import io.ashdavies.commons.view.AbstractView;
import io.ashdavies.eternity.rx.AbstractViewError;
import io.ashdavies.eternity.rx.EqualityPredicate;
import io.ashdavies.rx.rxfirebase.RxFirebaseAuth;
import javax.inject.Inject;

public class SignInPresenter extends AbstractViewPresenter<SignInPresenter.View> {

  @Inject GoogleSignInApi client;

  @Inject
  SignInPresenter() {
  }

  @Override
  protected void onViewAttached(@NonNull View view) {
    client.onConnectionEvent()
        .filter(new EqualityPredicate<>(GoogleSignInApi.Event.CONNECTED))
        .doOnNext(event -> RxFirebaseAuth.getInstance().signOut())
        .subscribe(event -> client.signOut(), new AbstractViewError<>(getView()));
  }

  Intent getSignInIntent() {
    return client.getSignInIntent();
  }

  void onSignIn(Intent data) {
    GoogleSignInResultSingle.from(data)
        .flatMap(new AuthCredentialFunction())
        .subscribe(result -> getView().startChatActivity(), new AbstractViewError<>(getView()));
  }

  public interface View extends AbstractView {

    void startChatActivity();
  }
}

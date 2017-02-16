package io.ashdavies.eternity.signin;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.firebase.auth.AuthResult;
import io.ashdavies.commons.presenter.AbstractViewPresenter;
import io.ashdavies.commons.view.AbstractView;
import io.ashdavies.eternity.rx.AbstractViewError;
import io.ashdavies.eternity.rx.EqualityPredicate;
import io.ashdavies.rx.rxfirebase.RxFirebaseAuth;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class SignInPresenter extends AbstractViewPresenter<SignInPresenter.View> {

  private final GoogleSignInApiClient client;

  @Inject
  SignInPresenter(GoogleSignInApiClient client) {
    this.client = client;
  }

  @Override
  protected void onViewAttached(@NonNull View view) {
    client.onConnectionEvent()
        .filter(new EqualityPredicate<>(GoogleSignInApiClient.Event.CONNECTED))
        .doOnNext(new Consumer<GoogleSignInApiClient.Event>() {
          @Override
          public void accept(@NonNull GoogleSignInApiClient.Event event) throws Exception {
            RxFirebaseAuth.getInstance().signOut();
          }
        })
        .subscribe(new Consumer<GoogleSignInApiClient.Event>() {
          @Override
          public void accept(@NonNull GoogleSignInApiClient.Event event) throws Exception {
            client.signOut();
          }
        });
  }

  void signIn(Activity activity) {
    SignInActivity.startForSignInResult(activity, client.getSignInIntent());
  }

  void onSignIn(Intent data) {
    GoogleSignInResultSingle.from(data)
        .flatMap(new AuthCredentialFunction())
        .subscribe(new Consumer<AuthResult>() {
          @Override
          public void accept(@NonNull AuthResult result) throws Exception {
            getView().startChatActivity();
          }
        }, new AbstractViewError<>(getView()));
  }

  public interface View extends AbstractView {

    void startChatActivity();
  }
}

package io.ashdavies.eternity.signin;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;
import io.ashdavies.rx.rxfirebase.RxFirebaseAuth;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

class AuthCredentialFunction implements Function<GoogleSignInAccount, SingleSource<? extends AuthResult>> {

  @Override
  public SingleSource<? extends AuthResult> apply(@NonNull GoogleSignInAccount account) throws Exception {
    return RxFirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(account.getIdToken(), null));
  }
}

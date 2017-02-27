package io.ashdavies.eternity.signin;

import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import dagger.Module;
import dagger.Provides;
import io.ashdavies.commons.view.AbstractView;
import io.ashdavies.eternity.R;
import io.ashdavies.eternity.google.ConnectionFailedException;
import io.ashdavies.eternity.inject.ActivityScope;
import io.ashdavies.eternity.inject.TypeModule;

@Module
public class SignInModule extends TypeModule<SignInActivity> {

  SignInModule(SignInActivity activity) {
    super(activity);
  }

  @Provides
  GoogleSignInOptions googleSignInOptions(SignInActivity activity) {
    return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
  }

  @Provides
  @ActivityScope
  GoogleApiClient googleApiClient(SignInActivity activity, GoogleSignInOptions options) {
    return new GoogleApiClient.Builder(activity)
        .enableAutoManage(activity, new AbstractViewConnectionFailedListener(activity))
        .addApi(Auth.GOOGLE_SIGN_IN_API, options)
        .build();
  }

  private static class AbstractViewConnectionFailedListener implements GoogleApiClient.OnConnectionFailedListener {

    private final AbstractView view;

    AbstractViewConnectionFailedListener(AbstractView view) {
      this.view = view;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
      view.onError(new ConnectionFailedException(result));
    }
  }

}

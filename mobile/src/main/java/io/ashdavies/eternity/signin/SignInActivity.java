package io.ashdavies.eternity.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.OnClick;
import io.ashdavies.commons.util.IntentUtils;
import io.ashdavies.eternity.R;
import io.ashdavies.eternity.activity.AbstractApplicationActivity;
import io.ashdavies.eternity.chat.ChatActivity;
import io.ashdavies.eternity.inject.ActivityComponentService;
import io.ashdavies.eternity.inject.TypeComponent;
import javax.inject.Inject;

public class SignInActivity extends AbstractApplicationActivity<TypeComponent<SignInActivity>> implements SignInPresenter.View {

  private static final int RC_SIGN_IN = 0x91;

  public static void start(Activity activity) {
    activity.startActivity(newIntent(activity));
  }

  private static Intent newIntent(Context context) {
    return IntentUtils.newIntent(context, SignInActivity.class);
  }

  @Inject SignInPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onAttach(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  protected TypeComponent<SignInActivity> createComponent() {
    return ActivityComponentService.obtain(this)
        .getBuilder(SignInActivity.class)
        .plus(new SignInModule(this))
        .build();
  }

  @Override
  public void injectMembers(TypeComponent<SignInActivity> component) {
    component.injectMembers(this);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case RC_SIGN_IN:
        presenter.onSignIn(data);
        return;

      default:
        super.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDetach();
  }

  @Override
  public void startChatActivity() {
    ChatActivity.start(this);
  }

  @OnClick(R.id.action_sign_in)
  void onLogin() {
    startActivityForResult(presenter.getSignInIntent(), RC_SIGN_IN);
  }
}

package io.ashdavies.eternity.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import dagger.android.AndroidInjection;
import io.ashdavies.commons.adapter.AbstractAdapter;
import io.ashdavies.commons.util.BundleUtils;
import io.ashdavies.commons.util.IntentUtils;
import io.ashdavies.eternity.R;
import io.ashdavies.eternity.Reporting;
import io.ashdavies.eternity.activity.AbstractListActivity;
import io.ashdavies.eternity.android.StringResolver;
import io.ashdavies.eternity.domain.Message;
import io.ashdavies.eternity.signin.SignInActivity;
import javax.inject.Inject;

public class ChatActivity extends AbstractListActivity<Pair<Message, MessageState>> implements ChatPresenter.View {

  private static final int RC_INVITE = 0x91;

  @BindView(R.id.avatar) ImageView avatar;
  @BindView(R.id.title) TextView title;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.recycler) RecyclerView recycler;

  @BindView(R.id.actions) FloatingActionsMenu actions;

  @Inject ChatPresenter presenter;
  @Inject StringResolver resolver;
  @Inject Reporting reporting;

  public static void start(Activity activity) {
    activity.startActivity(IntentUtils.newIntent(activity, ChatActivity.class));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    presenter.onAttach(this);
  }

  @Override
  protected void inject() {
    AndroidInjection.inject(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case RC_INVITE:
        reporting.log(FirebaseAnalytics.Event.SHARE, BundleUtils.create(FirebaseAnalytics.Param.VALUE, resultCode));
        return;

      default:
        super.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public void onBackPressed() {
    SignInActivity.start(this);
    finish();
  }

  @Override
  protected AbstractAdapter<? extends AbstractAdapter.ViewHolder<Pair<Message, MessageState>>, Pair<Message, MessageState>> createAdapter(Context context) {
    return new ChatAdapter(context, presenter);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDetach();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.chat_activity, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_share:
        startActivityForResult(presenter.getInviteIntent(), RC_INVITE);
        return true;

      case R.id.action_sign_out:
        onBackPressed();
        return true;
        
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected RecyclerView.LayoutManager getLayoutManager() {
    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    manager.setOrientation(LinearLayoutManager.VERTICAL);
    return manager;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_chat;
  }

  @Override
  public void collapseActions() {
    actions.collapse();
  }

  @Override
  public void setAvatar(Uri uri) {
    Picasso.with(this)
        .load(uri)
        .transform(new CircleTransform(56))
        .into(avatar);
  }

  @Override
  public void startSignInActivity() {
    SignInActivity.start(this);
    finish();
  }

  @Override
  public void scrollToTop() {
    recycler.scrollToPosition(0);
  }

  @OnClick(R.id.action_house_traps)
  void onActionHouseTrapsClick() {
    presenter.post(resolver.get(R.string.message_house_traps), null);
  }

  @OnClick(R.id.action_horse_aisle)
  void onActionHorseAisleClick() {
    presenter.post(resolver.get(R.string.message_horse_aisle), null);
  }
}

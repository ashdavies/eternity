package io.ashdavies.eternity.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.ashdavies.commons.activity.AbstractActivity;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.Logger;
import io.ashdavies.eternity.R;
import javax.inject.Inject;

public abstract class AbstractApplicationActivity extends AbstractActivity implements Logger.Preparable {

  private Unbinder unbinder;

  @Inject Config config;
  @Inject Logger logger;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    unbinder = ButterKnife.bind(this);

    inject();
    prepare(logger);
  }

  protected abstract void inject();

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override
  public void prepare(Logger logger) {
    config.prepare(logger);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onError(Throwable throwable) {
    alert(throwable.getMessage());
    error(throwable);
  }

  private void alert(String message) {
    new AlertDialog.Builder(this)
        .setTitle(R.string.label_error)
        .setMessage(message)
        .show();
  }

  private void error(Throwable throwable, Object... args) {
    logger.error(throwable, args);
  }
}

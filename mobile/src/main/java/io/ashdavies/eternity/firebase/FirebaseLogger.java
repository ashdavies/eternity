package io.ashdavies.eternity.firebase;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import io.ashdavies.commons.util.StringUtils;
import io.ashdavies.eternity.Logger;
import javax.inject.Inject;

class FirebaseLogger implements Logger {

  private final FirebaseAnalytics analytics;

  @Inject
  FirebaseLogger(Context context) {
    this(FirebaseAnalytics.getInstance(context));
  }

  private FirebaseLogger(FirebaseAnalytics analytics) {
    this.analytics = analytics;
  }

  @Override
  public void log(String event, Bundle bundle) {
    analytics.logEvent(event, bundle);
  }

  @Override
  public void error(Throwable throwable, Object... args) {
    if (args != null) {
      FirebaseCrash.log(StringUtils.toString(args));
    }

    FirebaseCrash.report(throwable);
  }
}

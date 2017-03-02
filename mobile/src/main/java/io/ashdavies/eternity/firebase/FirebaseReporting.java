package io.ashdavies.eternity.firebase;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ashdavies.eternity.Reporting;
import javax.inject.Inject;

public class FirebaseReporting implements Reporting {

  private final FirebaseAnalytics analytics;

  @Inject
  FirebaseReporting(Context context) {
    this(FirebaseAnalytics.getInstance(context));
  }

  private FirebaseReporting(FirebaseAnalytics analytics) {
    this.analytics = analytics;
  }

  @Override
  public void log(String message, Bundle bundle) {
    analytics.logEvent(message, bundle);
  }
}

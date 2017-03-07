package io.ashdavies.eternity.firebase;

import android.app.Application;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ashdavies.eternity.Reporting;
import javax.inject.Inject;

class FirebaseReporting implements Reporting {

  private final FirebaseAnalytics analytics;

  @Inject
  FirebaseReporting(Application application) {
    this.analytics = FirebaseAnalytics.getInstance(application);
  }

  @Override
  public void log(String message, Bundle bundle) {
    analytics.logEvent(message, bundle);
  }
}

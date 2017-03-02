package io.ashdavies.eternity.firebase;

import com.google.firebase.crash.FirebaseCrash;
import io.ashdavies.commons.util.StringUtils;
import io.ashdavies.eternity.Logger;
import javax.inject.Inject;

class FirebaseLogger implements Logger {

  @Inject
  FirebaseLogger() {
  }

  @Override
  public void log(String message) {
    FirebaseCrash.log(message);
  }

  @Override
  public void error(Throwable throwable, Object... args) {
    if (args != null) {
      log(StringUtils.toString(args));
    }

    FirebaseCrash.report(throwable);
  }
}

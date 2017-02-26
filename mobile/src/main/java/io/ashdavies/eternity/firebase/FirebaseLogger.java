package io.ashdavies.eternity.firebase;

import com.google.firebase.crash.FirebaseCrash;
import io.ashdavies.commons.util.StringUtils;
import io.ashdavies.eternity.Logger;

class FirebaseLogger implements Logger {

  @Override
  public void error(Throwable throwable, Object... args) {
    if (args != null) {
      FirebaseCrash.log(StringUtils.toString(args));
    }

    FirebaseCrash.report(throwable);
  }
}

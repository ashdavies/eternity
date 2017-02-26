package io.ashdavies.eternity.firebase;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import io.ashdavies.eternity.BuildConfig;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.Logger;
import io.ashdavies.eternity.R;
import io.ashdavies.rx.rxtasks.RxTasks;
import io.reactivex.functions.Action;

class FirebaseConfig implements Config {

  private static final int CACHE_EXPIRATION_IN_SECONDS = 3600;

  private final FirebaseRemoteConfig firebase;

  private FirebaseConfig(FirebaseRemoteConfig firebase) {
    this.firebase = firebase;
  }

  static FirebaseConfig newInstance() {
    FirebaseRemoteConfig firebase = FirebaseRemoteConfig.getInstance();

    firebase.setConfigSettings(buildSettings());
    firebase.setDefaults(R.xml.remote_config_defaults);

    return new FirebaseConfig(firebase);
  }

  private static FirebaseRemoteConfigSettings buildSettings() {
    return new FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(BuildConfig.DEBUG)
        .build();
  }

  @Override
  public void prepare(Logger logger) {
    if (firebase.getInfo().getLastFetchStatus() == FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS) {
      return;
    }

    RxTasks.completable(firebase.fetch(CACHE_EXPIRATION_IN_SECONDS))
        .subscribe(new Action() {

          @Override
          public void run() throws Exception {
            firebase.activateFetched();
          }
        }, new LoggerError(logger));
  }
}

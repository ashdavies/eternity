package io.ashdavies.eternity.firebase;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import io.ashdavies.eternity.BuildConfig;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.Logger;
import io.ashdavies.eternity.R;
import io.ashdavies.rx.rxtasks.RxTasks;
import java.util.concurrent.TimeUnit;

class FirebaseConfig implements Config {

  private static final long CACHE_EXPIRATION_IN_SECONDS = TimeUnit.HOURS.toMillis(1);

  private static final String OPTION_FAVOURITE_ENABLED = "favourite_enabled";
  private static final String OPTION_REPOST_ENABLED = "repost_enabled";

  private final FirebaseRemoteConfig firebase;
  private final Config.State state;

  private FirebaseConfig(FirebaseRemoteConfig firebase, Config.State state) {
    this.firebase = firebase;
    this.state = state;
  }

  static FirebaseConfig newInstance(Config.State state) {
    FirebaseRemoteConfig firebase = FirebaseRemoteConfig.getInstance();

    firebase.setConfigSettings(buildSettings());
    firebase.setDefaults(R.xml.remote_config_defaults);

    return new FirebaseConfig(firebase, state);
  }

  private static FirebaseRemoteConfigSettings buildSettings() {
    return new FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(BuildConfig.DEBUG)
        .build();
  }

  @Override
  public void prepare(Logger logger) {
    RxTasks.completable(firebase.fetch(getCacheExpirationInSeconds()))
        .subscribe(() -> {
          firebase.activateFetched();
          state.reset();
        }, new LoggerError(logger));
  }

  private long getCacheExpirationInSeconds() {
    if (state.invalidated()) {
      return 0;
    }

    return CACHE_EXPIRATION_IN_SECONDS;
  }

  @Override
  public boolean favouriteEnabled() {
    return firebase.getBoolean(OPTION_FAVOURITE_ENABLED);
  }

  @Override
  public boolean repostEnabled() {
    return firebase.getBoolean(OPTION_REPOST_ENABLED);
  }
}

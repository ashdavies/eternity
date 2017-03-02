package io.ashdavies.eternity.firebase;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.Logger;
import io.ashdavies.eternity.Reporting;
import io.ashdavies.eternity.inject.ApplicationScope;

@Module
public abstract class FirebaseModule {

  @Provides
  @ApplicationScope
  static Config config(Config.State state) {
    return FirebaseConfig.newInstance(state);
  }

  @Binds
  abstract Config.State state(ConfigStateStorage storage);

  @Binds
  abstract Logger logger(FirebaseLogger logger);

  @Binds
  abstract Reporting reporting(FirebaseReporting reporting);
}

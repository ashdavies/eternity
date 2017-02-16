package io.ashdavies.eternity.firebase;

import dagger.Module;
import dagger.Provides;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.Logger;
import io.ashdavies.eternity.inject.ApplicationScope;

@Module
public class FirebaseModule {

  @Provides
  @ApplicationScope
  public Config config() {
    return FirebaseConfig.newInstance();
  }

  @Provides
  public Logger logger() {
    return new FirebaseLogger();
  }
}

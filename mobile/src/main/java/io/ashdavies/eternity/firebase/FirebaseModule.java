package io.ashdavies.eternity.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import io.ashdavies.eternity.Config;
import io.ashdavies.eternity.Logger;
import io.ashdavies.eternity.inject.ApplicationScope;

@Module
public class FirebaseModule {

  @Provides
  Config.State state(SharedPreferences preferences) {
    return new ConfigStateStorage(preferences);
  }

  @Provides
  @ApplicationScope
  Config config(Config.State state) {
    return FirebaseConfig.newInstance(state);
  }

  @Provides
  Logger logger(Context context) {
    return new FirebaseLogger(context);
  }
}

package io.ashdavies.eternity.inject;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.ashdavies.eternity.Eternity;
import io.ashdavies.eternity.android.StringResolver;

@Module
abstract class ApplicationModule {

  @Binds
  abstract Application application(Eternity eternity);

  @Provides
  static StringResolver resolver(Application application) {
    return new StringResolver(application.getResources());
  }

  @Provides
  static SharedPreferences preferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}

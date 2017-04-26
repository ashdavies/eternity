package io.ashdavies.eternity.inject;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import io.ashdavies.eternity.android.StringResolver;

@Module
public abstract class ApplicationModule {

  @Provides
  static StringResolver resolver(Application application) {
    return new StringResolver(application.getResources());
  }

  @Provides
  static SharedPreferences preferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}

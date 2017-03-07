package io.ashdavies.eternity.inject;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import io.ashdavies.eternity.android.StringResolver;

@Module
public class ApplicationModule {

  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  Application application() {
    return application;
  }

  @Provides
  static StringResolver stringResolver(Application application) {
    return new StringResolver(application.getResources());
  }

  @Provides
  static SharedPreferences sharedPreferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}

package io.ashdavies.eternity.firebase;

import android.content.SharedPreferences;
import io.ashdavies.eternity.Config;
import javax.inject.Inject;

class ConfigStateStorage implements Config.State {

  private static final String KEY = "io.ashdavies.eternity.config.invalidated";

  private final SharedPreferences preferences;

  @Inject
  ConfigStateStorage(SharedPreferences preferences) {
    this.preferences = preferences;
  }

  @Override
  public boolean invalidated() {
    return preferences.getBoolean(KEY, false);
  }

  @Override
  public void invalidate() {
    preferences.edit().putBoolean(KEY, true).apply();
  }

  @Override
  public void reset() {
    preferences.edit().putBoolean(KEY, false).apply();
  }
}

package io.ashdavies.eternity.android;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import javax.inject.Inject;

public class StringResolver {

  private final Resources resources;

  @Inject
  public StringResolver(Resources resources) {
    this.resources = resources;
  }

  public String get(@StringRes int resId, Object... args) {
    return resources.getString(resId, args);
  }
}

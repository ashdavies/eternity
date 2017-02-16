package io.ashdavies.eternity.chat;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import javax.inject.Inject;

class StringResolver {

  private final Resources resources;

  @Inject
  StringResolver(Resources resources) {
    this.resources = resources;
  }

  String get(@StringRes int resId, Object... args) {
    return resources.getString(resId, args);
  }
}

package io.ashdavies.eternity.inject;

import android.support.v4.app.Fragment;
import dagger.MapKey;

@MapKey
public @interface FragmentKey {

  Class<? extends Fragment> value();
}

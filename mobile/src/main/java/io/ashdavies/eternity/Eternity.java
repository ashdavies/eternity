package io.ashdavies.eternity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.google.firebase.database.FirebaseDatabase;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasDispatchingActivityInjector;
import io.ashdavies.eternity.inject.ApplicationComponent;
import io.ashdavies.eternity.inject.ApplicationModule;
import io.ashdavies.eternity.inject.DaggerApplicationComponent;
import javax.inject.Inject;

public class Eternity extends Application implements HasDispatchingActivityInjector {

  @Inject DispatchingAndroidInjector<Activity> injector;
  @Inject Config.State state;

  @Override
  public void onCreate() {
    super.onCreate();

    FirebaseDatabase.getInstance()
        .setPersistenceEnabled(true);

    createComponent()
        .injectMembers(this);
  }

  @Override
  public DispatchingAndroidInjector<Activity> activityInjector() {
    return injector;
  }

  private ApplicationComponent createComponent() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public Config.State getState() {
    return state;
  }

  public static Eternity get(Context context) {
    return (Eternity) context.getApplicationContext();
  }
}

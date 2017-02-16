package io.ashdavies.eternity;

import android.app.Application;
import android.support.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import io.ashdavies.eternity.inject.ActivityComponentService;
import io.ashdavies.eternity.inject.ApplicationComponent;
import io.ashdavies.eternity.inject.ApplicationModule;
import io.ashdavies.eternity.inject.DaggerApplicationComponent;
import javax.inject.Inject;

public class Eternity extends Application {

  @Inject ActivityComponentService service;

  @Override
  public void onCreate() {
    super.onCreate();

    FirebaseDatabase.getInstance()
        .setPersistenceEnabled(true);

    createComponent()
        .injectMembers(this);
  }

  private ApplicationComponent createComponent() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  @Override
  public Object getSystemService(@NonNull String name) {
    if (ActivityComponentService.matches(name)) {
      return service;
    }

    return super.getSystemService(name);
  }
}

package io.ashdavies.eternity.inject;

import android.app.Activity;
import android.content.Context;
import java.util.Map;
import javax.inject.Inject;

public class ActivityComponentService extends TypeComponentService<Activity> {

  private static final String COMPONENT_SERVICE = "io.ashdavies.inject.activity";

  @Inject
  ActivityComponentService(Map<Class<? extends Activity>, TypeComponent.Builder> builders) {
    super(builders);
  }

  public static boolean matches(String name) {
    return COMPONENT_SERVICE.equals(name);
  }

  @SuppressWarnings({ "ResourceType", "WrongConstant" })
  public static ActivityComponentService obtain(Context context) {
    return (ActivityComponentService) context.getApplicationContext().getSystemService(COMPONENT_SERVICE);
  }
}

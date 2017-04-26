package io.ashdavies.eternity.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import dagger.android.AndroidInjection;
import io.ashdavies.eternity.Config;
import javax.inject.Inject;

public class MessageService extends FirebaseMessagingService {

  private static final String KEY_INVALIDATE_CONFIG = "io.ashdavies.eternity.message.state.invalidate";

  @Inject Config.State state;

  @Override
  public void onCreate() {
    AndroidInjection.inject(this);
    super.onCreate();
  }

  @Override
  public void onMessageReceived(RemoteMessage message) {
    if (message.getNotification().getBody().equals(KEY_INVALIDATE_CONFIG)) {
      state.invalidate();
    }
  }
}

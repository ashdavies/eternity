package io.ashdavies.eternity.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import io.ashdavies.eternity.Eternity;

public class MessageService extends FirebaseMessagingService {

  private static final String KEY_INVALIDATE_CONFIG = "io.ashdavies.eternity.message.state.invalidate";

  @Override
  public void onMessageReceived(RemoteMessage message) {
    if (message.getNotification().getBody().equals(KEY_INVALIDATE_CONFIG)) {
      Eternity.get(this).getState().invalidate();
    }
  }
}

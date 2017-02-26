package io.ashdavies.eternity.firebase;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class InstanceIdService extends FirebaseInstanceIdService {

  private static final String FRIENDLY_ENGAGE_TOPIC = "friendly_engage";

  @Override
  public void onTokenRefresh() {
    FirebaseMessaging.getInstance()
        .subscribeToTopic(FRIENDLY_ENGAGE_TOPIC);
  }
}

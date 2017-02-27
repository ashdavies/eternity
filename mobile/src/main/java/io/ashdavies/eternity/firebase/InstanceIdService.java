package io.ashdavies.eternity.firebase;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class InstanceIdService extends FirebaseInstanceIdService {

  private static final String ENGAGE_ETERNITY = "eternity_engage";

  @Override
  public void onTokenRefresh() {
    FirebaseMessaging.getInstance()
        .subscribeToTopic(ENGAGE_ETERNITY);
  }
}

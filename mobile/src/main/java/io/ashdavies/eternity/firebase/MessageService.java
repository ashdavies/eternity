package io.ashdavies.eternity.firebase;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {

  private Handler handler;

  @Override
  public void onCreate() {
    handler = new Handler(Looper.getMainLooper());
  }

  @Override
  public void onMessageReceived(RemoteMessage message) {
    handler.post(new MessageToaster(this, message));
  }

  private static class MessageToaster implements Runnable {

    private final Context context;
    private final RemoteMessage message;

    MessageToaster(Context context, RemoteMessage message) {
      this.context = context;
      this.message = message;
    }

    @Override
    public void run() {
      Toast.makeText(context, message.getNotification().getBody(), Toast.LENGTH_SHORT).show();
    }
  }
}

package io.ashdavies.eternity.chat;

import android.support.annotation.NonNull;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ashdavies.commons.util.BundleUtils;
import io.ashdavies.eternity.Logger;
import io.reactivex.functions.Consumer;

class MessageItemLogger implements Consumer<Message> {

  private final Logger logger;

  MessageItemLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void accept(@NonNull Message message) throws Exception {
    logger.log(FirebaseAnalytics.Event.VIEW_ITEM, BundleUtils.create(FirebaseAnalytics.Param.ITEM_ID, message.uuid()));
  }
}

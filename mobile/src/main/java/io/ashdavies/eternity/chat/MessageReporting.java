package io.ashdavies.eternity.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ashdavies.commons.util.BundleUtils;
import io.ashdavies.eternity.Reporting;
import io.ashdavies.eternity.domain.Message;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

class MessageReporting implements Consumer<Message> {

  private static final String EVENT_FAVOURITE = "favourite";
  private static final String EVENT_REPOST = "repost";
  private static final String EVENT_POST = "post";

  private static final String PARAMETER_TEXT = "text";

  private final Reporting reporting;

  @Inject
  MessageReporting(Reporting reporting) {
    this.reporting = reporting;
  }

  @Override
  public void accept(@NonNull Message message) throws Exception {
    reporting.log(FirebaseAnalytics.Event.VIEW_ITEM, BundleUtils.create(FirebaseAnalytics.Param.ITEM_ID, message.uuid()));
  }

  void favourite(String text, boolean favourite) {
    Bundle bundle = BundleUtils.create(PARAMETER_TEXT, text);
    bundle.putBoolean(EVENT_FAVOURITE, favourite);
    reporting.log(EVENT_FAVOURITE, bundle);
  }

  void repost(String text) {
    reporting.log(EVENT_REPOST, BundleUtils.create(PARAMETER_TEXT, text));
  }

  void post(String text) {
    reporting.log(EVENT_POST, BundleUtils.create(PARAMETER_TEXT, text));
  }
}

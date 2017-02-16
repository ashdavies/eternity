package io.ashdavies.eternity.chat;

import io.ashdavies.eternity.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

class TimeFormatter implements StringFormatter<Long> {

  private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MMM dd", Locale.getDefault());

  private final StringResolver resolver;

  @Inject
  TimeFormatter(StringResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  public String format(Long millis) {
    if (millis < TimeUnit.HOURS.toMillis(1)) {
      return resolver.get(R.string.label_minutes, TimeUnit.MILLISECONDS.toMinutes(millis));
    }

    if (millis < TimeUnit.DAYS.toMillis(1)) {
      return resolver.get(R.string.label_hours, TimeUnit.MILLISECONDS.toHours(millis));
    }

    return FORMAT.format(new Date(millis));
  }
}

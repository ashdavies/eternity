package io.ashdavies.eternity.android;

import io.ashdavies.eternity.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class TimeFormatter implements StringFormatter<Long> {

  private static final String FORMAT = "MMM dd";

  private final StringResolver resolver;
  private final SimpleDateFormat format;

  @Inject
  public TimeFormatter(StringResolver resolver) {
    this(resolver, new SimpleDateFormat(FORMAT, Locale.getDefault()));
  }

  private TimeFormatter(StringResolver resolver, SimpleDateFormat format) {
    this.resolver = resolver;
    this.format = format;
  }

  @Override
  public String format(Long millis) {
    long diff = System.currentTimeMillis() - millis;

    if (diff < TimeUnit.HOURS.toMillis(1)) {
      return resolver.get(R.string.label_minutes, Math.max(1, TimeUnit.MILLISECONDS.toMinutes(diff)));
    }

    if (diff < TimeUnit.DAYS.toMillis(1)) {
      return resolver.get(R.string.label_hours, Math.max(1, TimeUnit.MILLISECONDS.toHours(diff)));
    }

    return format.format(new Date(millis));
  }
}

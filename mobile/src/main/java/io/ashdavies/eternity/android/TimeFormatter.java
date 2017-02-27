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
    if (millis < TimeUnit.HOURS.toMillis(1)) {
      return resolver.get(R.string.label_minutes, TimeUnit.MILLISECONDS.toMinutes(millis));
    }

    if (millis < TimeUnit.DAYS.toMillis(1)) {
      return resolver.get(R.string.label_hours, TimeUnit.MILLISECONDS.toHours(millis));
    }

    return format.format(new Date(millis));
  }
}

package io.ashdavies.eternity.android;

import android.annotation.SuppressLint;
import io.ashdavies.eternity.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class TimeFormatterTest {

  private TimeFormatter formatter;

  @Mock StringResolver resolver;

  @Captor ArgumentCaptor<Long> captor;

  @Before
  public void setUp() throws Exception {
    formatter = new TimeFormatter(resolver);
  }

  @Test
  public void shouldShowAtLeastOneMinute() throws Exception {
    given(resolver.get(eq(R.string.label_minutes), anyString())).willAnswer(new StringAnswer(StringAnswer.MINUTES));

    assertThat(formatter.format(System.currentTimeMillis())).isEqualTo("1m");
  }

  @Test
  public void shouldShowUpToOneHour() throws Exception {
    given(resolver.get(eq(R.string.label_minutes), anyString())).willAnswer(new StringAnswer(StringAnswer.MINUTES));

    assertThat(formatter.format((System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1)) + 1)).isEqualTo("59m");
  }

  @Test
  public void shouldShowOneHour() throws Exception {
    given(resolver.get(eq(R.string.label_hours), anyString())).willAnswer(new StringAnswer(StringAnswer.HOURS));

    assertThat(formatter.format(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1))).isEqualTo("1h");
  }

  @Test
  public void shouldShowUpToOneDay() throws Exception {
    given(resolver.get(eq(R.string.label_hours), anyString())).willAnswer(new StringAnswer(StringAnswer.HOURS));

    assertThat(formatter.format((System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)) + 1)).isEqualTo("23h");
  }

  @Test
  @SuppressLint("SimpleDateFormat")
  public void shouldShowFormattedDate() throws Exception {
    long yesterday = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1);
    String formatted = new SimpleDateFormat("MMM dd").format(new Date(yesterday));

    assertThat(formatter.format(yesterday)).isEqualTo(formatted);
  }

  private static class StringAnswer implements Answer<String> {

    private static final String MINUTES = "%dm";
    private static final String HOURS = "%dh";

    private final String format;

    StringAnswer(String format) {
      this.format = format;
    }

    @Override
    public String answer(InvocationOnMock invocation) throws Throwable {
      return String.format(format, invocation.getArgumentAt(1, Long.class));
    }
  }
}

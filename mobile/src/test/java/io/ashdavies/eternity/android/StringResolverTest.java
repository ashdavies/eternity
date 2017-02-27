package io.ashdavies.eternity.android;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import io.ashdavies.eternity.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class StringResolverTest {

  private StringResolver resolver;

  @Mock Resources resources;

  @Before
  public void setUp() throws Exception {
    resolver = new StringResolver(resources);
  }

  @Test
  public void shouldGetStringWithoutArguments() throws Exception {
    resolver.get(R.string.application);

    then(resources).should().getString(R.string.application, new Object[0]);
  }

  @Test
  @SuppressLint("StringFormatInvalid")
  public void shouldGetStringWithArguments() throws Exception {
    resolver.get(R.string.application, "arg0", "arg1");

    then(resources).should().getString(R.string.application, "arg0", "arg1");
  }
}

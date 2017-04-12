package io.ashdavies.eternity.domain;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;
import java.util.UUID;
import javax.annotation.Nullable;
import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

@AutoValue
@FirebaseValue
public abstract class Message {

  public abstract String uuid();
  public abstract String text();
  public abstract Author author();
  public abstract long created();

  @Nullable
  public abstract Message original();

  public Object toFirebaseValue() {
    return new AutoValue_Message.FirebaseValue(this);
  }

  private static Builder builder() {
    return new AutoValue_Message.Builder()
        .uuid(UUID.randomUUID().toString())
        .created(System.currentTimeMillis());
  }

  public static Builder from(String text) {
    return builder().text(text);
  }

  public static Builder from(Message message) {
    return from(message.text()).original(message);
  }

  public static Message create(DataSnapshot snapshot) {
    return snapshot.getValue(AutoValue_Message.FirebaseValue.class).toAutoValue();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder uuid(String uuid);
    public abstract Builder text(String text);
    public abstract Builder author(Author author);
    public abstract Builder created(long created);

    public abstract Builder original(@Nullable Message message);

    public abstract Message build();
  }
}

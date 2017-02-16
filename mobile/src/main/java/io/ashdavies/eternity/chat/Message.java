package io.ashdavies.eternity.chat;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;
import javax.annotation.Nullable;
import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

@AutoValue
@FirebaseValue
abstract class Message {

  public abstract String uuid();
  public abstract String text();
  public abstract Author author();
  public abstract long created();

  @Nullable
  public abstract Message original();

  public Object toFirebaseValue() {
    return new AutoValue_Message.FirebaseValue(this);
  }

  public static Builder builder() {
    return new AutoValue_Message.Builder();
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

package io.ashdavies.eternity.chat;

import android.net.Uri;
import com.google.auto.value.AutoValue;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

@AutoValue
@FirebaseValue
abstract class Author {

  public abstract String uuid();
  public abstract String name();
  public abstract String email();
  public abstract String avatar();

  public Object toFirebaseValue() {
    return new AutoValue_Author.FirebaseValue(this);
  }

  public static Builder builder() {
    return new AutoValue_Author.Builder();
  }

  public static Author create(DataSnapshot snapshot) {
    return snapshot.getValue(AutoValue_Author.FirebaseValue.class).toAutoValue();
  }

  public static Author from(FirebaseUser user) {
    return builder()
        .uuid(user.getUid())
        .name(user.getDisplayName())
        .email(user.getEmail())
        .avatar(user.getPhotoUrl())
        .build();
  }

  @AutoValue.Builder
  public static abstract class Builder {

    public abstract Builder uuid(String uuid);
    public abstract Builder name(String name);
    public abstract Builder email(String email);
    public abstract Builder avatar(String avatar);

    public Builder avatar(Uri uri) {
      avatar(String.valueOf(uri));
      return this;
    }

    public abstract Author build();
  }
}

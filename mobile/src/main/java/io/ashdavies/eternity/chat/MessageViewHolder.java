package io.ashdavies.eternity.chat;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import io.ashdavies.commons.adapter.AbstractAdapter;
import io.ashdavies.eternity.R;
import io.ashdavies.eternity.android.StringFormatter;
import io.ashdavies.eternity.android.StringResolver;
import io.ashdavies.eternity.android.TimeFormatter;
import io.ashdavies.eternity.domain.Message;

class MessageViewHolder extends AbstractAdapter.ViewHolder<Pair<Message, MessageState>> {

  private final StringResolver resolver;
  private final StringFormatter<Long> formatter;
  private final MessageListener listener;

  @BindView(R.id.reposted) TextView reposted;
  @BindView(R.id.avatar) ImageView avatar;
  @BindView(R.id.author) TextView author;
  @BindView(R.id.text) TextView text;
  @BindView(R.id.ago) TextView ago;

  @BindView(R.id.repost) ImageButton repost;
  @BindView(R.id.favourite) ToggleButton favourite;

  MessageViewHolder(Context context, MessageListener listener, View view) {
    super(context, view);

    ButterKnife.bind(this, view);

    this.resolver = new StringResolver(context.getResources());
    this.formatter = new TimeFormatter(resolver);

    this.listener = listener;
  }

  @Override
  protected void bind(Pair<Message, MessageState> pair) {
    Message message = pair.first;
    Message original = message.original();

    if (original != null) {
      reposted.setText(resolver.get(R.string.label_reposted, original.author().name()));
      reposted.setVisibility(View.VISIBLE);

      message = original;
    }

    Picasso.with(getContext())
        .load(message.author().avatar())
        .into(avatar);

    favourite.setChecked(pair.second.favourite());
    favourite.setVisibility(listener.favouriteEnabled() ? View.VISIBLE : View.GONE);

    author.setText(message.author().name());
    text.setText(message.text());

    repost.setOnClickListener(new RepostClickListener(listener, message));
    repost.setVisibility(listener.repostEnabled() ? View.VISIBLE : View.GONE);

    favourite.setOnClickListener(new FavouriteClickListener(listener, message));

    ago.setText(formatter.format(System.currentTimeMillis() - message.created()));
  }

  private static class RepostClickListener implements Button.OnClickListener {

    private final MessageListener listener;
    private final Message message;

    RepostClickListener(MessageListener listener, Message message) {
      this.listener = listener;
      this.message = message;
    }

    @Override
    public void onClick(View view) {
      listener.post(message.text(), message);
    }
  }

  private static class FavouriteClickListener implements ToggleButton.OnClickListener {

    private final MessageListener listener;
    private final Message message;

    FavouriteClickListener(MessageListener listener, Message message) {
      this.listener = listener;
      this.message = message;
    }

    @Override
    public void onClick(View view) {
      listener.favourite(message, ((ToggleButton) view).isChecked());
    }
  }
}

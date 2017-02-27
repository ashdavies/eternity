package io.ashdavies.eternity.chat;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import io.ashdavies.commons.adapter.AbstractAdapter;
import io.ashdavies.eternity.R;
import io.ashdavies.eternity.domain.Message;

class ChatAdapter extends AbstractAdapter<MessageViewHolder, Pair<Message, MessageState>> {

  private final MessageListener listener;

  ChatAdapter(Context context, MessageListener listener) {
    super(context);

    this.listener = listener;
  }

  @Override
  public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MessageViewHolder(parent.getContext(), listener, inflate(parent));
  }

  private View inflate(ViewGroup parent) {
    return getInflater().inflate(R.layout.item_message, parent, false);
  }
}

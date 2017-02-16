package io.ashdavies.eternity.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import io.ashdavies.commons.adapter.AbstractAdapter;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.eternity.R;
import java.util.Collection;

public abstract class AbstractListActivity<Component, T> extends AbstractApplicationActivity<Component> implements ListView<T> {

  private AbstractAdapter<? extends AbstractAdapter.ViewHolder<T>, T> adapter;

  @BindView(R.id.recycler) RecyclerView recycler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    adapter = createAdapter(getContext());
    recycler.setAdapter(adapter);

    recycler.setHasFixedSize(true);
    recycler.addItemDecoration(getItemDividerDecoration());
    recycler.setLayoutManager(getLayoutManager());
  }

  protected abstract AbstractAdapter<? extends AbstractAdapter.ViewHolder<T>, T> createAdapter(Context context);

  @Override
  public void add(T item) {
    adapter.addItem(item);
  }

  @Override
  public void add(Collection<T> items) {
    adapter.addItems(items);
  }

  protected RecyclerView.ItemDecoration getItemDividerDecoration() {
    return new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
  }

  protected RecyclerView.LayoutManager getLayoutManager() {
    return new LinearLayoutManager(getContext());
  }
}

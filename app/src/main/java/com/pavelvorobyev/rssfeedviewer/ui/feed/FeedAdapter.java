package com.pavelvorobyev.rssfeedviewer.ui.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pavelvorobyev.rssfeedviewer.R;
import com.pavelvorobyev.rssfeedviewer.businesslogic.pojo.Item;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<Item> items = new ArrayList<>();
    private Callback callback;

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FeedViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_feed_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int i) {
        Item item = items.get(i);

        holder.titleView.setText(item.getTitle());
        holder.descriptionView.setText(item.getDescription());
        holder.itemView.setOnClickListener(v -> {
            if (callback != null)
                callback.itemClick(item);
        });

        if (item.getThumbnail() != null)
            Picasso.get()
                    .load(item.getThumbnail().getUrl())
                    .into(holder.thumbnailView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void updateItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleView)
        TextView titleView;
        @BindView(R.id.thumbnailView)
        ImageView thumbnailView;
        @BindView(R.id.descriptionView)
        TextView descriptionView;

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface Callback {
        void itemClick(Item item);
    }

}

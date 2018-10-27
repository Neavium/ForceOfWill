package ch.FOW_Collection.presentation.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.FOW_Collection.R;
import ch.FOW_Collection.domain.models.CardEdition;
import ch.FOW_Collection.presentation.utils.BackgroundImageProvider;
import ch.FOW_Collection.presentation.utils.EntityDiffItemCallback;


/**
 * This class is really similar to {@link CardPopularRecyclerViewAdapter} see the documentation there.
 */
public class CardEditionsRecyclerViewAdapter
        extends ListAdapter<CardEdition, CardEditionsRecyclerViewAdapter.ViewHolder> {

    private final CardEditionsFragment.OnItemSelectedListener listener;

    public CardEditionsRecyclerViewAdapter(CardEditionsFragment.OnItemSelectedListener listener) {
        super(new EntityDiffItemCallback<>());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_explore_card_editions_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(getItem(position), position, listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content)
        TextView content;

        @BindView(R.id.imageCard)
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(CardEdition item, int position, CardEditionsFragment.OnItemSelectedListener listener) {
            content.setText(item.getDe());
            Context resources = itemView.getContext();
            imageView.setImageDrawable(BackgroundImageProvider.getBackgroundImage(resources, position + 10));
            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onCardEditionSelected(item));
            }
        }
    }
}
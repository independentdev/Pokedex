package uk.co.sooce.pokedex.ui.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import uk.co.sooce.pokedex.R;
import uk.co.sooce.pokedex.model.Pokedex;
import uk.co.sooce.pokedex.model.PokedexNode;

import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private final List<PokedexNode> mValues;
    PokemonClickListener mPokemonClickListener;

    public PokemonAdapter(List<PokedexNode> items, PokemonClickListener clickListener) {
        mValues = items;
        mPokemonClickListener = clickListener;
    }

    @NotNull @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(view, mPokemonClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format(Locale.getDefault(), "#%d", mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getName());
        Picasso.with(holder.itemView.getContext())
                .load(mValues.get(position).getSpriteUrl())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setLiveData(LiveData<Pokedex> data, LifecycleOwner lifecycleOwner) {
        data.observe(lifecycleOwner, pokedex-> {
            if (pokedex != null) {
                mValues.clear();
                mValues.addAll(pokedex.getResults());
                notifyDataSetChanged();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public PokedexNode mItem;
        PokemonClickListener vPokemonClickListener;

        public ViewHolder(View view, PokemonClickListener clickListener) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            vPokemonClickListener = clickListener;
            view.setOnClickListener(this);
        }

        @NotNull @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            vPokemonClickListener.onPokemonClick(mItem);
        }
    }

    public interface PokemonClickListener {
        void onPokemonClick(PokedexNode pokedexNode);
    }
}
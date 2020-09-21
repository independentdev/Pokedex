package uk.co.sooce.pokedex.ui.detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import uk.co.sooce.pokedex.R;
import uk.co.sooce.pokedex.model.Trait;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PokemonTraitsAdapter extends RecyclerView.Adapter<PokemonTraitsAdapter.ViewHolder> {

    private List<Trait> mValues;

    public PokemonTraitsAdapter(List<Trait> items) {
        mValues = items;
    }

    @NotNull @Override
    public PokemonTraitsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_item, parent, false);
        return new PokemonTraitsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PokemonTraitsAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvLabel.setText(mValues.get(position).label);
        holder.tvValue.setText(mValues.get(position).value);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setItems(List<Trait> items) {
        this.mValues = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvLabel;
        public final TextView tvValue;
        public Trait mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvLabel = (TextView) view.findViewById(R.id.item_number);
            tvValue = (TextView) view.findViewById(R.id.content);
        }

        @NotNull @Override
        public String toString() {
            return super.toString() + " '" + tvValue.getText() + "'";
        }

    }

}

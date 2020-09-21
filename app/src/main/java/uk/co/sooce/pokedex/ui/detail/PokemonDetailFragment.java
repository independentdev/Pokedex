package uk.co.sooce.pokedex.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import uk.co.sooce.pokedex.R;
import uk.co.sooce.pokedex.databinding.PokemonDetailFragmentBinding;
import uk.co.sooce.pokedex.model.Pokemon;
import uk.co.sooce.pokedex.ui.detail.adapter.PokemonTraitsAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PokemonDetailFragment extends Fragment {

    public static final String ARG_PARAM1 = "pokemonName";
    private PokemonDetailFragmentBinding binding;
    private PokemonTraitsAdapter mAdapter;

    public PokemonDetailFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.pokemon_detail_fragment, container, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mAdapter = new PokemonTraitsAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(mAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PokemonDetailViewModel mViewModel = new ViewModelProvider(this).get(PokemonDetailViewModel.class);
        if (getArguments() != null) {
            String pokemonName = getArguments().getString(ARG_PARAM1);
            if (pokemonName != null && pokemonName.length() > 0) {
                mViewModel.fetchPokemon(pokemonName);
                mViewModel.currentPokemon.observe(getViewLifecycleOwner(), this::onPokemonFetch);
            }
        }
    }

    private void onPokemonFetch(Pokemon pokemon) {
        binding.setPokemon(pokemon);

        Picasso.with(requireContext())
                .load(pokemon.getSprites().getFront_default())
                .into(binding.ivImage);

        mAdapter.setItems(pokemon.getTraitList());
    }

}
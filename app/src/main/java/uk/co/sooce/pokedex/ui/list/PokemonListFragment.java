package uk.co.sooce.pokedex.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.sooce.pokedex.R;
import uk.co.sooce.pokedex.model.PokedexNode;
import uk.co.sooce.pokedex.ui.detail.PokemonDetailFragment;
import uk.co.sooce.pokedex.ui.list.adapter.PokemonAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment representing a list of Items.
 */
public class PokemonListFragment extends Fragment implements PokemonAdapter.PokemonClickListener {

    private PokemonListViewModel mViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokemonListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(PokemonListViewModel.class);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

//            mAdapter = new PokemonRecyclerViewAdapter(new ArrayList<>());
            PokemonAdapter mAdapter = new PokemonAdapter(new ArrayList<>(), this);
            recyclerView.setAdapter(mAdapter);
            mAdapter.setLiveData(mViewModel.pokedex(), this);
        }
        return view;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.fetchRemotePokemons();
    }

    @Override public void onPokemonClick(PokedexNode pokedexNode) {
        Bundle bundle = new Bundle();
        bundle.putString(PokemonDetailFragment.ARG_PARAM1, pokedexNode.getName());
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment, bundle);
    }
}
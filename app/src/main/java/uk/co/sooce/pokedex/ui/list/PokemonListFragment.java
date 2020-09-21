package uk.co.sooce.pokedex.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.sooce.pokedex.R;
import uk.co.sooce.pokedex.databinding.FragmentPokemonListBinding;
import uk.co.sooce.pokedex.model.PokedexNode;
import uk.co.sooce.pokedex.ui.detail.PokemonDetailFragment;
import uk.co.sooce.pokedex.ui.list.adapter.PokemonAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * A fragment representing a list of Items.
 */
public class PokemonListFragment extends Fragment implements PokemonAdapter.PokemonClickListener {

    private PokemonListViewModel mViewModel;
    private FragmentPokemonListBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokemonListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_list, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(PokemonListViewModel.class);

        // init recyclerview
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        // Set the adapter
        PokemonAdapter mAdapter = new PokemonAdapter(new ArrayList<>(), this);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setLiveData(mViewModel.getFilteredNodes(), this);
        initSearch();
        return binding.getRoot();
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

    @Override public void onResume() {
        super.onResume();
        binding.searchView.setQuery("", false);
    }

    private void initSearch() {
        binding.searchView.setSubmitButtonEnabled(true);
        binding.searchView.clearFocus();
        binding.searchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override public boolean onQueryTextSubmit(String query) {
                mViewModel.refreshFilter(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                mViewModel.refreshFilter(newText);
                return true;
            }
        });
    }
}
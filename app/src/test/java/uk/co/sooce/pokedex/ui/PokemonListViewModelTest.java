package uk.co.sooce.pokedex.ui;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.co.sooce.pokedex.data.repository.PokemonRepository;
import uk.co.sooce.pokedex.model.Pokedex;
import uk.co.sooce.pokedex.ui.list.PokemonListViewModel;
import uk.co.sooce.pokedex.utils.RxSingleSchedulers;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@RunWith(JUnit4.class)
public class PokemonListViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    PokemonRepository pokemonRepository;

    @Mock
    PokemonListViewModel pokemonListViewModel;

    @Mock
    Observer<Pokedex> pokedexObserver;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pokemonListViewModel = new PokemonListViewModel(pokemonRepository, RxSingleSchedulers.TEST_SCHEDULER);
        pokemonListViewModel.getPokedex().observeForever(pokedexObserver);
    }

    @Test
    public void testFetchPokemonList() {
        Pokedex dummyPokedex = new Pokedex(1);
        Mockito.when(pokemonRepository.fetchRemotePokemons(100, 0)).thenReturn(Single.just(dummyPokedex));
        pokemonListViewModel.fetchRemotePokemons();
        Mockito.verify(pokedexObserver).onChanged(dummyPokedex);
    }

}


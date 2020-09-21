package uk.co.sooce.pokedex.ui.detail;

import android.util.Log;

import uk.co.sooce.pokedex.data.repository.PokemonRepository;
import uk.co.sooce.pokedex.model.Pokedex;
import uk.co.sooce.pokedex.model.PokedexNode;
import uk.co.sooce.pokedex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PokemonDetailViewModel extends ViewModel {
    private static final String LOG_TAG = "POKEMON_LOG";
    private PokemonRepository mPokemonRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    MutableLiveData<Pokemon> currentPokemon = new MutableLiveData<>();

    @ViewModelInject
    public PokemonDetailViewModel(PokemonRepository pokemonRepository) {
        mPokemonRepository = pokemonRepository;
    }

    /**
     * Fetch Pokemon data async with RxJava
     * */
    public void fetchPokemon(String name) {
        mDisposable.add(mPokemonRepository.fetchRemotePokemon(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Pokemon>() {

                    @Override public void onSuccess(Pokemon pokemon) {
                        Log.d(LOG_TAG, String.format("Pokemon detail fetched %s", pokemon.getName()));
                        currentPokemon.postValue(pokemon);
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Error occurred on fetch", e);
                    }
                }));
    }

}
package uk.co.sooce.pokedex.ui.list;

import android.util.Log;

import uk.co.sooce.pokedex.data.repository.PokemonRepository;
import uk.co.sooce.pokedex.model.Pokedex;
import uk.co.sooce.pokedex.utils.RxSingleSchedulers;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PokemonListViewModel extends ViewModel {
    private static final String LOG_TAG = "POKEMON_LOG";
    private static final int PAGE_SIZE = 100;
    private PokemonRepository mPokemonRepository;
    private CompositeDisposable mDisposable;
    private RxSingleSchedulers mRxSingleSchedulers;
    private final MutableLiveData<Pokedex> mPokedex = new MutableLiveData<>();

    public LiveData<Pokedex> pokedex() {
        return mPokedex;
    }

    /**
     * Constructor for dependency Injection with Dagger-Hilt
     * */
    @ViewModelInject
    public PokemonListViewModel(PokemonRepository pokemonRepository, RxSingleSchedulers rxSingleSchedulers) {
        mPokemonRepository = pokemonRepository;
        mDisposable = new CompositeDisposable();
        mRxSingleSchedulers = rxSingleSchedulers;
    }

    /**
     * Fetch Pokemon List data async with RxJava
     * */
    public void fetchRemotePokemons() {
        int offset = 0;
        mDisposable.add(mPokemonRepository.fetchRemotePokemons(PAGE_SIZE, offset)
                .subscribeOn(Schedulers.io())
                .compose(mRxSingleSchedulers.applySchedulers())
                .subscribeWith(new DisposableSingleObserver<Pokedex>() {

                    @Override public void onSuccess(Pokedex pokedex) {
                        mPokedex.postValue(pokedex);
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Error occurred on fetch", e);
                    }
                }));
    }

}
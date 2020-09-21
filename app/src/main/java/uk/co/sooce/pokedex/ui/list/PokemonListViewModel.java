package uk.co.sooce.pokedex.ui.list;

import android.util.Log;

import uk.co.sooce.pokedex.data.repository.PokemonRepository;
import uk.co.sooce.pokedex.model.Pokedex;
import uk.co.sooce.pokedex.model.PokedexNode;
import uk.co.sooce.pokedex.utils.RxSingleSchedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private final MutableLiveData<List<PokedexNode>> mFilteredNodes = new MutableLiveData<>();

    public LiveData<Pokedex> getPokedex() {
        return mPokedex;
    }
    public LiveData<List<PokedexNode>> getFilteredNodes() {
        return mFilteredNodes;
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
                        mFilteredNodes.postValue(pokedex.getResults());
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Error occurred on fetch", e);
                    }
                }));
    }

    public void refreshFilter(String query) {
        if (mPokedex.getValue() == null)
            return;

        if (query == null || query.length() == 0) {
            mFilteredNodes.postValue(mPokedex.getValue().getResults());
        } else
        {
            List<PokedexNode> nodeList = new ArrayList<>();
            for (PokedexNode node : Objects.requireNonNull(mPokedex.getValue()).getResults()) {
                if (node.getName().contains(query))
                    nodeList.add(node);
            }
            mFilteredNodes.postValue(nodeList);
        }
    }

}
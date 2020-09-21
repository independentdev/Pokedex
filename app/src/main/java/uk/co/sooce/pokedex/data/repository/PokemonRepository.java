package uk.co.sooce.pokedex.data.repository;

import android.content.Context;

import uk.co.sooce.pokedex.data.local.PokedexDatabase;
import uk.co.sooce.pokedex.data.remote.PokeAPI;
import uk.co.sooce.pokedex.model.Pokedex;
import uk.co.sooce.pokedex.model.Pokemon;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class PokemonRepository {
    private static PokemonRepository instance;
    private PokedexDatabase mDb;
    private PokeAPI mPokeAPI;

    // singleton pattern

    private PokemonRepository(Context context, PokeAPI pokeAPI) {
        mDb = PokedexDatabase.getInstance(context);
        mPokeAPI = pokeAPI;
    }


    public static PokemonRepository getInstance(Context context, PokeAPI pokeAPI) {
        if (instance == null) {
            instance = new PokemonRepository(context, pokeAPI);
        }
        return instance;
    }

    /**
    * Fetch All Pokemons from DB
    */
    public Flowable<List<Pokemon>> fetchLocalPokemons() {
        return mDb.repositoryDao().fetchAll();
    }

    /**
     * Get requested range of Pokemons from API
     */
    public Single<Pokedex> fetchRemotePokemons(int pageLimit, int offset) {
        return mPokeAPI.getPokedex(pageLimit, offset);
    }


    /**
     * Get requested Pokemon from API by name
     */
    public Single<Pokemon> fetchRemotePokemon(String name) {
        return mPokeAPI.getPokemon(name);
    }

    /**
     * Delete All Pokemons from DB
     */
    public Completable deleteAllPokemons() {
        return mDb.repositoryDao().deleteAll();
    }

    /**
     * Insert Single Pokemon to DB
     */
    public Completable insertPokemon(Pokemon pokemon) {
        return mDb.repositoryDao().insert(pokemon);
    }

    /**
     * Insert List of Pokemons to DB
     */
    public Completable insertPokemonList(List<Pokemon> pokemonList) {
        return mDb.repositoryDao().insertAll(pokemonList);
    }
}

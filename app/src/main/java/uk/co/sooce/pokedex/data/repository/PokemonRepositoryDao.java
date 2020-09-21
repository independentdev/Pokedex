package uk.co.sooce.pokedex.data.repository;

import uk.co.sooce.pokedex.model.Pokemon;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface PokemonRepositoryDao {

    // Insert Single Pokemon to DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Pokemon pokemon);

    // Insert List of Pokemon to DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Pokemon> pokemonList);

    // Fetch all Pokemons from DB
    @Query("SELECT * FROM Pokedex")
    Flowable<List<Pokemon>> fetchAll();

    // Delete all Pokemons from DB
    @Query("DELETE FROM Pokedex")
    Completable deleteAll();


}

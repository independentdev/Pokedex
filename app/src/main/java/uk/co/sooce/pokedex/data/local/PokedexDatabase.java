package uk.co.sooce.pokedex.data.local;

import android.content.Context;

import uk.co.sooce.pokedex.data.repository.PokemonRepositoryDao;
import uk.co.sooce.pokedex.model.Pokemon;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { Pokemon.class }, version = 1, exportSchema = false)
public abstract class PokedexDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "pokedex.db";
    private static volatile PokedexDatabase instance;
    private static final Object LOCK = new Object();

    public abstract PokemonRepositoryDao repositoryDao();

    public static PokedexDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), PokedexDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}

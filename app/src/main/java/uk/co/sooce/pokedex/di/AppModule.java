package uk.co.sooce.pokedex.di;

import android.content.Context;

import uk.co.sooce.pokedex.data.remote.PokeAPI;
import uk.co.sooce.pokedex.data.repository.PokemonRepository;
import uk.co.sooce.pokedex.utils.RxSingleSchedulers;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {

    @Provides
    public static PokemonRepository providePokemonRepository(@ApplicationContext Context context, PokeAPI pokeAPI) {
        return PokemonRepository.getInstance(context, pokeAPI);
    }

    @Provides
    public static RxSingleSchedulers provideSchedulers() {
        return RxSingleSchedulers.DEFAULT;
    }
}

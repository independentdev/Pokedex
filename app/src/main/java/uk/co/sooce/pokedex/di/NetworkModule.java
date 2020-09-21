package uk.co.sooce.pokedex.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uk.co.sooce.pokedex.data.remote.PokeAPI;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {

    @Provides
    public static PokeAPI providePokeApi(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(PokeAPI.class);
    }

    @Provides
    public static Gson provideGson() {
        return new GsonBuilder().create();
    }
}

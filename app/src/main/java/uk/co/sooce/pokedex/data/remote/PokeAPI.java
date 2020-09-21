package uk.co.sooce.pokedex.data.remote;

import uk.co.sooce.pokedex.model.Pokedex;
import uk.co.sooce.pokedex.model.Pokemon;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeAPI {

    @GET("pokemon")
    Single<Pokedex> getPokedex(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{name}/")
    Single<Pokemon> getPokemon(@Path("name") String name);
}

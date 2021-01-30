package gap.com.pokemon.network;

import javax.inject.Inject;

import gap.com.pokemon.model.PokemonResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokeApiService {
    @GET("pokemon")
    Observable<PokemonResponse> getPokemons();
}

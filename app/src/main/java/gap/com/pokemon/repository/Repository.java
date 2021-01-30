package gap.com.pokemon.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import gap.com.pokemon.db.PokeDao;
import gap.com.pokemon.model.Pokemon;
import gap.com.pokemon.model.PokemonResponse;
import gap.com.pokemon.network.PokeApiService;
import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private PokeDao pokeDao;
    private PokeApiService apiService;

    @Inject
    public Repository(PokeDao pokeDao, PokeApiService apiService) {
        this.pokeDao = pokeDao;
        this.apiService = apiService;
    }

    public Observable<PokemonResponse> getPokemons() {
        return apiService.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon) {
        pokeDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        pokeDao.deletePokemon(pokemonName);
    }

    public void deleteAll() {
        pokeDao.deleteAll();
    }

    public LiveData<List<Pokemon>> getFavoritePokemon() {
        return pokeDao.getFavoritePokemons();
    }
}

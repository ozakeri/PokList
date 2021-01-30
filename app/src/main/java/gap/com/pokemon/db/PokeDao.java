package gap.com.pokemon.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import gap.com.pokemon.model.Pokemon;

@Dao
public interface PokeDao {
    @Insert
    void insertPokemon(Pokemon pokemon);

    @Query("DELETE FROM fav_table WHERE name = :pokemonName")
    void deletePokemon(String pokemonName);

    @Query("DELETE FROM fav_table")
    void deleteAll();

    @Query("SELECT * FROM fav_table")
    LiveData<List<Pokemon>> getFavoritePokemons();
}

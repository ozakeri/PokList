package gap.com.pokemon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import gap.com.pokemon.model.Pokemon;

@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {

    public abstract PokeDao pokeDao();

}

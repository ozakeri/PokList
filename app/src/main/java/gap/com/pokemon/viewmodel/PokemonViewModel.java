package gap.com.pokemon.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import gap.com.pokemon.constant.Constant;
import gap.com.pokemon.model.Pokemon;
import gap.com.pokemon.model.PokemonResponse;
import gap.com.pokemon.repository.Repository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {

    private static String TAG = PokemonViewModel.class.getSimpleName();
    private Repository repository;
    private ArrayList<Pokemon> arrayList;
    private MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<>();

    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    public void getPokemon() {

        repository.getPokemons().subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        arrayList = pokemonResponse.getResults();
                        System.out.println("arrayList====" + arrayList.size());
                        System.out.println("arrayList====" + pokemonResponse.toString());
                        for (Pokemon pok : arrayList) {
                            String url = pok.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pok.setUrl(Constant.IMG_URL + pokemonIndex[pokemonIndex.length - 1] + ".png");
                        }
                        return arrayList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemonList.setValue(result),
                        error -> Log.e(TAG, "getPokemons: " + error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Pokemon>> getFavoritePokemon() {
        return repository.getFavoritePokemon();
    }
}

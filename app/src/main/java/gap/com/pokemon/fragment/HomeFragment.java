package gap.com.pokemon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import gap.com.pokemon.R;
import gap.com.pokemon.adapter.PokeAdapter;
import gap.com.pokemon.databinding.FragmentHomeBinding;
import gap.com.pokemon.model.Pokemon;
import gap.com.pokemon.viewmodel.PokemonViewModel;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private PokeAdapter adapter;
    private PokemonViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        setUpItemTouchHelper();
        viewModel.getPokemon();
        viewModel.getPokemonList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                fragmentHomeBinding.pokemonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new PokeAdapter(pokemons);
                System.out.println("pokemons====" + pokemons.size());
                fragmentHomeBinding.pokemonRecyclerView.setAdapter(adapter);
            }
        });
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon pokemon = adapter.getPokemonAt(swipedPokemonPosition);
                viewModel.insertPokemon(pokemon);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Pokemon added to favorites.",Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(fragmentHomeBinding.pokemonRecyclerView);
    }
}
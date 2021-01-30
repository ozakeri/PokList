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
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import gap.com.pokemon.adapter.PokeAdapter;
import gap.com.pokemon.databinding.FragmentFavBinding;
import gap.com.pokemon.model.Pokemon;
import gap.com.pokemon.viewmodel.PokemonViewModel;

@AndroidEntryPoint
public class FavFragment extends Fragment {

    private FragmentFavBinding binding;
    private PokeAdapter adapter;
    private PokemonViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        setUpItemTouchHelper();

        viewModel.getFavoritePokemon().observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                binding.favRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                ArrayList<Pokemon> list = new ArrayList<>();
                list.addAll(pokemons);
                adapter = new PokeAdapter(list);
                System.out.println("pokemons====" + pokemons.size());
                binding.favRecyclerView.setAdapter(adapter);
            }
        });
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon pokemon = adapter.getPokemonAt(swipedPokemonPosition);
                viewModel.deletePokemon(pokemon.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Pokemon removed from favorites.", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.favRecyclerView);
    }
}
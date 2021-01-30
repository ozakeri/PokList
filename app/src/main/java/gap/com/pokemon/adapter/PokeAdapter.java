package gap.com.pokemon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gap.com.pokemon.databinding.ListItemBinding;
import gap.com.pokemon.model.Pokemon;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.PokemonViewHolder> {

    private ListItemBinding listItemBinding;
    private ArrayList<Pokemon> mList;

    public PokeAdapter(ArrayList<Pokemon> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        listItemBinding = ListItemBinding.inflate(inflater, parent, false);
        return new PokemonViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        System.out.println("mList===" + mList.size());
        holder.listItemBinding.pokemonName.setText(mList.get(position).getName());
        Glide.with(holder.listItemBinding.pokemonImage.getContext()).load(mList.get(position).getUrl())
                .into(holder.listItemBinding.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Pokemon getPokemonAt(int swipedPokemonPosition) {
        return mList.get(swipedPokemonPosition);
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding listItemBinding;

        public PokemonViewHolder(ListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }
    }

}

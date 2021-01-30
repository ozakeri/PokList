package gap.com.pokemon;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;
import gap.com.pokemon.databinding.ActivityMainBinding;
import gap.com.pokemon.fragment.FavFragment;
import gap.com.pokemon.fragment.HomeFragment;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private boolean isFavoriteListVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment())
                .commit();

        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFavoriteListVisible) {
                    isFavoriteListVisible = true;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FavFragment())
                            .commit();
                } else {
                    isFavoriteListVisible = false;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment())
                            .commit();
                }


            }
        });
    }
}
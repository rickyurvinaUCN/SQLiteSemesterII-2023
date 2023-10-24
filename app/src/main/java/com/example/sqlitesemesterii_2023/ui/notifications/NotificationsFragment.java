package com.example.sqlitesemesterii_2023.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sqlitesemesterii_2023.PokemonAPIService;
import com.example.sqlitesemesterii_2023.PokemonListItem;
import com.example.sqlitesemesterii_2023.PokemonListResponse;
import com.example.sqlitesemesterii_2023.databinding.FragmentNotificationsBinding;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsFragment extends Fragment {

    private ListView list;
    private ArrayList<String> pokemons= new ArrayList<>();

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, pokemons);
        list= binding.list;
        list.setAdapter(adapter);

        getPokemons(adapter);


        return root;
    }

    public void getPokemons(ArrayAdapter adapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonAPIService pokemonApiService = retrofit.create(PokemonAPIService.class);
        Call<PokemonListResponse> call = pokemonApiService.getPokemons();
        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                if (response.isSuccessful()) {
                    PokemonListResponse pokemonListResponse = response.body();
                    List<PokemonListItem> pokemonList = pokemonListResponse.getResults();

                    for (PokemonListItem pokemonListItem : pokemonList) {
                        String name= pokemonListItem.getName().toString();
                        pokemons.add(name);
                        adapter.notifyDataSetChanged();
                    };
                } else {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
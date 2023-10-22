package com.example.sqlitesemesterii_2023;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonAPIService {

    @GET("pokemon/?limit=50")
    Call<PokemonListResponse> getPokemons();
}

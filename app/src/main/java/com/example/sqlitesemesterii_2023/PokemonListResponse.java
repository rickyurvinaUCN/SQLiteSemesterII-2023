package com.example.sqlitesemesterii_2023;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonListResponse {
    @SerializedName("count")
    private int count; // Total de Pokémon en la API

    @SerializedName("next")
    private String next; // URL de la siguiente página de resultados

    @SerializedName("previous")
    private String previous; // URL de la página anterior (si aplicable)

    @SerializedName("results")
    private List<PokemonListItem> results; // Lista de Pokémon

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<PokemonListItem> getResults() {
        return results;
    }
}


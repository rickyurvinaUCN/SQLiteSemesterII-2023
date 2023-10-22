package com.example.sqlitesemesterii_2023;

import com.google.gson.annotations.SerializedName;

public class PokemonListItem {
    @SerializedName("name")
    private String name; // Nombre del Pokémon

    @SerializedName("url")
    private String url; // URL para obtener detalles del Pokémon

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

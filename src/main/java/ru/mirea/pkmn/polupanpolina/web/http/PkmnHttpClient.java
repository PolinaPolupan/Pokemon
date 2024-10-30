package ru.mirea.pkmn.polupanpolina.web.http;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Response;


import retrofit2.Call;
import retrofit2.Callback;


public class PkmnHttpClient {

    private PokemonTcgAPI tcgAPI;

    @Autowired
    PkmnHttpClient(PokemonTcgAPI tcgAPI) {
        this.tcgAPI = tcgAPI;
    }

    public void getPokemonCard(String name, String number, PokemonCardCallback callback) {
        String requestQuery = "name:\"" + name + "\" number:" + number;

        Call<JsonNode> call = tcgAPI.getPokemon(requestQuery);
        call.enqueue(new Callback<JsonNode>() {
            @Override
            public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Request failed with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<JsonNode> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface PokemonCardCallback {
        void onSuccess(JsonNode cardData);
        void onError(Throwable error);
    }
}

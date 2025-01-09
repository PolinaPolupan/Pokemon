package example.pokemon.service;

import example.pokemon.dto.CardImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PokemonTcgService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://api.pokemontcg.io/v2/cards?q=name:";

    public CardImageResponse fetchCardImageByName(String name) {
        String url = API_URL + name;
        try {
            var response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("data")) {
                throw new RuntimeException("No data found in API response");
            }

            var data = (List<Map<String, Object>>) response.get("data");
            if (data == null || data.isEmpty()) {
                throw new RuntimeException("No card found with the given name");
            }

            var card = data.get(0);
            if (!card.containsKey("images") || !card.containsKey("name")) {
                throw new RuntimeException("Card data is incomplete");
            }

            String cardName = (String) card.get("name");

            var images = (Map<String, String>) card.get("images");
            String imageUrl = images.get("large");

            return new CardImageResponse(cardName, imageUrl);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching card data: " + e.getMessage(), e);
        }
    }
}

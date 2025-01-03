CREATE TABLE IF NOT EXISTS pokemon.cards (
    id UUID NOT NULL,
    stage VARCHAR(255),
    name VARCHAR(255),
    hp INTEGER NOT NULL,
    evolves_from_id UUID,
    attack_skills JSON,
    weakness_type VARCHAR(255),
    resistance_type VARCHAR(255),
    retreat_cost VARCHAR(255),
    game_set VARCHAR(255),
    pokemon_type VARCHAR(255),
    regulation_mark CHAR NOT NULL,
    pokemon_owner_id UUID,
    card_number VARCHAR(255),
    CONSTRAINT pk_cards PRIMARY KEY (id)
);

ALTER TABLE pokemon.cards ADD CONSTRAINT FK_CARDS_ON_EVOLVESFROM FOREIGN KEY (evolves_from_id) REFERENCES cards (id);

ALTER TABLE pokemon.cards ADD CONSTRAINT FK_CARDS_ON_POKEMONOWNER FOREIGN KEY (pokemon_owner_id) REFERENCES pokemon.students (id);
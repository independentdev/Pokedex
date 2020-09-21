package uk.co.sooce.pokedex.model;

import com.google.gson.annotations.SerializedName;

public class PokemonTypeSlot {
    @SerializedName("slot")
    private int slot;
    @SerializedName("type")
    private PokemonType type;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }
}

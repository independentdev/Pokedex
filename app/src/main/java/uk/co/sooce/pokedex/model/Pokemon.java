package uk.co.sooce.pokedex.model;

import com.google.gson.annotations.SerializedName;

import android.net.TrafficStats;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokedex")
public class Pokemon {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("base_experience")
    private int baseExperience;

    @SerializedName("height")
    private int height;

    @SerializedName("is_default")
    private boolean isDefault;

    @SerializedName("name")
    private String name;

    @SerializedName("order")
    private int order;

    @Ignore
    @SerializedName("stats")
    private List<StatNode> stats = null;

    @Ignore
    @SerializedName("types")
    private List<PokemonTypeSlot> types = null;

    @Ignore
    @SerializedName("sprites")
    private Sprites sprites = null;

    @Ignore
    private List<Trait> traitList = null;

    @SerializedName("weight")
    private int weight;

    public Pokemon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<StatNode> getStats() {
        return stats;
    }

    public void setStats(List<StatNode> stats) {
        this.stats = stats;
    }

    public List<PokemonTypeSlot> getTypes() {
        return types;
    }

    public void setTypes(List<PokemonTypeSlot> types) {
        this.types = types;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Trait> getTraitList() {
        if (traitList == null) {
            traitList = new ArrayList<>();
            StringBuilder types = new StringBuilder();
            for (PokemonTypeSlot pokemonTypeSlot : this.types) {
                if (types.toString().length() > 0)
                    types.append(", ");
                types.append(pokemonTypeSlot.getType().getName());
            }

            Trait pokemonType = new Trait("Type", types.toString());
            traitList.add(pokemonType);

            for (StatNode stat: this.stats) {
                Trait trait = new Trait(stat.getStat().getName(), String.valueOf(stat.getBaseStat()));
                traitList.add(trait);
            }
        }

        return traitList;
    }
}

package uk.co.sooce.pokedex.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pokedex {

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<PokedexNode> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PokedexNode> getResults() {
        return results;
    }

    public void setResults(List<PokedexNode> results) {
        this.results = results;
    }

    /**
     * Dummy constructor for test
     * */
    public Pokedex(int count) {
        this.count = count;
        results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            PokedexNode node = new PokedexNode(String.format(Locale.getDefault(), "Pokemon %d", i), "", i);
            results.add(node);
        }
    }
}

package uk.co.sooce.pokedex.model;

import com.google.gson.annotations.SerializedName;

import android.util.Log;

public class PokedexNode {

    private static final String POKE_IMAGE_URL_PREFIX = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private static final String POKE_IMAGE_URL_SUFFIX = ".png";

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    private int id;

    public PokedexNode(String name, String url, int id) {
        this.name = name;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpriteUrl() {
        String spriteUrl = POKE_IMAGE_URL_PREFIX + getId() + POKE_IMAGE_URL_SUFFIX;
        Log.d("POKEMON", spriteUrl);
        return spriteUrl;
    }

    public int getId() {
        if (id != 0)
            return id;
        try {
            if (this.url != null && this.url.length() > 0) {
                int startIndex = url.indexOf("/pokemon/") + 9;
                String idStr = url.substring(startIndex);
                if (idStr.substring(idStr.length() - 1).equals("/"))
                    idStr = idStr.substring(0, idStr.length() - 1);
                return Integer.parseInt(idStr);
            } else return 0;
        } catch (Exception e){
            return 0;
        }
    }
}

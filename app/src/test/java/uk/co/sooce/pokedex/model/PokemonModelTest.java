package uk.co.sooce.pokedex.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PokemonModelTest {

    private final String name = "Bulbasaur";
    private final int height = 14;

    @Mock
    Pokemon pokemon;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(pokemon.getName()).thenReturn(name);
        Mockito.when(pokemon.getHeight()).thenReturn(height);
    }

    @Test
    public void testPokemonName() {
        Assert.assertEquals("Bulbasaur", pokemon.getName());
    }

    @Test
    public void testPokemonHeight() {
        Assert.assertEquals(14, pokemon.getHeight());
    }

}

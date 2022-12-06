package com.city.builder.game.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestTileType {
    @Test
    public void testEqual(){
        final TileType t1 = TileType.TREE;
        final TileType t2 = TileType.TREE;
        assertEquals(t1,t2);
    }
    @Test
    public void testNotEqual(){
        final TileType t1 = TileType.TREE;
        final TileType t2 = TileType.EMPTY;
        assertNotEquals(t1,t2);
    }
}

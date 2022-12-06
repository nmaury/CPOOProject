package com.city.builder.game.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestPieceType {
    @Test
    public void testEqual(){
        final PieceType t1 = PieceType.HOUSE;
        final PieceType t2 = PieceType.HOUSE;
        assertEquals(t1,t2);
    }
    @Test
    public void testNotEqual(){
        final PieceType t1 = PieceType.HOUSE;
        final PieceType t2 = PieceType.CIRCUS;
        assertNotEquals(t1,t2);
    }
}

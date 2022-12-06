package com.city.builder.game.model;

public class Piece {

private PieceType type;

    public Piece(final PieceType type) {
        this.type = type;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(final PieceType type) {
        this.type = type;
    }
}

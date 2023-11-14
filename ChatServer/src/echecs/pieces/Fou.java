package echecs.pieces;

import com.echecs.Position;

public class Fou extends Piece {
    public Fou(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {

        return false;
    }
}

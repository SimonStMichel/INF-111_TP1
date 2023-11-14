package echecs.pieces;

import com.echecs.Position;

public class Tour extends Piece {
    public Tour(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {

        return false;
    }
}

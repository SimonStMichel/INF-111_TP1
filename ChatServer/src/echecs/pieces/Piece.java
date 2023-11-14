package echecs.pieces;

import com.echecs.Position;

public abstract class Piece {
    protected char couleur; //b ou n
    public Piece(char couleur) {
        this.couleur = couleur;
    }

    public char getCouleur() {
        return couleur;
    }

    /**
     * Vérifie si la piece peut se déplacer d'une position à une autre.
     *
     * @param pos1 Position La position initiale
     * @param pos2 Position La position finale
     * @param echiquier Piece[][] Échiquier contenant les pièces d'une partie d'échecs
     * @return boolean true, si la pièce peut se déplacer de la position pos1 à la position pos2, false sinon
     */
    public abstract boolean peutSeDeplacer(Position pos1, Position pos2, Piece echiquier[][]);

    public boolean peutSeDeplacer(echecs.Position initiale, echecs.Position finale, Piece[][] echiquier) {
        return true;
    }


    public boolean peutSeDeplacer(Position positionPiece, echecs.Position positionRoi, Piece[][] echiquier) {
        return true;
    }
}

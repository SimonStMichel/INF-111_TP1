package com.echecs.pieces;

import com.echecs.Position;

/**
 * Représente une pièce dans un jeu d'échecs. Cette classe abstraite constitue
 * la base pour les classes de pièces concrètes.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */

public class Pion extends Piece {
    public Pion(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {

        return false;
    }
}

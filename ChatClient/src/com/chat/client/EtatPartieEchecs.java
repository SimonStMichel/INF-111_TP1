package com.chat.client;

/**
 * Cette classe étend la classe Client pour lui ajouter des fonctionnalités
 * spécifiques au chat et au jeu d'échecs en réseau.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class EtatPartieEchecs {
    private char[][] etatEchiquier;

    // Constructeur sans arguments pour l'initialisation à l'état initial d'une partie d'échecs
    public EtatPartieEchecs() {
        etatEchiquier = new char[][]{
                {'r', 'c', 'f', 'd', 'r', 'f', 'c', 'r'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'R', 'C', 'F', 'D', 'R', 'F', 'C', 'R'}
        };
    }

    // Getter pour etatEchiquier
    public char[][] getEtatEchiquier() {
        return etatEchiquier;
    }

    // Setter pour etatEchiquier
    public void setEtatEchiquier(char[][] nouvelEtat) {
        this.etatEchiquier = nouvelEtat;
    }

    // Redéfinition de la méthode toString() pour afficher l'état de la partie sous forme matricielle
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            result.append(8 - i).append(" ");
            for (int j = 0; j < 8; j++) {
                result.append(etatEchiquier[i][j]).append(" ");
            }
            result.append(8 - i).append("\n");
        }
        result.append("  a b c d e f g h\n");
        return result.toString();
    }
}


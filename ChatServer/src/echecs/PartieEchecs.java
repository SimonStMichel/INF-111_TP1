package echecs;

import echecs.pieces.Piece;
import echecs.pieces.Roi;

/**
 * Représente une partie de jeu d'échecs. Orcheste le déroulement d'une partie :
 * déplacement des pièces, vérification d'échec, d'échec et mat,...
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class PartieEchecs {
    /**
     * Grille du jeu d'échecs. La ligne 0 de la grille correspond à la ligne
     * 8 de l'échiquier. La colonne 0 de la grille correspond à la colonne a
     * de l'échiquier.
     */
    private Piece[][] echiquier;
    private String aliasJoueur1, aliasJoueur2;
    private char couleurJoueur1, couleurJoueur2;

    /**
     * La couleur de celui à qui c'est le tour de jouer (n ou b).
     */
    private char tour = 'b'; //Les blancs commencent toujours
    /**
     * Crée un échiquier de jeu d'échecs avec les pièces dans leurs positions
     * initiales de début de partie.
     * Répartit au hasard les couleurs n et b entre les 2 joueurs.
     */

    private void initialiserEchiquier() {
        // Placement des pièces pour les Blancs
        echiquier[0][0] = new Piece('b') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[0][1] = new Piece('b') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[0][2] = new Piece('b') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[0][3] = new Piece('b') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[0][4] = new Roi('b');
        echiquier[0][5] = new Piece('b') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[0][6] = new Piece('b') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[0][7] = new Piece('b') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };

        for (int i = 0; i < 8; i++) {
            echiquier[1][i] = new Piece('b') {
                @Override
                public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                    return false;
                }
            };
        }

        // Les pièces pour les Noirs sont placées symétriquement
        echiquier[7][0] = new Piece('n') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[7][1] = new Piece('n') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[7][2] = new Piece('n') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[7][3] = new Piece('n') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[7][4] = new Roi('n');
        echiquier[7][5] = new Piece('n') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[7][6] = new Piece('n') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };
        echiquier[7][7] = new Piece('n') {
            @Override
            public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                return false;
            }
        };

        for (int i = 0; i < 8; i++) {
            echiquier[6][i] = new Piece('n') {
                @Override
                public boolean peutSeDeplacer(com.echecs.Position pos1, com.echecs.Position pos2, Piece[][] echiquier) {
                    return false;
                }
            };
        }
    }
    public PartieEchecs() {
        echiquier = new Piece[8][8];
        // Initialiser la matrice
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                echiquier[i][j] = null; // Ou une autre valeur par défaut selon votre logique
            }
        }
        //Placement des pièces :
        initialiserEchiquier();
    }



        /**
     * Change la main du jeu (de n à b ou de b à n).
     */
    public void changerTour() {
        if (tour=='b')
            tour = 'n';
        else
            tour = 'b';
    }
    /**
     * Tente de déplacer une pièce d'une position à une autre sur l'échiquier.
     * Le déplacement peut échouer pour plusieurs raisons, selon les règles du
     * jeu d'échecs. Par exemples :
     *  Une des positions n'existe pas;
     *  Il n'y a pas de pièce à la position initiale;
     *  La pièce de la position initiale ne peut pas faire le mouvement;
     *  Le déplacement met en échec le roi de la même couleur que la pièce.
     *
     * @param initiale Position la position initiale
     * @param finale Position la position finale
     *
     * @return boolean true, si le déplacement a été effectué avec succès, false sinon
     */
    public boolean deplace(Position initiale, Position finale) {
        // throw new NotImplementedException();
        if (!estPositionValide(initiale) || !estPositionValide(finale)) {
            return false;
    }

        // Récupérer la pièce à la position initiale
        Piece pieceADeplacer = echiquier[initiale.getLigne()][initiale.getColonne()];

        // Vérifier s'il y a une pièce à déplacer à la position initiale
        if (pieceADeplacer == null) {
            return false;
        }

        // Vérifier si la couleur de la pièce correspond à la couleur du joueur qui a la main
        if (pieceADeplacer.getCouleur() != tour) {
            return false;
        }

        // Vérifier s'il n'y a pas à la position finale une pièce de même couleur que la pièce à déplacer
        if (echiquier[finale.getLigne()][finale.getColonne()] != null &&
                echiquier[finale.getLigne()][finale.getColonne()].getCouleur() == pieceADeplacer.getCouleur()) {
            return false;
        }

        // Vérifier les conditions pour le roque
        if (pieceADeplacer instanceof Roi) {
            // conditions spécifiques au roque
        }

        // Appeler la méthode peutSeDeplacer() avec les positions initiale et finale
        boolean deplacementValide = pieceADeplacer.peutSeDeplacer(initiale, finale, echiquier);

        // Si le déplacement est valide, effectuer le déplacement et changer de tour
        if (deplacementValide) {
            // Effectuer le déplacement
            echiquier[finale.getLigne()][finale.getColonne()] = pieceADeplacer;
            echiquier[initiale.getLigne()][initiale.getColonne()] = null;

            // Changer de tour
            changerTour();
        }

        return deplacementValide;
    }

    // Méthode utilitaire pour vérifier si une position est valide
    private boolean estPositionValide(Position position) {
        int ligne = position.getLigne();
        int colonne = position.getColonne();
        return ligne >= 0 && ligne < 8 && colonne >= 0 && colonne < 8;
    }

    /**
     * Vérifie si un roi est en échec et, si oui, retourne sa couleur sous forme
     * d'un caractère n ou b.
     * Si la couleur du roi en échec est la même que celle de la dernière pièce
     * déplacée, le dernier déplacement doit être annulé.
     * Les 2 rois peuvent être en échec en même temps. Dans ce cas, la méthode doit
     * retourner la couleur de la pièce qui a été déplacée en dernier car ce
     * déplacement doit être annulé.
     *
     * @return char Le caractère n, si le roi noir est en échec, le caractère b,
     * si le roi blanc est en échec, tout autre caractère, sinon.
     */
    public char estEnEchec() {
            //throw new NotImplementedException();
        Position positionRoi = trouvePositionRoi();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (echiquier[i][j] != null && echiquier[i][j].getCouleur() != tour) {
                    com.echecs.Position positionPiece = new com.echecs.Position((char) i, (byte) j);
                    if (echiquier[i][j].peutSeDeplacer(positionPiece, positionRoi, echiquier)) {
                        return echiquier[positionRoi.getLigne()][positionRoi.getColonne()].getCouleur();
                    }
                }
            }
        }


        return ' ';
    }
    private Position trouvePositionRoi() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (echiquier[i][j] instanceof Roi && echiquier[i][j].getCouleur() == tour) {
                    return new Position((char) i, (byte) j);
                }
            }
        }

        return new Position((char) -1, (byte) -1);
    }
    /**
     * Retourne la couleur n ou b du joueur qui a la main.
     *
     * @return char la couleur du joueur à qui c'est le tour de jouer.
     */
    public char getTour() {
        return tour;
    }
    /**
     * Retourne l'alias du premier joueur.
     * @return String alias du premier joueur.
     */
    public String getAliasJoueur1() {
        return aliasJoueur1;
    }
    /**
     * Modifie l'alias du premier joueur.
     * @param aliasJoueur1 String nouvel alias du premier joueur.
     */
    public void setAliasJoueur1(String aliasJoueur1) {
        this.aliasJoueur1 = aliasJoueur1;
    }
    /**
     * Retourne l'alias du deuxième joueur.
     * @return String alias du deuxième joueur.
     */
    public String getAliasJoueur2() {
        return aliasJoueur2;
    }
    /**
     * Modifie l'alias du deuxième joueur.
     * @param aliasJoueur2 String nouvel alias du deuxième joueur.
     */
    public void setAliasJoueur2(String aliasJoueur2) {
        this.aliasJoueur2 = aliasJoueur2;
    }
    /**
     * Retourne la couleur n ou b du premier joueur.
     * @return char couleur du premier joueur.
     */
    public char getCouleurJoueur1() {
        return couleurJoueur1;
    }


    /**
     * Retourne la couleur n ou b du deuxième joueur.
     * @return char couleur du deuxième joueur.
     */
    public char getCouleurJoueur2() {
        return couleurJoueur2;
    }
}
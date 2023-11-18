package com.chat.client;

/**
 * Cette classe étend la classe Client pour lui ajouter des fonctionnalités
 * spécifiques au chat et au jeu d'échecs en réseau.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ClientChat extends Client {
    private EtatPartieEchecs etatPartie;

    // Getter
    public EtatPartieEchecs getEtatPartie() {
        return etatPartie;
    }

    // Setter
    public void setEtatPartie(EtatPartieEchecs etatPartie) {
        this.etatPartie = etatPartie;
    }

    // Méthode appelée pour mettre à jour l'état de la partie après un déplacement validé par le serveur
    public void mettreAJourEtatPartie(char[][] nouvelEtat) {
        etatPartie.setEtatEchiquier(nouvelEtat);
    }

    public void nouvellePartie() {
        etatPartie = new EtatPartieEchecs();
    }


}


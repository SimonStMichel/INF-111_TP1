package com.chat.serveur;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;

/**
 * Cette classe repr�sente un gestionnaire d'�v�nement d'un serveur. Lorsqu'un serveur re�oit un texte d'un client,
 * il cr�e un �v�nement � partir du texte re�u et alerte ce gestionnaire qui r�agit en g�rant l'�v�nement.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'�v�nements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire g�re des �v�nements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * M�thode qui envoie un message re�u par le serveur � tout les utilisateurs connect�s.
     *
     * @param str le message.
     * @param aliasExpediteur l'alias de l'exp�diteur du message
     */
    public void envoyerATousSauf(String str, String aliasExpediteur) {
        for (Connexion connexion:serveur.connectes) {
            if(connexion.getAlias().equals(aliasExpediteur)) continue;
            connexion.envoyer(str);
        }
    }

    /**
     * M�thode de gestion d'�v�nements. Cette m�thode contiendra le code qui g�re les r�ponses obtenues d'un client.
     *
     * @param evenement L'�v�nement � g�rer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String msg, typeEvenement, aliasExpediteur;
        ServeurChat serveur = (ServeurChat) this.serveur;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "EXIT": //Ferme la connexion avec le client qui a envoy� "EXIT":
                    cnx.envoyer("END");
                    serveur.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des alias des personnes connect�es :
                    cnx.envoyer("LIST " + serveur.list());
                    break;

                //Ajoutez ici d�autres case pour g�rer d�autres commandes.
                case "MSG": //Envoie un message d'un utilisateur � tout le monde sauf lui :
                    String message = cnx.getAlias() + " >> " + evenement.getArgument();
                    envoyerATousSauf(message,  cnx.getAlias());
                    serveur.ajouterHistorique(message);
                    break;

                case "INVITE":
                    String alias1 = cnx.getAlias();
                    String alias2 = evenement.getArgument();
                    creerInvitation(alias1, alias2);
                    break;

                case "JOIN":
                    String alias1Join = cnx.getAlias();
                    String alias2Join = evenement.getArgument();
                    gererCommandeJoin(alias1Join, alias2Join);
                    break;

                case "DECLINE":
                    String alias1Decline = cnx.getAlias();
                    String alias2Decline = evenement.getArgument();
                    supprimerInvitation(alias1Decline, alias2Decline);
                    informerUtilisateur(alias2Decline, alias1Decline + " a refusé votre invitation.");
                    break;

                case "INV":
                    String aliasInv = cnx.getAlias();
                    envoyerListeInvitations(aliasInv);
                    break;

                case "PRV":
                    String[] arguments = evenement.getArgument().split(" ", 2);
                    String aliasPrv = arguments[0];
                    String messagePrv = arguments.length > 1 ? arguments[1] : "";
                    envoyerMessagePrive(cnx.getAlias(), aliasPrv, messagePrv);
                    break;

                case "QUIT":
                    String aliasQuit = cnx.getAlias();
                    String alias2Quit = evenement.getArgument();
                    quitterSalonPrive(aliasQuit, alias2Quit);
                    break;

                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }

    private void creerInvitation(String alias1, String alias2) {
    }

    private void informerUtilisateur(String alias2Decline, String s) {
    }


    private void gererCommandeJoin(String alias1, String alias2) {
    }

    private void supprimerInvitation(String alias1, String alias2) {
    }

    private void envoyerListeInvitations(String alias) {
    }
    private void envoyerMessagePrive(String alias1, String alias2, String message) {

    }
    private void quitterSalonPrive(String alias1, String alias2) {

    }
}



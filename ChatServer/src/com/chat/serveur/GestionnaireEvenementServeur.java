package com.chat.serveur;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;
import com.echecs.PartieEchecs;

import java.util.Vector;

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
        String msg, typeEvenement;
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

                //Ajoutez ici d'autres case pour gérer d'autres commandes.
                case "MSG": //Envoie un message d'un utilisateur à tout le monde sauf lui :
                    String message = cnx.getAlias() + " >> " + evenement.getArgument();
                    envoyerATousSauf(message,  cnx.getAlias());
                    serveur.ajouterHistorique(message);
                    break;

                case "JOIN":
                    Invitation invitation = new Invitation(cnx.getAlias(), evenement.getArgument());
                    Invitation invitationInverse = new Invitation(evenement.getArgument(), cnx.getAlias());

                    SalonPrive salon = new SalonPrive(cnx.getAlias(), evenement.getArgument());
                    SalonPrive salonInverse = new SalonPrive(evenement.getArgument(), cnx.getAlias());

                    // Si l'invitation ou le salon existe déjà
                    if(serveur.invitationExiste(invitation) || serveur.salonExiste(salon) || serveur.salonExiste(salonInverse)) cnx.envoyer("ERREUR");
                    // Si l'invitation est accepté
                    else if (serveur.invitationExiste(invitationInverse)) {
                            serveur.ajouterSalon(salon);
                            serveur.supprimerInvitation(invitation);
                            cnx.envoyer("NOUVEAU SALON AVEC " + salon.getAliasInvite());
                            for (Connexion connexion : serveur.connectes) {
                                if (connexion.getAlias().equals(salon.getAliasInvite())) {
                                    connexion.envoyer("NOUVEAU SALON AVEC " + salon.getAliasHote());
                                }
                            }
                    }
                    // Création d'une invitation
                    else {
                        serveur.ajouterInvitation(invitation);
                        for (Connexion connexion:serveur.connectes) {
                            if(connexion.getAlias().equals(evenement.getArgument())) connexion.envoyer(cnx.getAlias() + " VOUS A ENVOYÉ UNE INVITATION");
                        }
                    }
                    break;

                case "DECLINE":
                    invitation = new Invitation(evenement.getArgument(), cnx.getAlias());
                    invitationInverse = new Invitation(cnx.getAlias(), evenement.getArgument());
                    // Si l'invitation n'existe pas
                    if (!serveur.invitationExiste(invitation) || !serveur.invitationExiste(invitationInverse)) cnx.envoyer("L'INVITATION N'EXISTE PAS");
                    // Sinon
                    else{
                        cnx.envoyer("L'invitation a été décliné");
                        for (Connexion connexion:serveur.connectes) {
                            if(connexion.getAlias().equals(evenement.getArgument())) connexion.envoyer(invitation.getAliasInvite() + " A DÉCLINÉ VOTRE INVITATION");
                        }
                        serveur.supprimerInvitation(invitation);
                    }
                    break;

                case "INV":
                    cnx.envoyer("LIST_INV " + serveur.listInvitations(cnx));
                    break;

                case "PRV":
                    salon = new SalonPrive(cnx.getAlias(), evenement.getArgument());
                    salonInverse = new SalonPrive(evenement.getArgument(), cnx.getAlias());
                    if(serveur.salonExiste(salon) || serveur.salonExiste(salonInverse)) {
                        String[] arguments = evenement.getArgument().split(" ", 2);
                        for (Connexion connexion:serveur.connectes) {
                            if(connexion.getAlias().equals(arguments[0])) connexion.envoyer("PRV " + cnx.getAlias() + " >> " + arguments[1]);
                        }
                    } else cnx.envoyer("LE SERVEUR N'EXISTE PAS");
                    break;

                case "QUIT":
                    salon = new SalonPrive(cnx.getAlias(), evenement.getArgument());
                    salonInverse = new SalonPrive(evenement.getArgument(), cnx.getAlias());

                    if(serveur.salonExiste(salon) || serveur.salonExiste(salonInverse)) {
                        // Indiquer que le salon est fermé aux membres
                        cnx.envoyer("FERMETURE DU SALON PRIVÉE AVEC " + evenement.getArgument());

                        for (Connexion connexion:serveur.connectes) {
                            if(connexion.getAlias().equals(evenement.getArgument())) connexion.envoyer(cnx.getAlias() + " A FERMÉ LE SALON PRIVÉ");
                        }
                        // Suppression du salon
                        if(cnx.getAlias().equals(salon.getAliasHote())) serveur.supprimerSalon(salon);
                        else serveur.supprimerSalon(salonInverse);
                    }
                    else cnx.envoyer("LE SALON N'EXISTE PAS");
                    break;

                case "CHESS":
                    salon = new SalonPrive(cnx.getAlias(), evenement.getArgument());
                    salonInverse = new SalonPrive(evenement.getArgument(), cnx.getAlias());

                    Invitation partie = new Invitation(cnx.getAlias(), evenement.getArgument());
                    Invitation partieInverse = new Invitation(evenement.getArgument(), cnx.getAlias());

                    // Si le salon n'existe pas
                    if(!serveur.salonExiste(salon) && !serveur.salonExiste(salonInverse)){
                        cnx.envoyer("LE SALON N'EXISTE PAS AVEC " + evenement.getArgument());
                        return;
                    }
                    // Si l'invitation partie existe
                    else if(serveur.invitationPartieExiste(partie) || serveur.invitationPartieExiste(partieInverse)) {
                        // Si l'hote de l'invitation rfait une demande
                        if(cnx.getAlias().equals(partie.getAliasHote())) cnx.envoyer("DEMANDE DÉJÀ ENVOYÉ");
                        // Si l'autre utilisateur accepte
                        else {
                            PartieEchecs nouvellePartie = new PartieEchecs();
                            nouvellePartie.setAliasJoueur1(partie.getAliasHote());
                            nouvellePartie.setAliasJoueur2(partie.getAliasInvite());

                            cnx.envoyer("CHESSOK " + nouvellePartie.getCouleurJoueur1());
                            for (Connexion connexion:serveur.connectes) {
                                if(connexion.getAlias().equals(partie.getAliasHote())) {
                                    connexion.envoyer("CHESSOK " + nouvellePartie.getCouleurJoueur2());
                                }
                            }
                            salon.setPartieEchecs(nouvellePartie);
                            serveur.supprimerInvitation(partie);
                        }
                    }
                    // Sinon on créé une nouvelle invitation
                    break;

                default: // Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
    private void envoyerMessagePrive(String alias1, String alias2, String message) {

    }
    private void quitterSalonPrive(String alias1, String alias2) {

    }
}



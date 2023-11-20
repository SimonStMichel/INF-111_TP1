package com.chat.client;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;

/**
 * Cette classe représente un gestionnaire d'événement d'un client. Lorsqu'un client reçoit un texte d'un serveur,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementClient implements GestionnaireEvenement {
    private ClientChat client;

    /**
     * Construit un gestionnaire d'événements pour un client.
     *
     * @param client Client Le client pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementClient(Client client) {
        this.client = (ClientChat) client;
    }
    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un serveur.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String typeEvenement, arg;
        String[] membres;
        String[] messages;
        String[] invitations;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "END" : //Le serveur demande de fermer la connexion
                    client.deconnecter(); //On ferme la connexion
                    break;
                case "LIST" : //Le serveur a renvoyé la liste des connectés
                    arg = evenement.getArgument();
                    membres = arg.split(":");
                    System.out.println("\t\t"+membres.length+" personnes dans le salon :");
                    for (String s:membres)
                        System.out.println("\t\t\t- "+ s);
                    break;
                case "HIST" : //Le serveur a renvoyé l'historique des messages
                    messages = evenement.getArgument().split("\n");
                    for (String msg:messages)
                        System.out.println("\t\t\t." + msg);
                    break;
                case "LIST_INV" : //Le serveur a renvoyé les invitations en cours
                    arg = evenement.getArgument();
                    invitations = arg.split(":");
                    if(invitations[0].equals("AUCUNE INVITATION")) System.out.println("\t\t" + invitations[0]);
                    else{
                        System.out.println("\t\t"+invitations.length+" invitation(s) actives :");
                        for (String hote:invitations)
                            System.out.println("\t\t\t- "+ hote);
                    }
                    break;
                case "CHESSOK" : //Le serveur a renvoyé les invitations en cours
                    client.nouvellePartie();
                    break;
                default: //Afficher le texte recu :
                    System.out.println("\t\t\t."+evenement.getType()+" "+evenement.getArgument());
            }
        }
    }
}

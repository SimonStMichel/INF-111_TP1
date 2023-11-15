package com.chat.serveur;

import com.chat.commun.net.Connexion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Cette classe étend (hérite) la classe abstraite Serveur et y ajoute le nécessaire pour que le
 * serveur soit un serveur de chat.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-15
 */
public class ServeurChat extends Serveur {
    private Vector<String> historiqueMessages = new Vector<>();
    private Vector<Invitation> historiqueInvitations = new Vector<>();
    private Vector<SalonPrive> salonPrivesActifs = new Vector<>();

    /**
     * Crée un serveur de chat qui va écouter sur le port spécifié.
     *
     * @param port int Port d'écoute du serveur
     */
    public ServeurChat(int port) {
        super(port);
    }

    @Override
    public synchronized boolean ajouter(Connexion connexion) {
        String hist = this.historique();
        if ("".equals(hist)) {
            connexion.envoyer("OK");
        }
        else {
            connexion.envoyer("HIST " + hist);
        }
        return super.ajouter(connexion);
    }
    /**
     * Valide l'arrivée d'un nouveau client sur le serveur. Cette redéfinition
     * de la méthode héritée de Serveur vérifie si le nouveau client a envoyé
     * un alias composé uniquement des caractères a-z, A-Z, 0-9, - et _.
     *
     * @param connexion Connexion la connexion représentant le client
     * @return boolean true, si le client a validé correctement son arrivée, false, sinon
     */
    @Override
    protected boolean validerConnexion(Connexion connexion) {

        String texte = connexion.getAvailableText().trim();
        char c;
        int taille;
        boolean res = true;
        if ("".equals(texte)) {
            return false;
        }
        taille = texte.length();
        for (int i=0;i<taille;i++) {
            c = texte.charAt(i);
            if ((c<'a' || c>'z') && (c<'A' || c>'Z') && (c<'0' || c>'9')
                    && c!='_' && c!='-') {
                res = false;
                break;
            }
        }
        if (!res)
            return false;
        for (Connexion cnx:connectes) {
            if (texte.equalsIgnoreCase(cnx.getAlias())) { //alias déjà utilisé
                res = false;
                break;
            }
        }
        connexion.setAlias(texte);
        return true;
    }

    /**
     * Retourne la liste des alias des connectés au serveur dans une chaîne de caractères.
     *
     * @return String chaîne de caractères contenant la liste des alias des membres connectés sous la
     * forme alias1:alias2:alias3 ...
     */
    public String list() {
        String s = "";
        for (Connexion cnx:connectes)
            s+=cnx.getAlias()+":";
        return s;
    }

    /**
     * Retourne la liste des invitations envoyés à un utilisateur
     *
     * @param utilisateur Connexion Référence à l'utilisateur qui demande la liste d'invitations
     *
     * @return String chaîne de caractères contenant la liste invitations envoyés à un utilisateur
     */
    public String listInvitations(Connexion utilisateur) {
        Vector<String> invitations = new Vector<String>();
        String s = "";

        for (Invitation inv:historiqueInvitations) {
            if(inv.getAliasInvite().equals(utilisateur.getAlias())) invitations.add(inv.getAliasHote());
        }

        if(invitations.isEmpty()) s += "AUCUNE INVITATION:";
        else {
           for (String inv:invitations)
                s += inv + ":";
        }
        return s;
    }
    /**
     * Retourne la liste des messages de l'historique de chat dans une chaîne
     * de caractères.
     *
     * @return String chaîne de caractères contenant la liste des messages envoyés par les membres connectés sous la
     * forme message1\nmessage2\nmessage3 ...
     */
    public String historique() {
        String s = "";
        if(!historiqueMessages.isEmpty()) s = String.join("\n", historiqueMessages);
        return s;
    }

    /**
     * Méthode qui ajoute un message à l'historique de messages
     *
     * @param String msg string du message sous forme : alias >> message
     */
    public void ajouterHistorique(String msg) {
        historiqueMessages.add(msg);
    }

    /**
     * Méthode qui ajoute une invitation à l'historique des invitations
     *
     * @param Invitation invitation Objet de type invitation
     */
    public void ajouterInvitation(Invitation invitation) {
        historiqueInvitations.add(invitation);
    }

    /**
     * Méthode qui supprime une invitation de l'historique des invitations
     *
     * @param Invitation invitation Objet de type invitation
     */
    public void supprimerInvitation(Invitation invitation) {
        historiqueInvitations.remove(invitation);
    }

    /**
     * Méthode qui ajoute un salon à la liste des salons actifs
     *
     * @param SalonPrive salon Objet de type SalonPrive
     */
    public void ajouterSalon(SalonPrive salon) {
        salonPrivesActifs.add(salon);
    }

    /**
     * Méthode qui retourne true si l'invitation existe
     *
     * @param Invitation invitation Objet de type invitation
     */
    public boolean invitationExiste(Invitation invitation) {
        for (Invitation inv : historiqueInvitations) {
            if(inv.getAliasInvite().equals(invitation.getAliasInvite())  && inv.getAliasHote().equals(invitation.getAliasHote())) return true;
        }
        return false;
    }

    /**
     * Méthode qui retourne true si un salon existe
     *
     * @param SalonPrive salon Objet de type invitation
     */
    public boolean salonExiste(SalonPrive salon) {
        for (SalonPrive sal : salonPrivesActifs) {
            if(sal.getAliasInvite().equals(salon.getAliasInvite())  && sal.getAliasHote().equals(salon.getAliasHote())) return true;
        }
        return false;
    }
}

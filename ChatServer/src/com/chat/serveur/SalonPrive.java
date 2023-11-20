package com.chat.serveur;

import com.echecs.PartieEchecs;

public class SalonPrive {
    private String aliasHote;
    private String aliasInvite;
    private PartieEchecs partieEchecs;

    public PartieEchecs getPartieEchecs() { return partieEchecs; }
    public void setPartieEchecs(PartieEchecs partieEchecs) { this.partieEchecs = partieEchecs; }

    public String getAliasHote() {
        return aliasHote;
    }

    public String getAliasInvite() {
        return aliasInvite;
    }

    public SalonPrive(String aliasHote, String aliasInvite) {
        this.aliasHote = aliasHote;
        this.aliasInvite = aliasInvite;
    }
}

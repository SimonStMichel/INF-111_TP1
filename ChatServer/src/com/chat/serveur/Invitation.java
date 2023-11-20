package com.chat.serveur;

import java.util.Objects;

public class Invitation {
    private String aliasHote;
    private String aliasInvite;

    public String getAliasHote() {
        return aliasHote;
    }

    public String getAliasInvite() {
        return aliasInvite;
    }

    public Invitation(String aliasHote, String aliasInvite) {
        this.aliasHote = aliasHote;
        this.aliasInvite = aliasInvite;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Invitation other = (Invitation) obj;
        return Objects.equals(this.aliasHote, other.aliasHote) && Objects.equals(this.aliasInvite, other.aliasInvite);
    }
}

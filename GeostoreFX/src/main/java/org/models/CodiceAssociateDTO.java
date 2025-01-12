package org.models;

public class CodiceAssociateDTO {
    private Codice codiceAdmin;
    private String emailUtente;

    public Codice getCodiceAdmin() {
        return codiceAdmin;
    }

    public void setCodiceAdmin(Codice codiceAdmin) {
        this.codiceAdmin = codiceAdmin;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }
}

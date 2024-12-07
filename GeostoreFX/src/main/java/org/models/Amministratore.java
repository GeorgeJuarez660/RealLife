package org.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Amministratore extends Cliente{
    private String codeAdmin;

    public String getCodeAdmin() {
        return codeAdmin;
    }

    public void setCodeAdmin(String codeAdmin) {
        this.codeAdmin = codeAdmin;
    }

    public Amministratore() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amministratore cliente = (Amministratore) o;
        return Objects.equals(codeAdmin, cliente.codeAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeAdmin);
    }

    public Amministratore(Integer id, String nome, String cognome, String sesso, Date dataNascita, String email, String password, BigDecimal portafoglio, String telefono, String indirizzo, String codeAdmin) {
        super(id, nome, cognome, sesso, dataNascita, email, password, indirizzo, telefono, portafoglio);
        this.codeAdmin = codeAdmin;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codeAdmin='" + codeAdmin + '\'' +
                '}';
    }
}

package org.models;

import java.sql.Date;
import java.util.Objects;

public class Utente {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;
    private String cognome;
    private String sesso;
    private Date dataNascita;
    private String telefono;
    private String indirizzo;

    public Integer getId() {
        return id;
    }

    public void setCount() {
        count++;
        id = count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Utente() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente cliente = (Utente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(cognome, cliente.cognome) && Objects.equals(sesso, cliente.sesso) && Objects.equals(dataNascita, cliente.dataNascita) && Objects.equals(telefono, cliente.telefono) && Objects.equals(indirizzo, cliente.indirizzo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, sesso, dataNascita, telefono, indirizzo);
    }

    public Utente(Integer id, String nome, String cognome, String sesso, Date dataNascita, String telefono, String indirizzo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dataNascita = dataNascita;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso='" + sesso + '\'' +
                ", dataNascita='" + dataNascita + '\'' +
                ", telefono='" + telefono + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }
}

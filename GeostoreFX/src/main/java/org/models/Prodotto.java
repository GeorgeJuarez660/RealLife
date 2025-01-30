package org.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Prodotto {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;
    private BigDecimal prezzo;
    private Disponibilita disponibilita;
    private Materia materia;
    private Categoria categoria;
    private Integer quantita_disp;

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

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public Disponibilita getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Disponibilita disponibilita) {
        this.disponibilita = disponibilita;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantita_disp() {
        return quantita_disp;
    }

    public void setQuantita_disp(Integer quantita_disp) {
        this.quantita_disp = quantita_disp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;
        return Objects.equals(id, prodotto.id) && Objects.equals(nome, prodotto.nome) && Objects.equals(prezzo, prodotto.prezzo) && Objects.equals(disponibilita, prodotto.disponibilita) && Objects.equals(materia, prodotto.materia) && Objects.equals(categoria, prodotto.categoria) && Objects.equals(quantita_disp, prodotto.quantita_disp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, prezzo, disponibilita, materia, categoria, quantita_disp);
    }

    public Prodotto() {
    }

    public Prodotto(Integer id, String nome, BigDecimal prezzo, Disponibilita disponibilita, Materia materia, Categoria categoria, Integer quantita_disp) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.disponibilita = disponibilita;
        this.materia = materia;
        this.categoria = categoria;
        this.quantita_disp = quantita_disp;
    }

    @Override
    public String toString() {
        return "Oggetto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", disponibilita='" + disponibilita + '\'' +
                ", materia='" + materia + '\'' +
                ", categoria='" + categoria + '\'' +
                ", quantita_disp=" + quantita_disp +
                '}';
    }

    public String checkNotNullProdotto(Prodotto p){
        String canCU = "";
        boolean areThereNull = false;

        if(p.getNome() == null || p.getNome().isEmpty() || p.getNome().isBlank()){
            canCU += "NOME (NULLO) ";
            areThereNull = true;
        }
        if(p.getDisponibilita() == null || p.getDisponibilita().getCode() == null){
            canCU += "DISPONIBILITÀ (NULLO) ";
            areThereNull = true;
        }
        if(p.getCategoria() == null || p.getCategoria().getNome() == null){
            canCU += "CATEGORIA (NULLO) ";
            areThereNull = true;
        }
        if(p.getMateria() == null || p.getMateria().getNome() == null){
            canCU += "MATERIA (NULLO) ";
            areThereNull = true;
        }

        if(areThereNull){
            canCU = "ALCUNI CAMPI DEVONO ESSERE COMPILATI O FORMATTATI BENE: " + canCU;
        }

        return canCU;

    }
}

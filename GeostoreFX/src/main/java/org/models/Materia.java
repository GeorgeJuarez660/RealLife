package org.models;

import java.util.Objects;

public class Materia {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;

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

    public Materia() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia ordine1 = (Materia) o;
        return Objects.equals(id, ordine1.id) && Objects.equals(nome, ordine1.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    public Materia(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    public boolean checkNotNullMateria(Materia m){
        boolean canCU = true;

        if(m.getNome() == null || m.getNome().isEmpty() || m.getNome().isBlank()){
            canCU = false;
        }

        return canCU;

    }
}

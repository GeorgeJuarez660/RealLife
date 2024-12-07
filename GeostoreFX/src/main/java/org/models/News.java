package org.models;

import java.util.Objects;

public class News {
    private Integer id = 0;
    private static Integer count=0;
    private String testo;

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

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News categoria = (News) o;
        return Objects.equals(id, categoria.id) && Objects.equals(testo, categoria.testo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testo);
    }

    public News() {
    }

    public News(String testo) {
        this.testo = testo;
    }

    @Override
    public String toString() {
        return "News{" +
                "testo='" + testo + '\'' +
                '}';
    }
}

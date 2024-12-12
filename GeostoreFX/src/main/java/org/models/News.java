package org.models;

import java.sql.Date;
import java.util.Objects;

public class News {
    private Integer id = 0;
    private static Integer count=0;
    private String testo;
    private Date dataPub;

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

    public Date getDataPub() {
        return dataPub;
    }

    public void setDataPub(Date dataPub) {
        this.dataPub = dataPub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) && Objects.equals(testo, news.testo)&& Objects.equals(dataPub, news.dataPub);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testo, dataPub);
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
                "dataPub='" + dataPub + '\'' +
                '}';
    }
}

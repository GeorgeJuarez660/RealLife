package org.utility;

import org.models.Categoria;

import java.util.HashMap;

public interface categorieCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe CategoriaRepository

    //metodi override per operazioni CRUD locali
    public void insertCategoria(Integer id, Categoria c);
    public HashMap<Integer, Categoria> getCategorie();
    public Categoria getCategoria(String nome);
    public void updateCategoria(Integer id, Categoria newC);
    public boolean deleteCategoria(Integer id);

    //metodi override per operazioni CRUD con database
    public int insertCategoriaWithDB(Integer id, Categoria c);
    public HashMap<Integer, Categoria> getCategorieWithDB();
    public Categoria getCategoriaWithDB(Integer id);
    public int updateCategoriaWithDB(Integer id, Categoria newC);
    public int deleteCategoriaWithDB(Integer id);

}

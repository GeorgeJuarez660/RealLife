package org.utility.crud;

import org.models.Materia;

import java.util.HashMap;

public interface materieCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe MateriaRepository

    //metodi override per operazioni CRUD con database
    public int insertMateriaWithDB(Integer id, Materia m);
    public HashMap<Integer, Materia> getMaterieWithDB();
    public Materia getMateriaWithDB(Integer id);
    public int updateMateriaWithDB(Integer id, Materia newM);
    public int deleteMateriaWithDB(Integer id);

}

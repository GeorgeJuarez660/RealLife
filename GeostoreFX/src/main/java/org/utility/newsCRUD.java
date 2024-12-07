package org.utility;

import org.models.News;

import java.util.HashMap;

public interface newsCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe NewsRepository

    //metodi override per operazioni CRUD con database
    public int insertNotizieWithDB(String testo, Integer idAdmin);
    public HashMap<Integer, News> getNotizieWithDB();
    public News getNotiziaWithDB(String keyword);
    public int updateNotizieWithDB(Integer id, String newTesto, Integer newIdAdmin);
    public int deleteNotizieWithDB(Integer id);

}

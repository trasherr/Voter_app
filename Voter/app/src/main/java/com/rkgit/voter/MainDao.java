package com.rkgit.voter;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    @Delete
    void delete(MainData mainData);

    @Delete
    void reset( List<MainData> mainData);

    @Query("UPDATE table_name SET  name = :sText WHERE ID= :sID")
    void update(int sID, String sText);

    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}

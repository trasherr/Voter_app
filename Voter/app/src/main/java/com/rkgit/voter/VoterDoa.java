
package com.rkgit.voter;


        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.Query;

        import java.util.List;

        import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface VoterDoa {

    @Insert(onConflict = REPLACE)
    void insert(Voter voter);

    @Delete
    void delete(Voter voter);

    @Delete
    void reset( List<Voter> voter);

    @Query("UPDATE table_name2 SET  name = :sText WHERE ID= :sID")
    void update(int sID, String sText);

    @Query("SELECT * FROM table_name2")
    List<Voter> getAll();

}

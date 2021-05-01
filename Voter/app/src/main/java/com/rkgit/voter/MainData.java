package com.rkgit.voter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="table_name")
public class MainData implements Serializable{

    @PrimaryKey(autoGenerate = true)
        private int ID;

    @ColumnInfo(name="name")
        private String candidate;

    @ColumnInfo(name="slogans")
    private String slogans;

    @ColumnInfo(name="votes")
    private int votes;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getSlogans() {
        return slogans;
    }

    public void setSlogans(String slogans) {
        this.slogans = slogans;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}

package imi.softengineer.personalizedmultitionary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ImtiajulIslam on 3/19/2018.
 */

@Entity
public class User  {

    public User(String word, String description, String link) {
        this.word = word;
        this.description = description;
        this.link = link;
    }

    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "word")
    private  String word;

    @ColumnInfo(name = "description")
    private  String description;

    @ColumnInfo(name = "link")
    private  String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}


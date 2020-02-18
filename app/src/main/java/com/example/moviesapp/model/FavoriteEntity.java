package com.example.moviesapp.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by delaroy on 9/6/18.
 */

@Entity(tableName = "favoritetable",indices ={@Index(value = {"movieid"}, unique = true)})
public class FavoriteEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "movieid")
    private int movieid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "userrating")
    private Double userrating;

    @ColumnInfo(name = "posterpath")
    private String posterpath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "value")
    private Boolean value;

    public FavoriteEntity( int movieid, String title, Double userrating, String posterpath, String overview,Boolean value) {

        this.movieid = movieid;
        this.title = title;
        this.userrating = userrating;
        this.posterpath = posterpath;
        this.overview = overview;
        this.value=value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getUserrating() {
        return userrating;
    }

    public void setUserrating(Double userrating) {
        this.userrating = userrating;
    }

    public void setPosterpath(String posterpath){ this.posterpath = posterpath; }

    public String getPosterpath() { return posterpath; }

    public void setOverview(String image) { this.overview = overview; }

    public String getOverview() { return overview; }

}
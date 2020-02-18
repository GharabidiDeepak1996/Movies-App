package com.example.moviesapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//,indices ={@Index(value = {"posterPath"}, unique = true)}
@Entity(tableName = "MovieEntity", indices = {@Index(value = {"movie_id"},
        unique = true)})
public class MovieEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @SerializedName("poster_path")
    @ColumnInfo(name="posterPath")
    private String posterPath;

    @SerializedName("overview")
    @ColumnInfo(name="overview")
    private String overview;

    @SerializedName("id")
    @ColumnInfo(name="movie_id")
    private Integer movieid;

    @SerializedName("release_date")
    @ColumnInfo(name="releaseDate")
    private String releaseDate;

    @SerializedName("original_title")
    @ColumnInfo(name="originalTitle")
    private String originalTitle;

    @SerializedName("original_language")
    @ColumnInfo(name="originalLanguage")
    private String originalLanguage;

    @SerializedName("vote_average")
    @ColumnInfo(name="voteAverage")
    private Double voteAverage;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}

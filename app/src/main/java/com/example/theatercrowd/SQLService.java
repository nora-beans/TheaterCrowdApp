package com.example.theatercrowd;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class SQLService extends Service {

    public static SQLService instance;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Rhino", "Inside onStartCommand");
        int started = super.onStartCommand(intent, flags, startId);
        onCreate();
        setInstance();
        return started;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String PEOPLE_NAME = "Name";
    private static final String PEOPLE_BIRTHDATE = "Birthdate";
    private static final String PEOPLE_BIO = "Biography";
    private static final String PEOPLE_NATIONALITY = "Nationality";
    private static final String PEOPLE = "People";
    private static final String MOVIES_TITLE = "Title";
    private static final String MOVIES_RELEASEYEAR = "ReleaseYear";
    private static final String MOVIES_GENRE = "Genre";
    private static final String MOVIES_DESCRIPTION = "Description";
    private static final String MOVIES = "Movies";
    private static final String MOVIEREVIEWS_REVIEWNUMBER = "ReviewNumber";
    private static final String MOVIEREVIEWS_POSTERNICKNAME = "PosterNickname";
    private static final String MOVIEREVIEWS_MOVIESCORE = "MovieScore";
    private static final String MOVIEREVIEWS_REVIEW = "Review";
    private static final String MOVIEREVIEWS = "MovieReviews";
    private static final String AWARDS_AWARDNAME = "AwardName";
    private static final String AWARDS_AWARDYEAR = "AwardYear";
    private static final String AWARDS_MOVIETITLE = "MovieTitle";
    private static final String AWARDS_MOVIERELEASEYEAR = "MovieReleaseYear";
    private static final String AWARDS_AWARDWINNER = "AwardWinner";
    private static final String AWARDS_AWARDWINNERBIRTHDATE = "AwardWinnerBirthDate";
    private static final String AWARDS = "Awards";
    private static final String REVIEWS_REVIEWNUMBER = "ReviewNumber";
    private static final String REVIEWS_TITLE = "Title";
    private static final String REVIEWS_RELEASEYEAR = "ReleaseYear";
    private static final String REVIEWS = "Reviews";
    private static final String MOVIECREDITS_NAME = "Name";
    private static final String MOVIECREDITS_BIRTHDATE = "Date";
    private static final String MOVIECREDITS_MOVIETITLE = "MovieTitle";
    private static final String MOVIECREDITS_MOVIERELEASEYEAR = "MovieReleaseYear";
    private static final String MOVIECREDITS_ROLE = "Role";
    private static final String MOVIECREDITS = "MovieCredits";

    private MovieDbHelper dbHelper;
    private SQLiteDatabase db;

    public void onCreate() {
        Log.d("Rhino", "onCreate has been called in SQLService");
        setInstance();
        dbHelper = new MovieDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
            db.execSQL(SQL_CREATE_TABLE_PEOPLE);
            db.execSQL(SQL_CREATE_TABLE_MOVIES);
            db.execSQL(SQL_CREATE_TABLE_MOVIEREVIEWS);
            db.execSQL(SQL_CREATE_TABLE_AWARDS);
            db.execSQL(SQL_CREATE_TABLE_REVIEWS);
            db.execSQL(SQL_CREATE_TABLE_MOVIECREDITS);
    }
    private void setInstance() {
        instance = this;
    }

    public static SQLService getInstance() {
        if(instance == null) {
            Log.d("Rhino", "This ain't it, chief");
        } else {
            Log.d("Rhino", "Operation Singleton Design worked aight.");
        }
        return instance;
    }


    private static final String SQL_CREATE_TABLE_PEOPLE =
            "CREATE TABLE IF NOT EXISTS People ( Name Text, Birthdate Date, Bio Text NOT NULL, Nationality Text, PRIMARY KEY (Name, Birthdate))";

    private static final String SQL_CREATE_TABLE_MOVIES =
            "CREATE TABLE IF NOT EXISTS Movies ( Title Text, ReleaseYear Smallint, Genre Text, Description Varchar (250), PRIMARY KEY (Title, ReleaseYear))";

    private static final String SQL_CREATE_TABLE_MOVIEREVIEWS =
            "CREATE TABLE IF NOT EXISTS MovieReviews ( ReviewNumber Smallint, PosterNickname Varchar(30) NOT NULL, MovieScore DECIMAL(1,2)  NOT NULL CHECK(MovieScore <= 10), Review Varchar (300) NOT NULL, PRIMARY KEY (ReviewNumber))";

    private static final String SQL_CREATE_TABLE_AWARDS =
            "CREATE TABLE IF NOT EXISTS Awards ( AwardName Text, AwardYear Smallint, MovieTitle Text NOT NULL, MovieReleaseYear Smallint NOT NULL, AwardWinner Text, AwardWinnerBirthDate Date, PRIMARY KEY (AwardName, AwardYear), FOREIGN KEY (MovieTitle, MovieReleaseYear) REFERENCES Movies (Title, ReleaseYear), FOREIGN KEY (AwardWinner, AwardWinnerBirthDate) REFERENCES People (Name, Birthdate),CHECK (AwardYear >= MovieReleaseYear))";

    private static final String SQL_CREATE_TABLE_REVIEWS =
            "CREATE TABLE IF NOT EXISTS Reviews( ReviewNumber Smallint, Title Text, ReleaseYear Smallint, PRIMARY KEY (ReviewNumber, Title, ReleaseYear), FOREIGN KEY (ReviewNumber) REFERENCES MovieReviews(ReviewNumber), FOREIGN KEY (Title, ReleaseYear) REFERENCES Movies (Title, ReleaseYear))";

    private static final String SQL_CREATE_TABLE_MOVIECREDITS =
            "CREATE TABLE IF NOT EXISTS MovieCredits ( Name Text, Birthdate Date, MovieTitle Text, MovieReleaseYear Smallint, Role Text, PRIMARY KEY (Name, Birthdate, MovieTitle, MovieReleaseYear, Role), FOREIGN KEY (Name, Birthdate) REFERENCES People (Name, Birthdate), FOREIGN KEY (MovieTitle, MovieReleaseYear) REFERENCES Movies (Title, ReleaseYear))";


    public void insertPeople(String name, Date birthdate, String bio, String nationality) {
        ContentValues values = new ContentValues();
        values.put(PEOPLE_NAME, name);
        values.put(PEOPLE_BIRTHDATE, birthdate.toString());
        values.put(PEOPLE_BIO, bio);
        values.put(PEOPLE_NATIONALITY, nationality);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PEOPLE, null, values);
    }

    public void insertMovies(String title, int releaseYear, String genre, String description) {
        ContentValues values = new ContentValues();
        values.put(MOVIES_TITLE, title);
        values.put(MOVIES_RELEASEYEAR, releaseYear);
        values.put(MOVIES_GENRE, genre);
        values.put(MOVIES_DESCRIPTION, description);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MOVIES, null, values);
    }


    public void insertReviews(String title, int releaseYear, String posterNickname, float movieScore, String review) {
        ContentValues values = new ContentValues();
        values.put(REVIEWS_TITLE, title);
        values.put(REVIEWS_RELEASEYEAR, releaseYear);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(REVIEWS, null, values);
        insertMovieReviews(posterNickname, movieScore, review);
    }

    public void insertMovieReviews(String posterNickname, float movieScore, String review) {
        ContentValues values = new ContentValues();
        values.put(MOVIEREVIEWS_POSTERNICKNAME, posterNickname);
        values.put(MOVIEREVIEWS_MOVIESCORE, movieScore);
        values.put(MOVIEREVIEWS_REVIEW, review);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MOVIEREVIEWS, null, values);
    }

    public void insertAwards(String awardName, int awardYear, String movieTitle, int movieReleaseYear, String awardWinner, Date awardWinnerBirthdate) {
        ContentValues values = new ContentValues();
        values.put(AWARDS_AWARDNAME, awardName);
        values.put(AWARDS_AWARDYEAR, awardYear);
        values.put(AWARDS_MOVIETITLE, movieTitle);
        values.put(AWARDS_MOVIERELEASEYEAR, movieReleaseYear);
        values.put(AWARDS_AWARDWINNER, awardWinner);
        values.put(AWARDS_AWARDWINNERBIRTHDATE, awardWinnerBirthdate.toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(AWARDS, null, values);
    }

    public void insertMovieCredits(String name, Date birthdate, String movieTitle, int movieReleaseYear, String role) {
        ContentValues values = new ContentValues();
        values.put(MOVIECREDITS_NAME, name);
        values.put(MOVIECREDITS_BIRTHDATE, birthdate.toString());
        values.put(MOVIECREDITS_MOVIETITLE, movieTitle);
        values.put(MOVIECREDITS_MOVIERELEASEYEAR, movieReleaseYear);
        values.put(MOVIECREDITS_ROLE, role);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MOVIECREDITS, null, values);
    }
}

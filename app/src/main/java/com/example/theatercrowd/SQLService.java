package com.example.theatercrowd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SQLService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String PEOPLE_NAME = "Name";
    private static final String PEOPLE_BIRTHDATE = "Birthdate";
    private static final String PEOPLE_BIO = "Biography";
    private static final String PEOPLE_NATIONALITY = "Nationality";
    private static final String MOVIES_TITLE = "Title";
    private static final Date MOVIES_RELEASEYEAR = "ReleaseYear";
    private static final String MOVIES_GENRE = "Genre";
    private static final String MOVIES_DESCRIPTION = "Description";
    private static final Int MOVIEREVIEWS_REVIEWNUMBER = "ReviewNumber";
    private static final String MOVIEREVIEWS_POSTERNICKNAME = "PosterNickname";
    private static final Float MOVIEREVIEWS_MOVIESCORE = "MovieScore";
    private static final String MOVIEREVIEWS_REVIEW = "Review";
    private static final String AWARDS_AWARDNAME = "AwardName";
    private static final Int AWARDS_AWARDYEAR = "AwardYear";
    private static final String AWARDS_MOVIETITLE = "MovieTitle";
    private static final Int AWARDS_MOVIERELEASEYEAR = "MovieReleaseYear";
    private static final String AWARDS_AWARDWINNER = "AwardWinner";
    private static final Date AWARDS_AWARDWINNERBIRTHDATE = "AwardWinnerBirthDate";
    private static final Int REVIEWS_REVIEWNUMBER = "ReviewNumber";
    private static final String REVIEWS_TITLE = "Title";
    private static final Int REVIEWS_RELEASEYEAR = "ReleaseYear";
    private static final String MOVIECREDITS_NAME = "Name";
    private static final Date MOVIECREDITS_BIRTHDATE = "Date";
    private static final String MOVIECREDITS_MOVIETITLE = "MovieTitle";
    private static final Int MOVIECREDITS_MOVIERELEASEYEAR = "MovieReleaseYear";
    private static final String MOVIECREDITS_ROLE = "Role";


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PEOPLE);
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_);
        db.execSQL(SQL_CREATE_TABLE_PEOPLE);
        db.execSQL(SQL_CREATE_TABLE_PEOPLE);
        db.execSQL(SQL_CREATE_TABLE_PEOPLE);
        db.execSQL(SQL_CREATE_TABLE_PEOPLE);
    }

    private static final String SQL_CREATE_TABLE_PEOPLE =
            "CREATE TABLE People ( Name Text, Birthdate Date, Bio Text NOT NULL, Nationality Text, PRIMARY KEY (Name, Birthdate))";

    private static final String SQL_CREATE_TABLE_MOVIES =
            "CREATE TABLE Movies ( Title Text, ReleaseYear Smallint, Genre Text, Description Varchar (250), PRIMARY KEY (Title, ReleaseYear))";

    private static final String SQL_CREATE_TABLE_MOVIEREVIEWS =
            "CREATE TABLE MovieReviews ( ReviewNumber Smallint AUTO_INCREMENT, PosterNickname Varchar(30) NOT NULL, MovieScore DECIMAL(1,2) UNSIGNED NOT NULL CHECK(MovieScore <= 10), Review Varchar (300) NOT NULL, PRIMARY KEY (ReviewNumber))";

    private static final String SQL_CREATE_TABLE_AWARDS =
            "CREATE TABLE Awards ( AwardName Text, AwardYear Smallint, MovieTitle Text NOT NULL, MovieReleaseYear Smallint NOT NULL, AwardWinner Text, AwardWinnerBirthDate Date, PRIMARY KEY (AwardName, AwardYear), FOREIGN KEY (MovieTitle, MovieReleaseYear) REFERENCES Movies (Title, ReleaseYear), FOREIGN KEY (AwardWinner, AwardWinnerBirthDate) REFERENCES People (Name, Birthdate),CHECK (AwardYear >= MovieReleaseYear))";

    private static final String SQL_CREATE_TABLE_REVIEWS =
            "CREATE TABLE Reviews( ReviewNumber Smallint, Title Text, ReleaseYear Smallint, PRIMARY KEY (ReviewNumber, Title, ReleaseYear), FOREIGN KEY (ReviewNumber) REFERENCES MovieReviews(ReviewNumber), FOREIGN KEY (Title, ReleaseYear) REFERENCES Movies (Title, ReleaseYear))";

    private static final String SQL_CREATE_TABLE_MOVIECREDITS =
            "CREATE TABLE MovieCredit s( Name Text, Birthdate Date, MovieTitle Text, MovieReleaseYear Smallint, Role Text, PRIMARY KEY (Name, Birthdate, MovieTitle, MovieReleaseYear, Role), FOREIGN KEY (Name, Birthdate) REFERENCES People (Name, Birthdate), FOREIGN KEY (MovieTitle, MovieReleaseYear) REFERENCES Movies (Title, ReleaseYear))";

    // Gets the data repository in write mode
    SQLiteDatabase db = dbHelper.getWritableDatabase();



    public void insertPeople(string name, date birthdate, string bio, string nationality) {
        ContentValues values = new ContentValues();
        values.put(FeedEntry.PEOPLE_NAME, name);
        values.put(FeedEntry.PEOPLE_BIRTHDATE, birthdate);
        values.put(FeedEntry.PEOPLE_BIO, bio);
        values.put(FeedEntry.PEOPLE_NATIONALITY, nationality);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.PEOPLE, null, values);
    }

    public void insertMovies(string title, int releaseYear, string genre, string description) {
        ContentValues values = new ContentValues();
        values.put(FeedEntry.MOVIES_TITLE, title);
        values.put(FeedEntry.MOVIES_RELEASEYEAR, releaseYear);
        values.put(FeedEntry.MOVIES_GENRE, genre);
        values.put(FeedEntry.MOVIES_DESCRIPTION, description);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.MOVIES, null, values);
    }

    public void insertMovieReviews(string name, date birthdate, string bio, string nationality) {
        ContentValues values = new ContentValues();
        values.put(FeedEntry.PEOPLE_NAME, name);
        values.put(FeedEntry.PEOPLE_BIRTHDATE, birthdate);
        values.put(FeedEntry.PEOPLE_BIO, bio);
        values.put(FeedEntry.PEOPLE_NATIONALITY, nationality);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.MOVIEREVIEWS, null, values);
    }

    public void insertReviews(string name, date birthdate, string bio, string nationality) {
        ContentValues values = new ContentValues();
        values.put(FeedEntry.PEOPLE_NAME, name);
        values.put(FeedEntry.PEOPLE_BIRTHDATE, birthdate);
        values.put(FeedEntry.PEOPLE_BIO, bio);
        values.put(FeedEntry.PEOPLE_NATIONALITY, nationality);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.PEOPLE, null, values);
    }

    public void insertAwards(string awardName, int awardYear, string movieTitle, int movieReleaseYear, string awardWinner, date awardWinnerBirthdate) {
        ContentValues values = new ContentValues();
        values.put(FeedEntry.AWARDS_AWARDNAME, awardName);
        values.put(FeedEntry.AWARDS_AWARDYEAR, awardYear);
        values.put(FeedEntry.AWARDS_MOVIETITLE, movieTitle);
        values.put(FeedEntry.AWARDS_MOVIERELEASEYEAR, movieReleaseYear);
        values.put(FeedEntry.AWARDS_AWARDWINNER, awardWinner);
        values.put(FeedEntry.AWARDS_AWARDWINNERBIRTHDATE, awardWinnerBirthdate);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.AWARDS, null, values);
    }

    public void insertMovieCredits(string name, date birthdate, string movieTitle, int movieReleaseYear, string role) {
        ContentValues values = new ContentValues();
        values.put(FeedEntry.MOVIECREDITS_NAME, name);
        values.put(FeedEntry.MOVIECREDITS_BIRTHDATE, birthdate);
        values.put(FeedEntry.MOVIECREDITS_MOVIETITLE, movieTitle);
        values.put(FeedEntry.MOVIECREDITS_MOVIERELEASEYEAR, movieReleaseYear);
        values.put(FeedEntry.MOVIECREDITS_ROLE, role);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.MOVIECREDITS, null, values);
    }
}

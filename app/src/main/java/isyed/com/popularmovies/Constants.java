package isyed.com.popularmovies;

/**
 * Created by irfansyed on 11/18/15.
 */
public class Constants {

    public static String theMovieDB_discoverMovieURL = "http://api.themoviedb.org/3/discover/movie?";

    public static String theMovieDB_Poster_Path = "http://image.tmdb.org/t/p/w185";

    public static String theMovieDB_Poster_Details_Path = "http://image.tmdb.org/t/p/w500";

    public static String thumbnailSize ;

    //Enter the your registered the movie db api key here
    public static final String theMovieDB_api_key = "";

    public static final String QUERY_PARAM = "sort_by=";

    public static final String SORT_ORDER = ".desc";

    public static final String APP_PARAM = "api_key";

    // These are the names of the JSON objects that need to be extracted.
    public static final String TMDB_RESULTS = "results";
    public static final String TMDB_RELEASEDATE = "release_date";
    public static final String TMDB_TITLE = "title";
    public static final String TMDB_OVERVIEW = "overview";
    public static final String TMDB_POSTERPATH = "poster_path";
    public static final String TMDB_VOTE_AVERAGE = "vote_average";
    public static final String TMDB_BACKDROP = "backdrop_path";

    public static final String PARCEL_NAME = "movieName";

    public static String RELEASED_DATE_NAME = "<b>" + "Release Date: " + "</b>";
    public static String VOTE_AVERAGE_NAME = "<b>" + "Vote Average: " + "</b>";
    public static String SYNOPSIS_NAME = "<b>" + "Plot Synopsis: " + "</b>";

}

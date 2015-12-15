package isyed.com.popularmovies;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieListViewAdapter.Callbacks{

    //ListView mMoviesListView;
    GridView   mMoviesGridView;
    ArrayList<MovieListItem> movieListItems;
    MovieListViewAdapter movieAdapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mMoviesGridView = (GridView)findViewById(R.id.movie_grid_view);

        movieListItems = new ArrayList<MovieListItem>();

        movieAdapter = new MovieListViewAdapter(this, movieListItems);

        movieAdapter.setCallbacks(this);

        mMoviesGridView.setAdapter(movieAdapter);

        mMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                intent.putExtra(Constants.PARCEL_NAME, movieAdapter.getItem(i));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart()
    {
        updateUI();
        super.onStart();
    }

    private void updateUI()
    {
        FetchMovieListTask fetchMovieTask = new FetchMovieListTask();
        //fetchWeatherTask.execute("94043,us");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String filter = Constants.theMovieDB_discoverMovieURL + Constants.QUERY_PARAM + preferences.getString(getString(R.string.pref_movie_filter_key), getString(R.string.pref_movie_default_filter_category)) + Constants.SORT_ORDER;
        fetchMovieTask.execute(filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingIntent);
            //getResources().getStringArray(R.array.pref_example_list_values);

            return true;
        }
        /*if(id == R.id.action_map)
        {
            openPreferredLocationInMap();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemPressed() {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        startActivity(intent);
    }

    private class FetchMovieListTask extends AsyncTask<String, Void, String[]>{

        private final String LOG_TAG = FetchMovieListTask.class.getName();

        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String[] movieList = new String[]{};

            try {

                Uri uri = Uri.parse(params[0]).buildUpon()
                        .appendQueryParameter(Constants.APP_PARAM, Constants.theMovieDB_api_key)
                        .build();

                Log.v(LOG_TAG, "uri = " + uri.toString());


                URL url = new URL(uri.toString());

                // Create the request to thMovieDB api, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                String movieJsonStr;
                if (buffer.length() == 0) {
                    movieJsonStr = null;
                }
                movieJsonStr = buffer.toString();

                movieList = getMovieDataFromJson(movieJsonStr);

                return movieList;

            }
            catch(JSONException e)
            {
                Log.e(LOG_TAG, "" + e.getMessage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected  void onPostExecute(String[] results)
        {
            super.onPostExecute(results);
            movieAdapter.clear();

            for(String movie : results)
            {
                String[] movieData = movie.split("@");

                MovieListItem movieItem = new MovieListItem();

                movieItem.setTitle(movieData[0]);
                movieItem.setRelease_date(movieData[1]);
                movieItem.setPoster_path(movieData[2]);
                movieItem.setVote_average(movieData[3]);
                movieItem.setPlot_synopsis(movieData[4]);
                movieItem.setMovie_backdrop(movieData[5]);
                if(movieListItems.contains(movieItem) == false) {
                    movieListItems.add(movieItem);
                }
            }
            movieAdapter.setAdapterItems(movieListItems);
        }

        private String[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            JSONObject forecastJson = new JSONObject(movieJsonStr);
            JSONArray moviesArray = forecastJson.getJSONArray(Constants.TMDB_RESULTS);

            String[] resultStrs = new String[moviesArray.length()];
            for(int i = 0; i < moviesArray.length(); i++) {

                //Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
                String title;
                String release_date;
                String poster_path;
                String vote_average;
                String plot_synopsis;
                String backdrop_path;

                // Get the JSON object representing the day
                JSONObject movie = moviesArray.getJSONObject(i);

                title = movie.getString(Constants.TMDB_TITLE);
                release_date = movie.getString(Constants.TMDB_RELEASEDATE);
                poster_path = movie.getString(Constants.TMDB_POSTERPATH);
                vote_average = movie.getString(Constants.TMDB_VOTE_AVERAGE);
                plot_synopsis = movie.getString(Constants.TMDB_OVERVIEW);
                backdrop_path = movie.getString(Constants.TMDB_BACKDROP);

                resultStrs[i] = title + "@" + release_date + "@" + poster_path + "@" + vote_average + "@" + plot_synopsis + "@" + backdrop_path;
                Log.e("TEST", resultStrs[i]);

            }

            return resultStrs;

        }



    }
}

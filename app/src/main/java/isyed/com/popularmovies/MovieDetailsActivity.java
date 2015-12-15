package isyed.com.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieListItem mMovieListItem;
    private ImageView     mPoster;
    private TextView      mTitleTextView;
    private TextView      mReleaseDateTextView;
    private TextView      mVotingTextView;
    private TextView      mMovieSynopsisTextView;
    private RatingBar     mRatingBar;
    private LinearLayout  mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);

        mPoster = (ImageView)findViewById(R.id.poster_imageView);
        mTitleTextView = (TextView)findViewById(R.id.title_textView);
        mReleaseDateTextView = (TextView)findViewById(R.id.releasedate_textView);
        mVotingTextView = (TextView)findViewById(R.id.voting_textView);
        mMovieSynopsisTextView = (TextView)findViewById(R.id.synopsis_testView);
        mRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        mlinearLayout = (LinearLayout)findViewById(R.id.movieLinearLayout);

        Intent intent = getIntent();

        mMovieListItem = (MovieListItem)intent.getParcelableExtra(Constants.PARCEL_NAME);

        renderMovieDetailsOnScreen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void renderMovieDetailsOnScreen()
    {
        Picasso.with(getApplicationContext()).load(Constants.theMovieDB_Poster_Details_Path+mMovieListItem.getPoster_path()).into(mPoster);
        mTitleTextView.setText(mMovieListItem.getTitle());
        mReleaseDateTextView.setText(Html.fromHtml(Constants.RELEASED_DATE_NAME));// +
        mReleaseDateTextView.append(mMovieListItem.getRelease_date());
        mVotingTextView.setText(Html.fromHtml(Constants.VOTE_AVERAGE_NAME));// +
        mVotingTextView.append(mMovieListItem.getVote_average());
        mMovieSynopsisTextView.setText(Html.fromHtml(Constants.SYNOPSIS_NAME));// +
        mMovieSynopsisTextView.append(mMovieListItem.getPlot_synopsis());
        mRatingBar.setIsIndicator(true);
        mRatingBar.setRating(Float.parseFloat(mMovieListItem.getVote_average()));
        setTitle(mMovieListItem.getTitle());
    }


    private class MoviePosterHelper extends AsyncTask<String, Void, Void>{

        private void renderMovieDetailsOnScreen()
        {
            Picasso.with(getApplicationContext()).load(Constants.theMovieDB_Poster_Path+mMovieListItem.getPoster_path()).into(mPoster);

        }


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //Initialize the UI components here.
            mPoster = (ImageView)findViewById(R.id.poster_imageView);
            mTitleTextView = (TextView)findViewById(R.id.title_textView);
            mReleaseDateTextView = (TextView)findViewById(R.id.releasedate_textView);
            mMovieSynopsisTextView = (TextView)findViewById(R.id.synopsis_testView);
        }

        @Override
        protected Void doInBackground(String... params) {
            renderMovieDetailsOnScreen();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
        }
    }
}

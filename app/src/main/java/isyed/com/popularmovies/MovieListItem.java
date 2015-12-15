package isyed.com.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by irfansyed on 11/18/15.
 */
public class MovieListItem implements Parcelable {

    private MovieListItem.Callbacks mCallbacks;
    private String mTitle;
    private String mRelease_date;
    private String mPoster_path;
    private String mVote_average;
    private String mPlot_synopsis;
    private String mMovie_backdrop;

    public MovieListItem(){}

    public MovieListItem(Parcel parcel)
    {
        mTitle = parcel.readString();
        mRelease_date = parcel.readString();
        mPoster_path = parcel.readString();
        mVote_average = parcel.readString();
        mPlot_synopsis = parcel.readString();
        mMovie_backdrop = parcel.readString();
    }

    public void setTitle(String title)
    {
        this.mTitle = title;
    }

    public void setRelease_date(String release_date)
    {
        this.mRelease_date = release_date;
    }

    public void setPoster_path(String poster_path)
    {
        this.mPoster_path = poster_path;
    }

    public void setVote_average(String vote_average)
    {
        this.mVote_average = vote_average;
    }

    public void setPlot_synopsis(String plot_synopsis)
    {
        this.mPlot_synopsis = plot_synopsis;
    }

    public void setMovie_backdrop(String movie_backdrop) { this.mMovie_backdrop = movie_backdrop;}

    public String getTitle()
    {
        return this.mTitle;
    }

    public String getRelease_date()
    {
        return this.mRelease_date;
    }

    public String getPoster_path()
    {
        return this.mPoster_path;
    }

    public String getVote_average()
    {
        return this.mVote_average;
    }

    public String getPlot_synopsis()
    {
        return this.mPlot_synopsis;
    }

    public String getMovie_backdrop() { return this.mMovie_backdrop;}

    public void setCallbacks(MovieListItem.Callbacks callbacks)
    {
        this.mCallbacks = callbacks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieListItem> CREATOR = new Creator<MovieListItem>() {

        public MovieListItem createFromParcel(Parcel source) {
            MovieListItem mMovieListItem = new MovieListItem();
            mMovieListItem.setTitle(source.readString());
            mMovieListItem.setRelease_date(source.readString());
            mMovieListItem.setPoster_path(source.readString());
            mMovieListItem.setVote_average(source.readString());
            mMovieListItem.setPlot_synopsis(source.readString());
            mMovieListItem.setMovie_backdrop(source.readString());

            return mMovieListItem;
        }
        public MovieListItem[] newArray(int size) {
            return new MovieListItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mRelease_date);
        parcel.writeString(mPoster_path);
        parcel.writeString(mVote_average);
        parcel.writeString(mPlot_synopsis);
        parcel.writeString(mMovie_backdrop);
    }

    public interface Callbacks{
        public void onMovieItemPressed();
    }
}

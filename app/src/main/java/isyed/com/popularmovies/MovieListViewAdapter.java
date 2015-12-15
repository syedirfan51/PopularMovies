package isyed.com.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by irfansyed on 11/18/15.
 */
public class MovieListViewAdapter extends ArrayAdapter<MovieListItem> {

    private ArrayList<MovieListItem> mListViewItems;

    private Context mContext;

    private MovieListViewAdapter.Callbacks mCallbacks;

    public MovieListViewAdapter(Context context, ArrayList<MovieListItem> listviewItems)
    {
        super(context, R.layout.movie_list_item, listviewItems);
        this.mContext = context;
    }

    public void setAdapterItems(ArrayList<MovieListItem> listViewItems)
    {
        this.mListViewItems = listViewItems;
    }

    @Override
    public View getView(final int position, View view, ViewGroup root)
    {
        View localView;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            localView = layoutInflater.inflate(R.layout.movie_list_item,root, false);
        }
        else
        {
            localView = (View)view;
        }
        MovieListItem item = mListViewItems.get(position);

        ImageView imageView = (ImageView) localView.findViewById(R.id.movie_image_view);

        Picasso.with(mContext).load(Constants.theMovieDB_Poster_Path + item.getPoster_path()).into(imageView);

        return localView;

    }

    public void setCallbacks(MovieListViewAdapter.Callbacks callbacks)
    {
        mCallbacks = callbacks;
    }

    //Originally comtemplated using callbacks instead on the onClickListener, leaving this here for future use.
    public interface Callbacks{
        public void onItemPressed();
    }

}

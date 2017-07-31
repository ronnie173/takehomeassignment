package xyz.appsian.googlebooksparser.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import xyz.appsian.googlebooksparser.R;
import xyz.appsian.googlebooksparser.activities.BooksInfoActivity;
import xyz.appsian.googlebooksparser.api.Item;


/**
 * Created by jeromeraymond on 7/29/17.
 */
public class GoogleBooksApiAdapter extends RecyclerView.Adapter<GoogleBooksApiAdapter.GoogleApiViewHolder>{
    /**
     * The Items.
     */
    static List<Item> items = Collections.emptyList();


    /**
     * The type Google api view holder.
     */
    public static class GoogleApiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The Book image view.
         */
        ImageView bookImageView;
        /**
         * The Title.
         */
        TextView title;
        /**
         * The Sub title.
         */
        TextView subTitle;
        /**
         * The Ratings.
         */
        TextView ratings;

        /**
         * Instantiates a new Google api view holder.
         *
         * @param itemView the item view
         */
        public GoogleApiViewHolder(final View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.bookImageView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);
            ratings = itemView.findViewById(R.id.ratings);
            itemView.setOnClickListener(this);

        }

        /**
         * When the user clicks on an item in the recuclerview this code handles
         * sending the correct data to the calling activity
         * @param view
         */
        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            int position = getAdapterPosition();
            if (position!= RecyclerView.NO_POSITION){
               Intent intent = new Intent(view.getContext(), BooksInfoActivity.class);
                List<Item> items = GoogleBooksApiAdapter.items;
                bundle.putString("title",items.get(position).getVolumeInfo().getTitle());
                bundle.putString("subTitle",items.get(position).getVolumeInfo().getSubtitle());
                bundle.putString("description",items.get(position).getVolumeInfo().getDescription());
                bundle.putString("infoLink",items.get(position).getVolumeInfo().getInfoLink());
                bundle.putString("publisher",items.get(position).getVolumeInfo().getPublisher());
                bundle.putString("language",items.get(position).getVolumeInfo().getLanguage());
                bundle.putString("imageLink",items.get(position).getVolumeInfo().getImageLinks().getThumbnail());
                if (items.get(position).getVolumeInfo().getCategories() == null){
                    bundle.putString("category","Undetermined");
                }else{
                    bundle.putString("category",items.get(position).getVolumeInfo().getCategories().get(0));
                }
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

            }
        }
    }

    /**
     * Instantiates a new Google books api adapter.
     *
     * @param items the items
     */
    public GoogleBooksApiAdapter(List<Item> items) {
        this.items = items;

    }

    /**
     * inflates the layout to be used for the items listed in this adapter
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public GoogleApiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_books_fancy, parent, false);
        return new GoogleApiViewHolder(view);
    }

    /**
     * Binds the widgets to the view holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final GoogleApiViewHolder holder, int position) {
        Log.d("", "onBindViewHolder: " + position);
        holder.title.setText(items.get(position).getVolumeInfo().getTitle());
        holder.subTitle.setText(items.get(position).getVolumeInfo().getDescription());
        if (items.get(position).getVolumeInfo().getAverageRating()!= null){
            holder.ratings.setText(String.valueOf("Average Ratings " + items.get(position).getVolumeInfo().getAverageRating()));
        }else{
            holder.ratings.setText(String.valueOf("Average Ratings " + 3.0));
        }
        if (items.get(position).getVolumeInfo().getImageLinks() != null){
            Picasso.with(holder.itemView.getContext()).load(items.get(position).getVolumeInfo().getImageLinks().getThumbnail()).into(holder.bookImageView);

        }


    }

    /**
     * Keeps track of items count
     * @return
     */
    @Override
    public int getItemCount() {
        Log.d("", "getItemCount: " + items.size());
        return items.size();
    }


}

package com.example.trainticketapi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TrainItemAdapter extends RecyclerView.Adapter<TrainItemAdapter.ViewHolder> implements Filterable {

    private ArrayList<TrainItem> mTrainItemsData;
    private ArrayList<TrainItem> mTrainItemsDataAll;
    private Context mContext;
    private int lastPosition = -1;

    TrainItemAdapter(Context context, ArrayList<TrainItem> itemsData){
        this.mTrainItemsData =itemsData;
        this.mTrainItemsDataAll =itemsData;
        this.mContext= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TrainItem currentItem = mTrainItemsData.get(position);
            
            holder.bindTo(currentItem);

            if(holder.getAdapterPosition()> lastPosition){
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
                holder.itemView.startAnimation(animation);
                lastPosition= holder.getAdapterPosition();
            }
        if(holder.getAdapterPosition()< lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_col);
            holder.itemView.startAnimation(animation);
            lastPosition= holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mTrainItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return trainFilter;
    }

    private Filter trainFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<TrainItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                    results.count = mTrainItemsDataAll.size();
                    results.values = mTrainItemsDataAll;
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(TrainItem item : mTrainItemsDataAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }

                }

                results.count = filteredList.size();
                results.values = filteredList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mTrainItemsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();


        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTrainNameText;
        private TextView mRouteText;
        private TextView mDateText;
        private TextView mPriceText;
        private ImageView mTrainImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTrainNameText = itemView.findViewById(R.id.trainTitle);
            mRouteText = itemView.findViewById(R.id.routeTitle);
            mDateText = itemView.findViewById(R.id.dateTitle);
            mPriceText = itemView.findViewById(R.id.priceTitle);
            mTrainImage = itemView.findViewById(R.id.trainImage);

            itemView.findViewById(R.id.trainTitle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Activity", "Book button clicked!");
                }
            });


        }

        public void bindTo(TrainItem currentItem) {
            mTrainNameText.setText(currentItem.getName());
            mRouteText.setText(currentItem.getRoute());
            mDateText.setText(currentItem.getDate());
            mPriceText.setText(currentItem.getPrice());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mTrainImage);

        }
    }
}



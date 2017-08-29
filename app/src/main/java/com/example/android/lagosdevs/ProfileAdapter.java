package com.example.android.lagosdevs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ogunbowale on 8/20/2017.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.DataViewHolder> {

    private static final String TAG = ProfileAdapter.class.getSimpleName();
    public static final String KEY_NAME = "name";
    private List<Profiles> profileLists;
    private Context context;
    final private ListItemClickListner mOnClickListner;

    public interface ListItemClickListner{
        void onListItemClick(int clickedItemIndex);
    }


    public ProfileAdapter(List<Profiles> profileLists, ListItemClickListner listner) {
        this.profileLists = profileLists;
        mOnClickListner = listner;
    }


    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.profile_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        DataViewHolder dataViewHolder = new DataViewHolder(view);

        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {

        Log.d(TAG, "#" + position);

        final Profiles profileList = profileLists.get(position);
        holder.login.setText(profileList.getUserLogin());
        Picasso.with(context)
                .load(profileList.getUserImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.avatar_url);



    }

    @Override
    public int getItemCount() {
        return profileLists.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView login;
        CircularImageView avatar_url;

        public DataViewHolder(View v){
            super(v);

            login = (TextView) v.findViewById(R.id.usr_id);
            avatar_url = (CircularImageView) v.findViewById(R.id.imageView);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListner.onListItemClick(clickedPosition);

            Profiles profiles = profileLists.get(clickedPosition);
            Intent i = new Intent(context, DetailProfile.class);
            i.putExtra(appConstant.API_USER_NAME, profiles.getUserLogin());
            i.putExtra(appConstant.API_AVATAR_URL, profiles.getUserImage());
            i.putExtra(appConstant.API_PROFILE_URL, profiles.getUserUrl());
            context.startActivity(i);





        }
    }
}

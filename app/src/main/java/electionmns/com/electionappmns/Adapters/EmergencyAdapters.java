package electionmns.com.electionappmns.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import electionmns.com.electionappmns.Fragments.DailogFragmentImage;
import electionmns.com.electionappmns.Models.DailyUpdatesModel;
import electionmns.com.electionappmns.Models.EmergencyContactModel;
import electionmns.com.electionappmns.R;


public class EmergencyAdapters extends RecyclerView
        .Adapter<EmergencyAdapters
        .DataObjectHolder> {

    private Typeface typefaceFontAws;
    private ArrayList<EmergencyContactModel> mDataset;
    private static MyClickListener myClickListener;
    Context con;
    private ProgressDialog progDailog;
    Activity activity2;
    private Typeface typefaceFA;


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {


        TextView cattv;
        TextView titletv;
        TextView contv;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public DataObjectHolder(View itemView) {
            super(itemView);

            cattv = (TextView) itemView.findViewById(R.id.catrec);
            titletv = (TextView) itemView.findViewById(R.id.titrec);
            contv = (TextView) itemView.findViewById(R.id.conrec);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public EmergencyAdapters(ArrayList<EmergencyContactModel> myDataset, Context cnt) {
        con = cnt;
        mDataset = myDataset;

        typefaceFA = Typeface.createFromAsset(cnt.getAssets(), "fonts/FontAwesome.otf");


    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emergency_rec, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.titletv.setText(mDataset.get(position).title);
        holder.cattv.setText(mDataset.get(position).cat);
        holder.contv.setText(mDataset.get(position).con);



    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }




}
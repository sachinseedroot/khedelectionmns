package electionmns.com.electionappmns.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import electionmns.com.electionappmns.Activities.PartyEventGridActivity;
import electionmns.com.electionappmns.Fragments.DailogFragmentImage;
import electionmns.com.electionappmns.Fragments.DailogFragmentImagePart;
import electionmns.com.electionappmns.Models.PartyModel;
import electionmns.com.electionappmns.R;


public class PartyEventGridAdapter extends RecyclerView
        .Adapter<PartyEventGridAdapter
        .DataObjectHolder> {

    private final ArrayList<String> mdatasets;
    private final ArrayList<String> mDataset;
    private final Activity activity;
    private Typeface typefaceFontAws;
    private static MyClickListener myClickListener;
    Context con;
    private ProgressDialog progDailog;
    Activity activity2;
    private Typeface typefaceFA;

    public PartyEventGridAdapter(Context mcontext, ArrayList<String> urls, ArrayList<String> urlL, PartyEventGridActivity partyEventGridActivity) {
        con = mcontext;
        mdatasets = urls;
        mDataset = urlL;
        activity = partyEventGridActivity;

        typefaceFA = Typeface.createFromAsset(con.getAssets(), "fonts/FontAwesome.otf");

    }


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {


        private final ProgressBar loader;
        ImageView rprt;



        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public DataObjectHolder(View itemView) {
            super(itemView);

            rprt = (ImageView) itemView.findViewById(R.id.ig);
            loader = (ProgressBar) itemView.findViewById(R.id.imgloadergrid);
//            rpra = (TextView) itemView.findViewById(R.id.rpra);
//            contv = (TextView) itemView.findViewById(R.id.conrec);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

//    public PartyEventGridAdapter(ArrayList<PartyModel> myDataset, Context cnt) {
//        con = cnt;
//        mDataset = myDataset;
//
//        typefaceFA = Typeface.createFromAsset(cnt.getAssets(), "fonts/FontAwesome.otf");
//
//
//    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imgview, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.loader.setVisibility(View.VISIBLE);
        Picasso.with(con).load(mdatasets.get(position)).into(holder.rprt, new Callback() {
            @Override
            public void onSuccess() {
                holder.loader.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        holder.rprt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = activity.getFragmentManager();
                DailogFragmentImagePart dailogFragmentImagePart = new DailogFragmentImagePart(mDataset.get(position));
                dailogFragmentImagePart.show(fm, "Image Fragment");

            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }




}
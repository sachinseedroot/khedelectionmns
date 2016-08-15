package electionmns.com.electionappmns.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import electionmns.com.electionappmns.Activities.PartyEventGridActivity;
import electionmns.com.electionappmns.Models.EmergencyContactModel;
import electionmns.com.electionappmns.Models.PartyModel;
import electionmns.com.electionappmns.Models.PartyWorksModel;
import electionmns.com.electionappmns.R;


public class PartyEventAdapter extends RecyclerView
        .Adapter<PartyEventAdapter
        .DataObjectHolder> {

    private Typeface typefaceFontAws;
    private ArrayList<PartyModel> mDataset;
    private static MyClickListener myClickListener;
    Context con;
    private ProgressDialog progDailog;
    Activity activity2;
    private Typeface typefaceFA;


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {


        TextView rprt;
        TextView rpra;
        TextView details;
        TextView location;
        RelativeLayout rprptparent;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public DataObjectHolder(View itemView) {
            super(itemView);

            rprt = (TextView) itemView.findViewById(R.id.rprt);
            rpra = (TextView) itemView.findViewById(R.id.rpra);
            details = (TextView)itemView.findViewById(R.id.detailpt);
            location  = (TextView)itemView.findViewById(R.id.loc);
            rprptparent = (RelativeLayout)itemView.findViewById(R.id.rprptparent);
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

    public PartyEventAdapter(ArrayList<PartyModel> myDataset, Context cnt, Activity activity) {
        con = cnt;
        mDataset = myDataset;
        activity2 = activity;
        typefaceFA = Typeface.createFromAsset(cnt.getAssets(), "fonts/FontAwesome.otf");


    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.rprt.setText(mDataset.get(position).title);
        holder.rpra.setTypeface(typefaceFA);
        holder.details.setText(mDataset.get(position).date);
        holder.location.setText(mDataset.get(position).location);
        holder.rprptparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity2, PartyEventGridActivity.class);
                intent.putExtra("title",mDataset.get(position).title);
                intent.putExtra("urls",mDataset.get(position).imagesarray.toString());
                activity2.startActivity(intent);
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
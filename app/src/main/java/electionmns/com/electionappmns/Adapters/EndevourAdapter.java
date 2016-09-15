package electionmns.com.electionappmns.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import electionmns.com.electionappmns.Models.EmergencyContactModel;
import electionmns.com.electionappmns.Models.PartyWorksModel;
import electionmns.com.electionappmns.R;


public class EndevourAdapter extends RecyclerView
        .Adapter<EndevourAdapter
        .DataObjectHolder> {

    private Typeface typefaceFontAws;
    private ArrayList<PartyWorksModel> mDataset;
    private static MyClickListener myClickListener;
    Context con;



    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {


        TextView title;
        TextView amount;
        TextView nidhi;


        public DataObjectHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.rec_titl);
            amount = (TextView) itemView.findViewById(R.id.rec_rakk2);
            nidhi = (TextView) itemView.findViewById(R.id.rec_amt2);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public EndevourAdapter(ArrayList<PartyWorksModel> myDataset, Context cnt) {
        con = cnt;
        mDataset = myDataset;
   }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partyworkds_rec, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.title.setText(mDataset.get(position).title);
        holder.amount.setText(mDataset.get(position).amount);
        holder.nidhi.setText(mDataset.get(position).nidhi);



    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }




}
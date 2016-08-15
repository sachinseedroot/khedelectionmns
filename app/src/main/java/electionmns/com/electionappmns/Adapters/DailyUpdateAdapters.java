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
import electionmns.com.electionappmns.R;


public class DailyUpdateAdapters extends RecyclerView
        .Adapter<DailyUpdateAdapters
        .DataObjectHolder> {

    private Typeface typefaceFontAws;
    private ArrayList<DailyUpdatesModel> mDataset;
    private static MyClickListener myClickListener;
    Context con;
    private ProgressDialog progDailog;
    Activity activity2;
    private Typeface typefaceFA;


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        ImageView img1;
        ImageView img2;
        TextView title;
        TextView desc;
        TextView date;
        TextView location;
        TextView share;
        ProgressBar imgloader1;
        ProgressBar imgloader2;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public DataObjectHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.rec_date);
            title = (TextView) itemView.findViewById(R.id.rec_dy_txt_tile);
            desc = (TextView) itemView.findViewById(R.id.rec_dy_txt_desc);
            location = (TextView) itemView.findViewById(R.id.rec_location);
            img1 = (ImageView) itemView.findViewById(R.id.rec_dy_img1);
            img2 = (ImageView) itemView.findViewById(R.id.rec_dy_img2);
            share = (TextView) itemView.findViewById(R.id.rec_share);

            imgloader1 = (ProgressBar) itemView.findViewById(R.id.imgloader1);
            imgloader2 = (ProgressBar) itemView.findViewById(R.id.imgloader2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public DailyUpdateAdapters(ArrayList<DailyUpdatesModel> myDataset, Context cnt, Activity activity) {
        con = cnt;
        mDataset = myDataset;
        activity2 = activity;
        typefaceFA = Typeface.createFromAsset(cnt.getAssets(), "fonts/FontAwesome.otf");


    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dailyupdate_rec_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.title.setText(mDataset.get(position).title);
        holder.desc.setText(mDataset.get(position).desc);
        holder.location.setText(mDataset.get(position).location);
        holder.share.setText("Share " + con.getResources().getString(R.string.share));

        holder.date.setText(mDataset.get(position).date);
        holder.imgloader1.setVisibility(View.VISIBLE);
        holder.imgloader1.setVisibility(View.VISIBLE);
        if (isNetworkConnected() == true) {
            if (!mDataset.get(position).imgUrl1.equals("")) {
                Picasso.with(con).load(mDataset.get(position).imgUrl1).into(holder.img1, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imgloader1.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holder.imgloader1.setVisibility(View.GONE);
                    }
                });
            } else {
                holder.img1.setVisibility(View.GONE);
            }
        } else {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/MNS/DailyUpdates/"+mDataset.get(position).id+"1.jpg";
            File file = new File(path);
            if(file.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.img1.setImageBitmap(myBitmap);
            }
        }
        if (isNetworkConnected() == true) {
            if (!mDataset.get(position).imgUrl2.equals("")) {
                Picasso.with(con).load(mDataset.get(position).imgUrl2).into(holder.img2, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imgloader2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.imgloader2.setVisibility(View.GONE);
                    }
                });
            } else {
                holder.img2.setVisibility(View.GONE);
            }
        } else {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/MNS/DailyUpdates/"+mDataset.get(position).id+"2.jpg";
            File file = new File(path);
            if(file.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.img2.setImageBitmap(myBitmap);
            }
        }

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = mDataset.get(position).title + "\n" + mDataset.get(position).desc + "\n" + mDataset.get(position).date + ", " + mDataset.get(position).location;
                System.out.println("---------shareingintetn----" + shareBody);
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity2.startActivity(Intent.createChooser(sharingIntent, con.getResources().getString(R.string.share_using)));
            }
        });
        holder.desc.setTypeface(typefaceFA);
        holder.date.setTypeface(typefaceFA);
        holder.share.setTypeface(typefaceFA);
        holder.location.setTypeface(typefaceFA);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.img1.getDrawable() != null) {
                    Bitmap bm = ((BitmapDrawable) holder.img1.getDrawable()).getBitmap();
                    saveimage saveimages = new saveimage(bm, mDataset.get(position).id, "1");
                    saveimages.execute();
                }
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.img2.getDrawable() != null) {
                    Bitmap bm = ((BitmapDrawable) holder.img2.getDrawable()).getBitmap();
                    saveimage saveimages = new saveimage(bm, mDataset.get(position).id, "2");
                    saveimages.execute();
                }
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


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public class saveimage extends AsyncTask<Void, Void, Void> {
        Bitmap bmp = null;
        boolean issuccess = false;
        String ids = "";
        String k = "";

        saveimage(Bitmap bitmap, String id, String i) {
            bmp = bitmap;
            ids = id;
            k = i;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (bmp != null && !TextUtils.isEmpty(ids) && !TextUtils.isEmpty(k)) {
                String name = ids + k;
                saveImageFile(bmp, name);
                issuccess = true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            stoploadprogress();
            if (issuccess == true) {
                FragmentManager fm = activity2.getFragmentManager();
                DailogFragmentImage dailogFragmentImage = new DailogFragmentImage(bmp);
                dailogFragmentImage.show(fm, "Image Fragment");
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadprogressbar();
        }
    }

    public String saveImageFile(Bitmap bitmap, String name) {
        FileOutputStream out = null;
        String filename = getFilename(name);
        File filesv = new File(filename);
        if (filesv.exists()) {
            System.out.println("------------alreadysaved-----");
        } else {
            try {
                out = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                System.out.println("------------imagesaved-----");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }

    private String getFilename(String name) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "MNS/DailyUpdates");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/"
                + name + ".jpg");
        return uriSting;
    }

    public void loadprogressbar() {
        if (progDailog == null) {
            progDailog = new ProgressDialog(activity2);
        }
        if (progDailog.isShowing() == false) {
            progDailog.setMessage("Loading, Please wait...");
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
    }

    public void stoploadprogress() {

        if (progDailog.isShowing() == true) {
            progDailog.dismiss();
        }

    }

}
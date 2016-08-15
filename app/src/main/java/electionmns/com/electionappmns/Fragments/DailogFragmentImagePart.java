package electionmns.com.electionappmns.Fragments;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.ZoomActivityImageView;

/**
 * Created by Neha on 8/4/2016.
 */
public class DailogFragmentImagePart extends DialogFragment {
    private View rootview;
    private String bmp;
    Button buttons;
    Bitmap bitmapmain;
    private ProgressBar getProgDailog;

    public DailogFragmentImagePart(String url) {
        bmp = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.imageviewscreenpr, null);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttons = (Button) rootview.findViewById(R.id.btnshare);
        final Button buttonc = (Button) rootview.findViewById(R.id.btnclose);
        getProgDailog = (ProgressBar)view.findViewById(R.id.imgloaderpr);
        getProgDailog.setVisibility(View.VISIBLE);
        final ImageView touch = (ImageView) rootview.findViewById(R.id.ind_imageviewf);
        Picasso.with(getActivity().getApplicationContext()).load(bmp).into(touch, new Callback() {
            @Override
            public void onSuccess() {
                buttons.setVisibility(View.VISIBLE);
                buttons.setText("Download");
                getProgDailog.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                buttons.setVisibility(View.GONE);
                getProgDailog.setVisibility(View.GONE);
            }
        });
//        touch.setImageBitmap(bitmap);

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttons.getText().toString().trim().equals("Share")) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("image/jpeg");
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    Uri uri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);


                    OutputStream outstream;
                    try {
                        outstream = getActivity().getApplicationContext().getContentResolver().openOutputStream(uri);
                        bitmapmain.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                        outstream.close();
                    } catch (Exception e) {
                        System.err.println(e.toString());
                    }

                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(Intent.createChooser(sharingIntent, getActivity().getApplicationContext().getResources().getString(R.string.share_using)));

                } else if (buttons.getText().toString().trim().equals("Download")) {
                    bitmapmain = ((BitmapDrawable) touch.getDrawable()).getBitmap();
                    saveimage saveimages = new saveimage(bitmapmain);
                    saveimages.execute();
                }

            }
        });


        buttonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public class saveimage extends AsyncTask<Void, Void, Void> {
        Bitmap bmp = null;
        boolean issuccess = false;



        saveimage(Bitmap bitmap) {
            bmp = bitmap;


        }

        @Override
        protected Void doInBackground(Void... params) {
            if (bmp != null ) {

                saveImageFile(bmp);
                issuccess = true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (issuccess == true) {

                buttons.setText("Share");
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    public String saveImageFile(Bitmap bitmap) {
        FileOutputStream out = null;
        String filename = getFilename();
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

    private String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "MNS/PartyEvents");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }




}

package electionmns.com.electionappmns.Fragments;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.OutputStream;

import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.ZoomActivityImageView;

/**
 * Created by Neha on 8/4/2016.
 */
public class DailogFragmentImage extends DialogFragment {
    private View rootview;
    private Bitmap bmp;

    public DailogFragmentImage(Bitmap bitmap) {
        bmp = bitmap;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.imageviewscreen,null);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ZoomActivityImageView touch = (ZoomActivityImageView)rootview.findViewById(R.id.ind_imageview);
//        touch.setImageBitmap(bitmap);
        if(bmp!=null){
            touch.setImageBitmap(bmp);
        }
        Button buttons = (Button)rootview.findViewById(R.id.btnshare);
        Button buttonc = (Button)rootview.findViewById(R.id.btnclose);

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/jpeg");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);


                OutputStream outstream;
                try {
                    outstream = getActivity().getApplicationContext().getContentResolver().openOutputStream(uri);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                    outstream.close();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }

                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(Intent.createChooser(sharingIntent, getActivity().getApplicationContext().getResources().getString(R.string.share_using)));
            }
        });


        buttonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

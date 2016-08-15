
package electionmns.com.electionappmns.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;


import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 6/22/2016.
 */
public class PartyWorksFragment extends Fragment implements View.OnClickListener {
    private LinearLayout panel1,panel2,panel3,panel4,panel5,panel6;
    private TextView text1,text2,text3,text4,text5,text6;
    private View view;
    private View openLayout;
    private Typeface fontawesome;
    private Fragment[] objFragment = {null};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.partyend,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fontawesome = TypeFaceHelper.getInstance(getActivity().getApplicationContext()).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME);
        panel1 = (LinearLayout) view.findViewById(R.id.panel1);
        panel2 = (LinearLayout) view.findViewById(R.id.panel2);
        panel3 = (LinearLayout) view.findViewById(R.id.panel3);
        panel4 = (LinearLayout) view.findViewById(R.id.panel4);
        panel5 = (LinearLayout) view.findViewById(R.id.panel5);
        panel6 = (LinearLayout) view.findViewById(R.id.panel6);
//        panel7 = (LinearLayout) view.findViewById(R.id.panel7);
//        panel8 = (LinearLayout) view.findViewById(R.id.panel8);
//        panel9 = (LinearLayout) view.findViewById(R.id.panel9);
//        panel10 = (LinearLayout) view.findViewById(R.id.panel10);
//        panel11 = (LinearLayout) view.findViewById(R.id.panel11);
//        panel12 = (LinearLayout) view.findViewById(R.id.panel12);
        //  panel4.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));

        text1 = (TextView) view.findViewById(R.id.text1);
        text2 = (TextView) view.findViewById(R.id.text2);
        text3 = (TextView) view.findViewById(R.id.text3);
        text4 = (TextView) view.findViewById(R.id.text4);
        text5 = (TextView) view.findViewById(R.id.text5);
        text6 = (TextView) view.findViewById(R.id.text6);
//        text7 = (TextView) view.findViewById(R.id.text7);
//        text8 = (TextView) view.findViewById(R.id.text8);
//        text9 = (TextView) view.findViewById(R.id.text9);
//        text10 = (TextView) view.findViewById(R.id.text10);
//        text11 = (TextView) view.findViewById(R.id.text11);
//        text12 = (TextView) view.findViewById(R.id.text12);



        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
        text6.setOnClickListener(this);
//        text7.setOnClickListener(this);
//        text8.setOnClickListener(this);
//        text9.setOnClickListener(this);
//        text10.setOnClickListener(this);
//        text11.setOnClickListener(this);
//        text12.setOnClickListener(this);

        TextView backarrow = (TextView)view.findViewById(R.id.backarrowpartyworksTV);
        backarrow.setTypeface(fontawesome);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
    }
    public class ScaleAnimToShow extends ScaleAnimation
    {

        private View mView;

        private LinearLayout.LayoutParams mLayoutParams;

        private int mMarginBottomFromY, mMarginBottomToY;

        private boolean mVanishAfter = false;

        public ScaleAnimToShow(float toX, float fromX, float toY, float fromY, int duration, View view,boolean vanishAfter)
        {
            super(fromX, toX, fromY, toY);
            openLayout = view;
            setDuration(duration);
            mView = view;
            mVanishAfter = vanishAfter;
            mLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            mView.setVisibility(View.VISIBLE);
            int height = mView.getHeight();
            //mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin + height;
            //mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) + height;

            mMarginBottomFromY = 0;
            mMarginBottomToY = height;

            Log.v("CZ",".................height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY  + " , mMarginBottomToY.." +mMarginBottomToY);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f)
            {
                int newMarginBottom = (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime) - mMarginBottomToY;
                mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
                mView.getParent().requestLayout();
                //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
            }
        }

    }
    @Override
    public void onClick(View v)
    {
        hideOthers(v);
    }
    private void hideThemAll()
    {
        if(openLayout == null) return;
        if(openLayout == panel1)
            panel1.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
        if(openLayout == panel2)
            panel2.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
        if(openLayout == panel3)
            panel3.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
        if(openLayout == panel4)
            panel4.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
        if(openLayout == panel5)
            panel5.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel5, true));
        if(openLayout == panel6)
            panel6.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel6, true));
//        if(openLayout == panel7)
//            panel7.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel7, true));
//        if(openLayout == panel8)
//            panel8.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel8, true));
//        if(openLayout == panel9)
//            panel9.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel9, true));
//        if(openLayout == panel10)
//            panel10.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel10, true));
//        if(openLayout == panel11)
//            panel11.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel11, true));
//        if(openLayout == panel12)
//            panel12.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel12, true));
    }
    private void hideOthers(View layoutView)
    {
        int v;
        if(layoutView.getId() == R.id.text1)
        {
            v = panel1.getVisibility();
            if(v != View.VISIBLE)
            {
                panel1.setVisibility(View.VISIBLE);
                Log.v("CZ","height..." + panel1.getHeight());
            }

//panel1.setVisibility(View.GONE);
//Log.v("CZ","again height..." + panel1.getHeight());
            hideThemAll();
            if(v != View.VISIBLE)
            {
                panel1.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
            }
        }
        else if(layoutView.getId() == R.id.text2)
        {
            v = panel2.getVisibility();
            hideThemAll();
            if(v != View.VISIBLE)
            {
                panel2.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
            }
        }
        else if(layoutView.getId() == R.id.text3)
        {
            v = panel3.getVisibility();
            hideThemAll();
            if(v != View.VISIBLE)
            {
                panel3.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
            }
        }
        else if(layoutView.getId() == R.id.text4)
        {
            v = panel4.getVisibility();
            hideThemAll();
            if(v != View.VISIBLE)
            {
                panel4.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
            }
        }
        else if(layoutView.getId() == R.id.text5)
        {
            v = panel5.getVisibility();
            hideThemAll();
            if(v != View.VISIBLE)
            {
                panel5.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel5, true));
            }
        }
        else if(layoutView.getId() == R.id.text6)
        {
            v = panel6.getVisibility();
            hideThemAll();
            if(v != View.VISIBLE)
            {
                panel6.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel6, true));
            }
        }
//        else if(layoutView.getId() == R.id.text7)
//        {
//            v = panel7.getVisibility();
//            hideThemAll();
//            if(v != View.VISIBLE)
//            {
//                panel7.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel7, true));
//            }
//        }
//        else if(layoutView.getId() == R.id.text8)
//        {
//            v = panel8.getVisibility();
//            hideThemAll();
//            if(v != View.VISIBLE)
//            {
//                panel8.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel8, true));
//            }
//        }
//        else if(layoutView.getId() == R.id.text9)
//        {
//            v = panel9.getVisibility();
//            hideThemAll();
//            if(v != View.VISIBLE)
//            {
//                panel9.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel9, true));
//            }
//        }
//        else if(layoutView.getId() == R.id.text10)
//        {
//            v = panel10.getVisibility();
//            hideThemAll();
//            if(v != View.VISIBLE)
//            {
//                panel10.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel10, true));
//            }
//        }
//        else if(layoutView.getId() == R.id.text11)
//        {
//            v = panel11.getVisibility();
//            hideThemAll();
//            if(v != View.VISIBLE)
//            {
//                panel11.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel11, true));
//            }
//        }
//        else if(layoutView.getId() == R.id.text12)
//        {
//            v = panel12.getVisibility();
//            hideThemAll();
//            if(v != View.VISIBLE)
//            {
//                panel12.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel12, true));
//            }
//        }
    }
    public class ScaleAnimToHide extends ScaleAnimation
    {

        private View mView;

        private LinearLayout.LayoutParams mLayoutParams;

        private int mMarginBottomFromY, mMarginBottomToY;

        private boolean mVanishAfter = false;

        public ScaleAnimToHide(float fromX, float toX, float fromY, float toY, int duration, View view,boolean vanishAfter)
        {
            super(fromX, toX, fromY, toY);
            setDuration(duration);
            openLayout = null;
            mView = view;
            mVanishAfter = vanishAfter;
            mLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            int height = mView.getHeight();
            mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin - height;
            mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) - height;

            Log.v("CZ","height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY  + " , mMarginBottomToY.." +mMarginBottomToY);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f)
            {
                int newMarginBottom = mMarginBottomFromY + (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime);
                mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
                mView.getParent().requestLayout();
                //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
            }
            else if (mVanishAfter)
            {
                mView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

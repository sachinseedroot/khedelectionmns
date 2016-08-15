package electionmns.com.electionappmns.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import electionmns.com.electionappmns.R;

/**
 * Created by Neha on 6/21/2016.
 */
public class RegistrationFragment extends Fragment {
    private View view;
    private Fragment[] objFragment = {null};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homescreen,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout partworks = (RelativeLayout)view.findViewById(R.id.partworks_rel);
        partworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new PartyWorksFragment();
                fm.beginTransaction().setCustomAnimations(R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
    }
}

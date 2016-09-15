package electionmns.com.electionappmns.Fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import electionmns.com.electionappmns.R;

/**
 * Created by Neha on 6/21/2016.
 */
public class HomeFragment extends Fragment {
    private View view;
    private Fragment[] objFragment = {null};
    private RelativeLayout rel_dailyupdates;
    private RelativeLayout rel_joinparty;
    private RelativeLayout emer_rel;
    private RelativeLayout rel_contact;
    private RelativeLayout problem_layout;
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
        rel_dailyupdates = (RelativeLayout) view.findViewById(R.id.rel_dailyupdates);
        rel_joinparty = (RelativeLayout) view.findViewById(R.id.rel_joinparty);
        emer_rel = (RelativeLayout)view.findViewById(R.id.emer_rel);
        rel_contact = (RelativeLayout)view.findViewById(R.id.rel_contact);
        problem_layout = (RelativeLayout)view.findViewById(R.id.problem_layout);
        partworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new PartyWorksFragment();
//                fm.beginTransaction().setCustomAnimations(R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        rel_dailyupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new DailyUpdatesFragment();
//                fm.beginTransaction().setCustomAnimations(R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
        rel_joinparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new JoinPartyFragment();
//                fm.beginTransaction().setCustomAnimations(R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        emer_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new EmergencyContactFragment();
                //fm.beginTransaction().setCustomAnimations(R.anim.slideinleft,R.anim.slideoutright,R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        rel_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new ContactFragment();
                //fm.beginTransaction().setCustomAnimations(R.anim.slideinleft,R.anim.slideoutright,R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        RelativeLayout evensgallry = (RelativeLayout) view.findViewById(R.id.evensgallry);
        evensgallry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new PartyEventsFragment();
                //fm.beginTransaction().setCustomAnimations(R.anim.slideinleft,R.anim.slideoutright,R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
        problem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new ProblemFragment();
                //fm.beginTransaction().setCustomAnimations(R.anim.slideinleft,R.anim.slideoutright,R.anim.slideinright,R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
    }
}

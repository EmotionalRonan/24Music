package info.emotionalronan.a24music.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.emotionalronan.a24music.R;
import info.emotionalronan.a24music.adapter.ViewPagerAdapter1;
import info.emotionalronan.a24music.executor.OnBackPressListener;

/**
 * Created by YG on 2017/5/28.
 */

public class CarouselFragment extends Fragment {
    /**
     * TabPagerIndicator
     *
     * Please refer to ViewPagerIndicator library
     */

    protected ViewPager pager;

    private ViewPagerAdapter1 adapter;


    public CarouselFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.include_viewpage, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.viewpager);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Note that we are passing childFragmentManager, not FragmentManager
        adapter = new ViewPagerAdapter1(getResources(), getChildFragmentManager());

        pager.setAdapter(adapter);
    }

    /**
     * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
     *
     * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
     */
    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(pager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }

}

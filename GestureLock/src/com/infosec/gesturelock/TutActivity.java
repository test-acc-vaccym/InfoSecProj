package com.infosec.gesturelock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

public class TutActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo. MUST MATCH THE
     * NUMBER OF PICTURES IN THE FOLLOWING ARRAY
     */
    private static final int NUM_PAGES = 5;

    /**
     * Array that holds the IDs of the drawables
     */
    private final int[] images = { R.drawable.gesturelocktutorialscreen,
            R.drawable.gesturelocktutorialscreen2,
            R.drawable.gesturelocktutorialscreen3,
            R.drawable.gesturelocktutorialscreen4,
            R.drawable.gesturelocktutorialscreen5 };

    /**
     * The pager widget, which handles animation and allows swiping horizontally
     * to access previous and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tut);

        // Instantiate a ViewPager and a PagerAdapter.
        this.mPager = (ViewPager) this.findViewById(R.id.pager);
        this.mPagerAdapter = new TutActivityPagerAdapter(
                this.getSupportFragmentManager());
        this.mPager.setAdapter(this.mPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavUtils.navigateUpFromSameTask(this);
        this.finish();
        return true;
    }

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
     * objects, in sequence.
     */
    private class TutActivityPagerAdapter extends FragmentStatePagerAdapter {
        public TutActivityPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TutActivityFrag.create(position,
                    TutActivity.this.images[position]);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

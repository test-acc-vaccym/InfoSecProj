package com.infosec.gesturelock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 * 
 * <p>
 * This class is used by the {@link CardFlipActivity} and
 * {@link ScreenSlideActivity} samples.
 * </p>
 */
public class StartTestActivity extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_IMAGE = "image";

    /**
     * The fragment's page number, which is set to the argument value for
     * {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * The fragment's page number, which is set to the argument value for
     * {@link #ARG_PAGE}.
     */
    private int mImageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the
     * given page number.
     */
    public static StartTestActivity create(int pageNumber, int imgNumber) {
        StartTestActivity fragment = new StartTestActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_IMAGE, imgNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public StartTestActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPageNumber = this.getArguments().getInt(ARG_PAGE);
        this.mImageNumber = this.getArguments().getInt(ARG_IMAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_tut, container, false);

        // Set the title view to show the page number.
        ((TextView) rootView.findViewById(R.id.tutImgHeader)).setText(this
                .getString(R.string.title_template_step, this.mPageNumber + 1));

        ((ImageView) rootView.findViewById(R.id.tutImage))
                .setImageResource(this.mImageNumber);

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return this.mPageNumber;
    }

    /**
     * Returns the image number represented by this fragment object.
     */
    public int getImageNumber() {
        return this.mImageNumber;
    }
}

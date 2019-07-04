package com.app.homeycam.CustomeViews.CustomBottomView;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.app.homeycam.R;

import java.util.ArrayList;
import java.util.List;


public class BottomNavigationBar implements View.OnClickListener {

    public static final int MENU_BAR_1 = 0;
    public static final int MENU_BAR_2 = 1;
    public static final int MENU_BAR_3 = 2;
    public static final int MENU_BAR_4 = 3;

    private List<NavigationPage> mNavigationPageList = new ArrayList<>();

    private Context mContext;
    private AppCompatActivity mActivity;
    private BottomNavigationMenuClickListener mListener;

    private LinearLayout mLLBar1, mLLBar2, mLLBar3;
    private View mViewBar1, mViewBar2, mViewBar3;
    private AppCompatImageView mImageViewBar1, mImageViewBar2, mImageViewBar3;
    private AppCompatTextView mTextViewBar1, mTextViewBar2, mTextViewBar3;

    public BottomNavigationBar(Context mContext, List<NavigationPage> pages, BottomNavigationMenuClickListener listener) {

        // initialize variables
        this.mContext = mContext;
        this.mActivity = (AppCompatActivity) mContext;
        this.mListener = listener;
        this.mNavigationPageList = pages;

        // getting reference to bar linear layout viewgroups
        this.mLLBar1 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar1);
        this.mLLBar2 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar2);
        this.mLLBar3 = (LinearLayout) mActivity.findViewById(R.id.linearLayoutBar3);

        // getting reference to bar upper highlight
        this.mViewBar1 = (View) mActivity.findViewById(R.id.viewBar1);
        this.mViewBar2 = (View) mActivity.findViewById(R.id.viewBar2);
        this.mViewBar3 = (View) mActivity.findViewById(R.id.viewBar3);

        // getting reference to bar icons
        this.mImageViewBar1 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar1);
        this.mImageViewBar2 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar2);
        this.mImageViewBar3 = (AppCompatImageView) mActivity.findViewById(R.id.imageViewBar3);

        // setting the icons
        this.mImageViewBar1.setImageDrawable(mNavigationPageList.get(0).getIcon());
        this.mImageViewBar2.setImageDrawable(mNavigationPageList.get(1).getIcon());
        this.mImageViewBar3.setImageDrawable(mNavigationPageList.get(2).getIcon());

        // getting reference to bar titles
        this.mTextViewBar1 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar1);
        this.mTextViewBar2 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar2);
        this.mTextViewBar3 = (AppCompatTextView) mActivity.findViewById(R.id.textViewBar3);

        // 2etting the titles
        this.mTextViewBar1.setText(mNavigationPageList.get(0).getTitle());
        this.mTextViewBar2.setText(mNavigationPageList.get(1).getTitle());
        this.mTextViewBar3.setText(mNavigationPageList.get(2).getTitle());

        // setting click listeners
        this.mLLBar1.setOnClickListener(this);
        this.mLLBar2.setOnClickListener(this);
        this.mLLBar3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // setting clicked bar as highlighted view
        setView(view);

        // triggering click listeners
        if (view.getId() == R.id.linearLayoutBar1) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_1);
            return;
        } else if (view.getId() == R.id.linearLayoutBar2) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_2);
            return;
        } else if (view.getId() == R.id.linearLayoutBar3) {
            mListener.onClickedOnBottomNavigationMenu(MENU_BAR_3);
            return;
        } else {
            return;
        }

    }

    /**
     * sets the clicked view as selected, resets other views
     *
     * @param view clicked view
     */
    private void setView(View view) {

        // seting all highlight bar as invisible
        this.mViewBar1.setVisibility(View.INVISIBLE);
        this.mViewBar2.setVisibility(View.INVISIBLE);
        this.mViewBar3.setVisibility(View.INVISIBLE);

        // resetting colors of all icons
        this.mImageViewBar1.setColorFilter(ContextCompat.getColor(mContext, R.color.colorNavAccentUnselected));
        this.mImageViewBar2.setColorFilter(ContextCompat.getColor(mContext, R.color.colorNavAccentUnselected));
        this.mImageViewBar3.setColorFilter(ContextCompat.getColor(mContext, R.color.colorNavAccentUnselected));

        // resetting colors of all titles
        this.mTextViewBar1.setTextColor(ContextCompat.getColor(mContext, R.color.colorNavAccentUnselected));
        this.mTextViewBar2.setTextColor(ContextCompat.getColor(mContext, R.color.colorNavAccentUnselected));
        this.mTextViewBar3.setTextColor(ContextCompat.getColor(mContext, R.color.colorNavAccentUnselected));

        // selectively colorizing the marked view
        if (view.getId() == R.id.linearLayoutBar1) {
            this.mViewBar1.setVisibility(View.VISIBLE);
            this.mImageViewBar1.setColorFilter(ContextCompat.getColor(mContext, R.color.button_color));
            this.mTextViewBar1.setTextColor(ContextCompat.getColor(mContext, R.color.button_color));
            return;
        } else if (view.getId() == R.id.linearLayoutBar2) {
            this.mViewBar2.setVisibility(View.VISIBLE);
            this.mImageViewBar2.setColorFilter(ContextCompat.getColor(mContext, R.color.button_color));
            this.mTextViewBar2.setTextColor(ContextCompat.getColor(mContext, R.color.button_color));
            return;
        } else if (view.getId() == R.id.linearLayoutBar3) {
            this.mViewBar3.setVisibility(View.VISIBLE);
            this.mImageViewBar3.setColorFilter(ContextCompat.getColor(mContext, R.color.button_color));
            this.mTextViewBar3.setTextColor(ContextCompat.getColor(mContext, R.color.button_color));
            return;
        } else {
            return;
        }

    }

    public interface BottomNavigationMenuClickListener {
        void onClickedOnBottomNavigationMenu(int menuType);
    }

}

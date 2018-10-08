package com.example.iamsh.flickrbrowser;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private static final String TAG = "RecyclerItemClickListen";
    OnRecyclerClickListener mListener;
    GestureDetectorCompat gestureDetector;
    public RecyclerItemClickListener(final OnRecyclerClickListener mListener, Context context, final RecyclerView recyclerView) {
        this.mListener = mListener;
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: ");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {
                    Log.d(TAG, "onLongPress: Calling listener.onItemLongClick");
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: Starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {

                    Log.d(TAG, "onSingleTapUp: Calling listener.onItemClick");
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }

                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: Starts : " + gestureDetector);
        if (gestureDetector != null) {
            boolean result = gestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: Returned : " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: returned False");
            return false;
        }
    }

    interface OnRecyclerClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}

package com.eowise.recyclerview.stickyheaders;

import android.util.Pair;
import android.view.View;

/**
 * Created by aurel on 08/11/14.
 */
public interface OnHeaderClickListener {
    void onHeaderClick(View header, long headerId, Pair<Float,Float> tapOffset);
}

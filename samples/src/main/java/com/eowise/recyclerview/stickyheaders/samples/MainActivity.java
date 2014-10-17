package com.eowise.recyclerview.stickyheaders.samples;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.eowise.recyclerview.stickyheaders.HeaderPosition;
import com.eowise.recyclerview.stickyheaders.InvalidateAnimationRunnable;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.eowise.recyclerview.stickyheaders.samples.adapters.BigramHeaderAdapter;
import com.eowise.recyclerview.stickyheaders.samples.adapters.InitialHeaderAdapter;
import com.eowise.recyclerview.stickyheaders.samples.adapters.PersonAdapter;
import com.eowise.recyclerview.stickyheaders.samples.data.PersonDataProvider;

/**
 * Created by aurel on 22/09/14.
 */
public class MainActivity extends Activity {

    private Toolbar toolbar;
    private RecyclerView list;
    private StickyHeadersItemDecoration top;
    private StickyHeadersItemDecoration overlay;
    private PersonDataProvider personDataProvider;
    private PersonAdapter personAdapter;
    private Spinner samplesSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        list = (RecyclerView)findViewById(R.id.list);
        samplesSpinner = (Spinner)findViewById(R.id.samples_spinner);

        list.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        personDataProvider = new PersonDataProvider();
        personAdapter = new PersonAdapter(personDataProvider);
        personAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                ViewCompat.postOnAnimationDelayed(list, new InvalidateAnimationRunnable(list), 50);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                ViewCompat.postOnAnimationDelayed(list, new InvalidateAnimationRunnable(list), 50);
            }
        });


        top = new StickyHeadersItemDecoration(new BigramHeaderAdapter(personDataProvider.getItems()), list, HeaderPosition.TOP);
        overlay = new StickyHeadersItemDecoration(new InitialHeaderAdapter(personDataProvider.getItems()), list, HeaderPosition.OVERLAY);

        top.registerAdapterDataObserver(personAdapter);
        overlay.registerAdapterDataObserver(personAdapter);

        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.add_item) {

                    int addedPosition = personDataProvider.insertAfter(list.getChildPosition(list.getChildAt(0)));
                    personAdapter.notifyItemInserted(addedPosition);

                }

                return false;
            }
        });


        samplesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    list.setAdapter(personAdapter);
                    list.removeItemDecoration(overlay);
                    list.addItemDecoration(top);
                }
                else {
                    list.setAdapter(personAdapter);
                    list.removeItemDecoration(top);
                    list.addItemDecoration(overlay);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
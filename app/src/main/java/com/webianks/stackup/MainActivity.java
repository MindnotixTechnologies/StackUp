package com.webianks.stackup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchBox.SearchListener;
import com.webianks.stackup.CustomTabs.CustomTabActivityHelper;
import com.webianks.stackup.Data.MainListAdapter;
import com.webianks.stackup.Data.SetterGetterClass;
import com.webianks.stackup.Network.RequestJasonSearchClass;

import android.speech.RecognizerIntent;
import android.content.Intent;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private SearchBox search;
    private String TAG = "MainActivity";
    private static ListView mListView;
    static Context context;
    static MainListAdapter adapter;
    public static ProgressBar progressBar;
    static List<SetterGetterClass> list;
    RelativeLayout tobehidden;
    public static TextView apiQuota;
    static boolean gotData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        search = (SearchBox) findViewById(R.id.searchbox);
        tobehidden = (RelativeLayout) findViewById(R.id.tobeHidden);
        apiQuota = (TextView) findViewById(R.id.apiQuota);

        setSupportActionBar(toolbar);


        search.setLogoText("Search");
        gotData = false;


        search.setSearchListener(new SearchListener() {

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
            }

            @Override
            public void onSearchTermChanged() {
                //React to the search term changing
                //Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {

                final String FORECAST_BASE_URL =
                        "https://api.stackexchange.com/2.2/search/advanced?";

                final String ORDER_PARAM = "order";
                final String SORT_PARAM = "sort";
                final String ACCEPTED = "accepted";
                final String ANSWERS = "answers";
                final String QUERY_PARAM = "q";
                final String SITE_PARAM = "site";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(ORDER_PARAM, "desc")
                        .appendQueryParameter(SORT_PARAM, "activity")
                        .appendQueryParameter(ACCEPTED, "False")
                        .appendQueryParameter(ANSWERS, "0")
                        .appendQueryParameter(QUERY_PARAM, searchTerm.trim())
                        .appendQueryParameter(SITE_PARAM, "stackoverflow")
                        .build();

                String full_search_query = builtUri.toString();

                Log.d(TAG, full_search_query);

                progressBar.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.INVISIBLE);
                tobehidden.setVisibility(View.INVISIBLE);

                RequestJasonSearchClass.requestJasonSearch(full_search_query, MainActivity.this);

            }

            @Override
            public void onSearchCleared() {
                //Called when the clear button is clicked
            }
        });


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mListView = (ListView) findViewById(R.id.list_view);
        progressBar.setVisibility(View.INVISIBLE);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String url = list.get(position).getLink();
                Uri uri = Uri.parse(url);
                openChromeCustomTab(uri);


            }
        });

        setupDrawer(toolbar);


    }

    private void openChromeCustomTab(Uri uri) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#2196F3"));
        CustomTabsIntent customTabsIntent = builder.build();
        CustomTabActivityHelper.openCustomTab(MainActivity.this, customTabsIntent, uri,
                new CustomTabActivityHelper.CustomTabFallback() {
                    @Override
                    public void openUri(Activity activity, Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
    }

    private void setupDrawer(Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName("Projects").withTag("pro");

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.header)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(), item2
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        if (drawerItem.getTag() == "pro") {

                            String url = "https://play.google.com/store/apps/dev?id=5406110317606112331";
                            Uri uri = Uri.parse(url);
                            openChromeCustomTab(uri);

                        }

                        return true;
                    }
                })
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_filter) {

            if (gotData && list.size() > 0) {

                String[] choices = {"Creation", "Votes"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Sort by");
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection


                        if (item == 0) {

                            sortByCreationDate(list);

                        } else {

                            sortByScore(list);

                        }

                    }


                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }


        return super.onOptionsItemSelected(item);
    }


    private void sortByScore(List<SetterGetterClass> list) {


        Collections.sort(list, new Comparator<SetterGetterClass>() {
            @Override
            public int compare(SetterGetterClass setter2, SetterGetterClass setter1) {
                int l1 = setter1.getScore();
                int l2 = setter2.getScore();

                return l1 > l2 ? -1 : (l1 > l2) ? 1 : 0;
            }
        });

        adapter = new MainListAdapter(list, context);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void sortByCreationDate(List<SetterGetterClass> list) {

        Collections.sort(list, new Comparator<SetterGetterClass>() {
            @Override
            public int compare(SetterGetterClass setter2, SetterGetterClass setter1) {
                long l1 = setter1.getCreationDate();
                long l2 = setter2.getCreationDate();

                return l1 > l2 ? -1 : (l1 > l2) ? 1 : 0;
            }
        });

        adapter = new MainListAdapter(list, context);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void mic(View v) {
        search.micClick(this);
    }


    public static void jsonCallback(List<SetterGetterClass> mlist, int quote) {


        if (mlist != null) {

            gotData = true;
            list = mlist;
            adapter = new MainListAdapter(list, context);
            mListView.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);

            apiQuota.setText("API Quote " + Integer.toString(quote) + "%");
            apiQuota.setVisibility(View.VISIBLE);


        } else {
            gotData = false;
            Toast.makeText(context, "No result found.", Toast.LENGTH_SHORT).show();
        }

    }


}
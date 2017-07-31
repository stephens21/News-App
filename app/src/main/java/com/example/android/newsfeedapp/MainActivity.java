package com.example.android.newsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private NewsAdapter newsAdapter;
    private ListView listView;
    private TextView noConnectionTV;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noConnectionTV = (TextView) findViewById(R.id.no_connection_textView);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressBar);

        loadingProgressBar.setVisibility(View.VISIBLE);

        if (checkConnection() == true) {

            loadingProgressBar.setVisibility(View.GONE);
            noConnectionTV.setVisibility(View.GONE);
            newsAdapter = new NewsAdapter(this, new ArrayList<News>());
            listView = (ListView) findViewById(R.id.list_view);

            listView.setAdapter(newsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    News news = newsAdapter.getItem(i);
                    String webUrl = news.getWebUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(webUrl));
                    startActivity(intent);
                }
            });
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();

        } else {
            loadingProgressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "No Network Connection !", Toast.LENGTH_LONG).show();
            noConnectionTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.refresh_option_menu) {
            refreshNews();
        }
        return true;
    }

    private void refreshNews() {

        if (checkConnection() == true) {

            loadingProgressBar.setVisibility(View.GONE);
            noConnectionTV.setVisibility(View.GONE);
            newsAdapter = new NewsAdapter(this, new ArrayList<News>());
            listView.setAdapter(newsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    News news = newsAdapter.getItem(i);
                    String webUrl = news.getWebUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(webUrl));
                    startActivity(intent);
                }
            });
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();

        } else {
            loadingProgressBar.setVisibility(View.GONE);
            listView.setAdapter(null);
            noConnectionTV.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "No Network Connection !", Toast.LENGTH_LONG).show();

        }
    }


    private boolean checkConnection() {

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoaderTask(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {

        if (data != null || data.size() != 0) {
            newsAdapter.addAll(data);
            Toast.makeText(getApplicationContext(), "News Found !", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "No News Found !", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
        ArrayList<News> news = new ArrayList<>();
        newsAdapter.addAll(news);
    }
}
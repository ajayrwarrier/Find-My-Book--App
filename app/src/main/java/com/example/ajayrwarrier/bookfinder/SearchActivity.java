package com.example.ajayrwarrier.bookfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {
    private ListView listView;
    private String url = "https://www.googleapis.com/books/v1/volumes?q=";
    private String TAG = SearchActivity.class.getSimpleName();
    private String bookName = "";
    private String authorName = "";
    private ArrayList<Book> bookArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String s = getIntent().getStringExtra("SearchTerm");
        url = url + s;
        listView = (ListView) findViewById(R.id.listview);
        new GetBooks().execute();
        final SearchView searchView = (SearchView) findViewById(R.id.searchView2);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search Books");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                intent.putExtra("SearchTerm", searchView.getQuery().toString());
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private class GetBooks extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler hh = new HttpHandler();
            String JsonString = hh.makeServiceCall(url);
            if (JsonString != null) {

                try {
                    JSONObject jsonObj = new JSONObject(JsonString);

                    // Getting JSON Array node
                    JSONArray items = jsonObj.getJSONArray("items");

                    // looping through All Contacts
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject itemsJSONObject = items.getJSONObject(i);


                        JSONObject volumeInfo = itemsJSONObject.getJSONObject("volumeInfo");
                        bookName = volumeInfo.getString("title");

                        JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                        if (authorsArray != null) {
                            authorName = authorsArray.getString(0);
                            for (int j = 1; j < authorsArray.length(); j++) {
                                authorName = authorName + "," + authorsArray.getString(j);
                            }
                        }


                        bookArrayList.add(new Book(bookName, authorName));

                    }
                } catch (final JSONException e) {
                    defaultSetter();
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                defaultSetter();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;


        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            BookAdapter bookAdapter = new BookAdapter(SearchActivity.this, bookArrayList);
            listView.setAdapter(bookAdapter);
        }
    }

    private void defaultSetter() {
        TextView defaultView = (TextView) findViewById(R.id.defaultView);
        defaultView.setText(R.string.defaulttext);

    }


}

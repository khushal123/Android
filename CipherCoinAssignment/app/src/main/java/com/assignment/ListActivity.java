package com.assignment;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ListActivity extends AppCompatActivity {

    RecyclerView rv_products;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        rv_products = (RecyclerView) findViewById(R.id.rv_products);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_products.setLayoutManager(linearLayoutManager);
        getProductList();
    }

    private void getProductList(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(true, 80, 443);
        asyncHttpClient.setTimeout(5000);
        asyncHttpClient.setConnectTimeout(50000);
        asyncHttpClient.setMaxRetriesAndTimeout(1,5000);
        String url = getString(R.string.url)+"?key="+getString(R.string.api_key)+"&token="+getString(R.string.token);
        Log.e("url", url);
        asyncHttpClient.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("response", response.toString());
                if(response != null && response.length() >0){
                    try {

                        JSONArray products = response.getJSONArray("products");
                        ProductListAdapter adapter = new ProductListAdapter(ListActivity.this, products);
                        rv_products.setAdapter(adapter);
                        rv_products.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                }
                else {

                    Toast.makeText(ListActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ListActivity.this, "Data not found", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ListActivity.this, "Data not found", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ListActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.ryan.weather;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Weather extends ActionBarActivity implements View.OnClickListener {

    public static final String WEATHER_BASE_URL =
            "http://api.openweathermap.org/data/2.5/weather?q=";
    TextView promptUser;
    EditText searchCity;
    Button searchCityButton;

    ProgressDialog weatherProgressDialog;
    JSONAdapter mJSONAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Initialize all view fields by id
        findByID();
        initializeDialog();

        searchCityButton.setOnClickListener(this);
    }

    public void findByID(){
        promptUser=(TextView)findViewById(R.id.prompt_user);
        searchCity=(EditText)findViewById(R.id.search_city);
        searchCityButton=(Button)findViewById(R.id.search_button);
    }
    public void initializeDialog(){
        weatherProgressDialog = new ProgressDialog(this);
        weatherProgressDialog.setMessage("Searching for City");
        weatherProgressDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // What happens when the search button is clicked (searchCityButton)
    }

    private void queryBooks(String searchString) {

        // Prepare your search string to be put in a URL
        // It might have reserved characters or something
        String urlString = "";
        try {
            urlString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            // if this fails for some reason, let the user know why
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();

        weatherProgressDialog.show();
        // Have the client get a JSONArray of data
        // and define how to respond
        client.get(WEATHER_BASE_URL + urlString,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // 11. Dismiss the ProgressDialog
                        weatherProgressDialog.dismiss();
                        // Display a "Toast" message
                        // to announce your success
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();

                        // update the data in your custom method.
                        mJSONAdapter.updateData(jsonObject.optJSONArray("docs"));
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        weatherProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Could not find City",Toast.LENGTH_LONG).show();
                    }
                });
    }
}

package com.example.ryan.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;

/**
 * Created by Ryan on 2015-05-06.
 */
public class JSONAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONObject weatherAPI;

    public JSONAdapter(Context context, LayoutInflater inflater){
        mContext = context;
        mInflater = inflater;
        weatherAPI = new JSONObject();
    }

    @Override
    public int getCount() {

        return weatherAPI.length();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            // Inflating the weather_display allows me to use it
            convertView = mInflater.inflate(R.layout.weather_display,null);

            // Create the view that i will hold onto and use later
            holder = new ViewHolder();
            holder.temperatureText= (TextView) convertView.findViewById(R.id.temperature_text);
            holder.humidityText = (TextView) convertView.findViewById(R.id.humidity_text);
            holder.pressureText = (TextView) convertView.findViewById(R.id.pressure_text);

            holder.temperatureInput= (TextView) convertView.findViewById(R.id.temperature_input);
            holder.humidityInput = (TextView) convertView.findViewById(R.id.humidity_input);
            holder.pressureInput = (TextView) convertView.findViewById(R.id.pressure_input);

            // This will allow me to easily access the already inflated view
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }



        String temperature = "";
        String humidity = "";
        String pressure = "";

        if(weatherAPI.has("temp")){
            temperature = weatherAPI.optString("temp");
        }
        if (weatherAPI.has("humidity")){
            humidity = weatherAPI.optString("humidity");
        }
        if (weatherAPI.has("pressure")){
            pressure = weatherAPI.optString("pressure");
        }

        holder.temperatureInput.setText(temperature);
        holder.humidityInput.setText(humidity);
        holder.pressureInput.setText(pressure);

        return convertView;
    }


    public void updateData(JSONObject jsonObject){
        weatherAPI = jsonObject;
        notifyDataSetChanged();
    }
    private static class ViewHolder {
        public TextView temperatureText;
        public TextView humidityText;
        public TextView pressureText;
        public TextView temperatureInput;
        public TextView humidityInput;
        public TextView pressureInput;
    }
}

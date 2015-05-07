package com.example.ryan.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.zip.Inflater;

/**
 * Created by Ryan on 2015-05-06.
 */
public class JSONAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray weatherAPI;

    public JSONAdapter(Context context, LayoutInflater inflater){
        mContext = context;
        mInflater = inflater;
        weatherAPI = new JSONArray();
    }

    @Override
    public int getCount() {
        return weatherAPI.length();
    }

    @Override
    public Object getItem(int position) {
        return weatherAPI.optJSONObject(position);
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

            // This will allow me to easily access the already inflated view
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject weatherAPIObject = (JSONObject) getItem(position);

        String temperature = "";
        String humidity = "";
        String pressure = "";

        if(weatherAPIObject.has("temp")){
            temperature = String.valueOf(weatherAPIObject.optDouble("temp"));
        }
        if (weatherAPIObject.has("humidity")){
            humidity = String.valueOf(weatherAPIObject.optDouble("humidity"));
        }
        if (weatherAPIObject.has("pressure")){
            pressure = String.valueOf(weatherAPIObject.optDouble("pressure"));
        }

        holder.temperatureText.append(temperature);
        holder.humidityText.append(humidity);
        holder.pressureText.append(pressure);

        return convertView;
    }


    public void updateData(JSONArray jsonArray){
        weatherAPI = jsonArray;
        notifyDataSetChanged();
    }
    private static class ViewHolder {
        public TextView temperatureText;
        public TextView humidityText;
        public TextView pressureText;
    }
}

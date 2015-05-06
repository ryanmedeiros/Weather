package com.example.ryan.weather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;

/**
 * Created by Ryan on 2015-05-06.
 */
public class JSONAdapter extends BaseAdapter {

    Context mContext;
    JSONArray weatherAPI;

    public JSONAdapter(Context context){
        mContext = context;
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
        return null;
    }

    public void updateData(JSONArray jsonArray){
        weatherAPI = jsonArray;
        notifyDataSetChanged();
    }
}

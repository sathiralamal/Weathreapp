package com.example.weatherapp;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomAdapter extends BaseAdapter {
    public String TAG = "CustomAdapter";

    Context context;
    String forcastData[];
    LayoutInflater inflter;




    public CustomAdapter(Context getcontext,String [] data){
        Log.i(TAG,"call CustomAdapter method");
        inflter = (LayoutInflater.from(getcontext));
        this.context = getcontext;
        this.forcastData=data;



    }
    @Override
    public int getCount() {
        return forcastData.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG,"Custom adapter getView call");
        convertView=inflter.inflate(R.layout.activity_forcast_list,null);

        TextView forcast_condition=convertView.findViewById(R.id.forcast_condition);
        TextView maxtemp_c=convertView.findViewById(R.id.maxtemp_c);
        TextView daily_chance_of_rain=convertView.findViewById(R.id.daily_chance_of_rain);
        TextView dateTextbox=convertView.findViewById(R.id.date_textbox);
        ImageView forcastImage=convertView.findViewById(R.id.forcast_image);
        TextView maxwind_kph_textbox=convertView.findViewById(R.id.maxwind_kph);

        try {
            JSONObject forcastdateobj=new JSONObject(forcastData[position]);
            JSONObject dayobj= (JSONObject) forcastdateobj.get("day");
                String maxtemp_cdata=(String) dayobj.getString("maxtemp_c");
                String daily_chance_of_raindata=(String)dayobj.getString("daily_chance_of_rain");
                String maxwind_kph=(String)dayobj.getString("maxwind_kph");



            JSONObject conditionobj= (JSONObject) dayobj.get("condition");
                String text= (String) conditionobj.getString("text");
                String wetherimageurl= (String) conditionobj.getString("icon");
                String day =(String) forcastdateobj.get("date");

            Picasso.get().load("https:"+wetherimageurl).into(forcastImage);
            forcast_condition.setText(text);

            maxtemp_c.setText(maxtemp_cdata+"ºC");
            daily_chance_of_rain.setText("chance of rain "+daily_chance_of_raindata+" % ☔");
            maxwind_kph_textbox.setText("wind "+maxwind_kph+"\uD83C\uDF42");
            dateTextbox.setText(day);




        } catch (JSONException e) {
            e.printStackTrace();
        }




        return convertView;
    }
}

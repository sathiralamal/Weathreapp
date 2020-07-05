package com.example.weatherapp;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import android.Manifest;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";
    private FusedLocationProviderClient fusedLocationClient;
    private final int REQUEST_LOCATION_PERMISSION = 1;



    TextView condition_text_box;
    TextView condition_icon;
    TextView feelslike_c_textbox;
    TextView location_country;
    TextView humidity_textbox;
    TextView cloud_textbox;
    TextView passure_textbox;
    TextView uv_textbox;
    TextView gust_kph;
    TextView last_updated;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        condition_text_box = findViewById(R.id.condition_text);
        feelslike_c_textbox = findViewById(R.id.feelslike_c_textbox);
        imageView = findViewById(R.id.imageView);
        location_country=findViewById(R.id.location_country);
        humidity_textbox=findViewById(R.id.humidity_textbox);
        cloud_textbox=findViewById(R.id.cloud_textbox);
        passure_textbox=findViewById(R.id.passure_textbox);
        uv_textbox=findViewById(R.id.uv_textbox);
        gust_kph=findViewById(R.id.gust_kph);
        last_updated=findViewById(R.id.last_updated_text);


        requestLocationPermission();



    }

    public void getCurrentWeather(String location){

       Log.i(TAG,"call getCurrentWeather method");

       Weather weather=new Weather();

       RequestQueue queue = Volley.newRequestQueue(this);
           StringRequest stringRequest=new StringRequest(Request.Method.GET, weather.getBaseURL() + weather.getCurrentWeatherURL()+"?key="+weather.getAPIKEY()+"&q="+location,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                            Log.i(TAG,response);
                            ShowData(response);

                       }
                   }, new Response.ErrorListener(){
               @Override
               public void onErrorResponse(VolleyError error) {
                            Log.i(TAG,String.valueOf(error));

               }
           });

       queue.add(stringRequest);

   }

    public void ShowData(String data ){
        Log.i(TAG,"Call Show Data Method");

       try{

           JSONObject dataobject=new JSONObject(data);

           JSONObject current=(JSONObject)dataobject.get("current");
           JSONObject condition=(JSONObject)current.get("condition");

           String text =(String)condition.getString("text");
           String feelslike_c = (String)current.getString("feelslike_c");

           JSONObject location= (JSONObject) dataobject.get("location");
            String location_name= location.getString("name");
            String location_countrydata= location.getString("country");

            String location_localtime=location.getString("localtime");

            String humidity=current.getString("humidity");
            String uv=current.getString("uv");
            String cloud=current.getString("cloud");
            String pressure_mb=current.getString("pressure_mb");
            String gust_kphdata=current.getString("gust_kph");
            String last_updateddata=(String) current.getString("last_updated");



           condition_text_box.setText(text);
           feelslike_c_textbox.setText(feelslike_c+" C");

           humidity_textbox.setText("Humidity "+humidity+"%"+"\uD83D\uDCA7");
           cloud_textbox.setText("Cloud "+cloud+"%"+" ☁");

           passure_textbox.setText("Passure "+pressure_mb+"mb");
           uv_textbox.setText("UV index "+uv);
           gust_kph.setText("Wind"+gust_kphdata+"Km");

           String wethericon =(String)condition.getString("icon");
           ShowImage(wethericon);

           location_country.setText(location_name+","+location_countrydata);
           last_updated.setText("Last update :"+last_updateddata);




       }catch (Throwable t){
           Log.i(TAG,"Error in String convert to json");
       }

    }

    public void ShowImage(String wetherimageurl){
        Log.i(TAG,"Call Show image method");
        Log.i(TAG,wetherimageurl);

        Picasso.get().load("https:"+wetherimageurl).into(imageView);

    }

    public void GetLocationfromPlay(){

        Log.i(TAG,"Call Get Location method");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG,"Not location permition");



         }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.i(TAG,String.valueOf(location.getLongitude()));
                            Log.i(TAG,String.valueOf(location.getLatitude()));

                            String locationcodinate=String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude());
                            getCurrentWeather(locationcodinate);


                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            GetLocationfromPlay();

        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
            GetLocationfromPlay();

        }
    }












}
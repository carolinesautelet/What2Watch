package com.example.what2watch;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.google_direction.Routing;
import com.example.google_direction.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.maps.*;


public class MapActivity extends FragmentActivity implements RoutingListener {
	
	
	private GoogleMap map = null;
	private LatLng start = null;
	private LatLng end = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		//MapFragment map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
		Double latitudeStart = getIntent().getExtras().getDouble("LatitudeStart");
		Double longitudeStart = getIntent().getExtras().getDouble("LongitudeStart");
		Double latitudeEnd = getIntent().getExtras().getDouble("latitudeEnd");
		Double longitudeEnd = getIntent().getExtras().getDouble("longitudeEnd");
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        start  = new LatLng(latitudeStart, longitudeStart);
		end = new LatLng(latitudeEnd,longitudeEnd);
       CameraUpdate center=CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom=  CameraUpdateFactory.zoomTo(15);
		/*start  = new LatLng(50.6692785, 4.6122516);
		end = new LatLng(50.6682371,5.5541383);
       CameraUpdate center=CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom=  CameraUpdateFactory.zoomTo(15);*/

        map.moveCamera(center);
        map.animateCamera(zoom);
		
		
        map.setMyLocationEnabled(true);
       map.setMapType(2);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 13));

        map.addMarker(new MarkerOptions()
                .title("Cinema")
                .snippet("Cinema choisi")
                .position(start));
        map.addMarker(new MarkerOptions().title("home").snippet("maison").position(end));
        Polyline line = map.addPolyline(new PolylineOptions()
        .add(end,start)
        .width(5)
        .color(Color.RED));
        /*Routing routing = new Routing(Routing.TravelMode.WALKING);
        routing.registerListener(this);
        routing.execute(start, end);*/
        
      
        
	}

	   @Override
	    public void onRoutingFailure() {
	      // The Routing request failed
	    }

	    @Override
	    public void onRoutingStart() {
	      // The Routing Request starts
	    }

	    @Override
	    public void onRoutingSuccess(PolylineOptions mPolyOptions) {
	      PolylineOptions polyoptions = new PolylineOptions();
	      polyoptions.color(Color.BLUE);
	      polyoptions.width(10);
	      polyoptions.addAll(mPolyOptions.getPoints());
	      map.addPolyline(polyoptions);

	      // Start marker
	      MarkerOptions options = new MarkerOptions();
	      options.position(start);
	      options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
	      map.addMarker(options);

	      // End marker
	      options = new MarkerOptions();
	      options.position(end);
	      options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));  
	      map.addMarker(options);
	    }

}



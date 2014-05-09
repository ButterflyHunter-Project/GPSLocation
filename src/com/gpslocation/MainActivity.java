package com.gpslocation;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

/**
 * 
 * ���GPS networkͬʱ��λ���⡣http://blog.csdn.net/limb99/article/details/8765584
 * Ŀǰ����ֻ��ʵ��GPS��
 * 
 * @author Administrator
 *
 */

public class MainActivity extends Activity {
	
	private Button btnStart;  
	private Button btnStop;  
	private TextView textView;  
	private Location mLocation;  
	private LocationManager mLocationManager;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnStart = (Button)findViewById(R.id.btnStart);  
	    btnStop  = (Button)findViewById(R.id.btnStop);  
	    textView = (TextView)findViewById(R.id.text);   //get the text ID and display the location on the textView
	    btnStart.setOnClickListener(btnClickListener);  //start  
	    btnStop.setOnClickListener(btnClickListener);   //stop

	}
	
	public Button.OnClickListener btnClickListener = new Button.OnClickListener()  
    {  
        public void onClick(View v)  
        {  
            Button btn = (Button)v;  
            if(btn.getId() == R.id.btnStart)    //ΪʲôgetID �����Զ���ȡbtnStart�أ�
            {  
                if(!gpsIsOpen())  
                return;  
              
                mLocation = getLocation();  
              
                if(mLocation != null)  
                    textView.setText("Latitude:" + mLocation.getLatitude() + "\nLongitude:" + mLocation.getLongitude());  
                else textView.setText("No GPS data received");  
            }  
            else if(btn.getId() == R.id.btnStop)  
            {  
                mLocationManager.removeUpdates(locationListener);  
            }  
              
        }  
    };  
    
    /*check whether gps is open*/
    private boolean gpsIsOpen()  
    {  
        boolean bRet = true;  
          
        LocationManager alm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);  
        if(!alm.isProviderEnabled(LocationManager.GPS_PROVIDER))  
        {  
            Toast.makeText(this, "GPS inactive", Toast.LENGTH_SHORT)  
            .show();  
              
            bRet = false;  
        }  
        else   
        {  
            Toast.makeText(this, "GPS has been started", Toast.LENGTH_SHORT)  
            .show();  
        }  
  
        return bRet;  
    }  
    
    /*get the gps location*/
    private Location getLocation()  
    {  
        //��ȡλ�ù������  
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);  
  
        //���ҷ�����Ϣ  
        Criteria criteria = new Criteria();  
        criteria.setAccuracy(Criteria.ACCURACY_FINE);   //��λ����: ���  
        criteria.setAltitudeRequired(false);            //������Ϣ������Ҫ  
        criteria.setBearingRequired(false);             //��λ��Ϣ: ����Ҫ  
        criteria.setCostAllowed(true);                  //�Ƿ�������  
        criteria.setPowerRequirement(Criteria.POWER_LOW);  //�ĵ���: �͹���  
          
        String provider = mLocationManager.getBestProvider(criteria, true); //��ȡGPS��Ϣ  
  
        Location location = mLocationManager.getLastKnownLocation(provider);  
          
     // ���ü��������Զ����µ���Сʱ��Ϊ���N��
        mLocationManager.requestLocationUpdates(provider, 2000, 5, locationListener);  
          
        return location;  
    }  
    
    private final LocationListener locationListener = new LocationListener()  
    {  
        public void onLocationChanged(Location location)  
        {  
            // TODO Auto-generated method stub  
        	//makeUseOfNewLocation
            if(location != null)  
                textView.setText("Latitude:" + location.getLatitude() + "\nLongitude:"   
                            + location.getLongitude());  
            else    textView.setText("��ȡ��������" + Integer.toString(10));  
              
        }  
  
        public void onProviderDisabled(String provider)  
        {  
            // TODO Auto-generated method stub  
              
        }  
  
        public void onProviderEnabled(String provider)  
        {  
            // TODO Auto-generated method stub  
              
        }  
  
        public void onStatusChanged(String provider, int status, Bundle extras)  
        {  
            // TODO Auto-generated method stub  
              
        }  
    };  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}

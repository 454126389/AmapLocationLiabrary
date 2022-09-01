package me.xf.demo;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import me.kjx.amaplocation.AmapConfig;
import me.kjx.amaplocation.OnAmapLibraryListen;
import me.kjx.amaplocation.PathSmoothTool;
import me.kjx.amaplocation.UtilsContextOfAmap;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.tbruyelle.rxpermissions.RxPermissions;

public class MainActivity extends AppCompatActivity implements OnAmapLibraryListen.DistanceListen, OnAmapLibraryListen.LocationListen, OnAmapLibraryListen.DrawTraceListen, GeocodeSearch.OnGeocodeSearchListener {

    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    GeocodeSearch geocoderSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        UtilsContextOfAmap.init(this,true,true,"e91b306f59162ad3d8771e2885dfbaf3");


        AmapConfig amapConfig = new AmapConfig();
        //初始化定位相关参数
        amapConfig.initLocation();
        //开始定位
        amapConfig.startLocation();
        //添加监听
        amapConfig.setLocationListen(this);

        PathSmoothTool a=new PathSmoothTool();


        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(24.610421, 118.04714), 200, GeocodeSearch.AMAP);


        if (geocoderSearch==null)
        {
            try {
                geocoderSearch = new GeocodeSearch(this);
                geocoderSearch.setOnGeocodeSearchListener(this);
            } catch (Exception e) {
                Log.e("test","err="+e.toString());
                e.printStackTrace();

            }
        }

        if (geocoderSearch != null) {
            Log.e("test","4");
            geocoderSearch.getFromLocationAsyn(query);
        }else
            Log.e("test","geocoderSearch=null");

        Log.e("test","3");


    }

    //权限检测
    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(permissions)
                .subscribe(permission -> {
                    if (permission.granted) {


                    } else if (permission.shouldShowRequestPermissionRationale) {
                        boolean fineLocationPermissionApproved =
                                ActivityCompat.checkSelfPermission(this,
                                        Manifest.permission.ACCESS_FINE_LOCATION)
                                        == PackageManager.PERMISSION_GRANTED;
                        if (!fineLocationPermissionApproved) {
                            Toast.makeText(MainActivity.this,"请开启定位权限",Toast.LENGTH_LONG).show();
                            return;
                        }
                        boolean backgroundLocationPermissionApproved =
                                ActivityCompat.checkSelfPermission(this,
                                        Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                                        == PackageManager.PERMISSION_GRANTED;

                        if (!backgroundLocationPermissionApproved){
                            // Denied permission without ask never again
                            Toast.makeText(MainActivity.this,"请始终允许定位，否则应用退到后台或手机锁屏后无法记录运动信息",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Denied permission with ask never again
                        //Need to go to the setting

                    }

                });
    }

    @Override
    public void getCurrentAmapLocation(AMapLocation aMapLocation) {
        Log.e("test",aMapLocation.getLatitude()+" "+aMapLocation.getLongitude());
    }

    @Override
    public void getDistance(double distance) {
        Log.e("test","distance="+distance);
    }

    @Override
    public void drawTrace() {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        Log.e("test","i1"+i);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        Log.e("test","i2"+i);
    }
}

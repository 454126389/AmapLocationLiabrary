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
import com.tbruyelle.rxpermissions.RxPermissions;

public class MainActivity extends AppCompatActivity implements OnAmapLibraryListen.DistanceListen, OnAmapLibraryListen.LocationListen, OnAmapLibraryListen.DrawTraceListen {

    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        UtilsContextOfAmap.init(this,true,true);


        AmapConfig amapConfig = new AmapConfig();
        //初始化定位相关参数
        amapConfig.initLocation();
        //开始定位
        amapConfig.startLocation();
        //添加监听
        amapConfig.setLocationListen(this);

        PathSmoothTool a=new PathSmoothTool();
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
}

package com.xf.demo;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.amaplocation.AmapConfig;
import com.tbruyelle.rxpermissions.RxPermissions;

public class MainActivity extends AppCompatActivity  {

    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        AmapConfig amapConfig = new AmapConfig(this);
        //初始化定位相关参数
        amapConfig.initLocation();
        //开始定位
        amapConfig.startLocation();

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
}

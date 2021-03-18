
整合网上各路大神思路,汇总成一个到android 10为止还可正常后台定位的定位工具类,仅为自己整合学习，不稳定维护

### 引入依赖库
```
    项目build.gradle里
    allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}
    模块build.gradle里
    dependencies {
    	        implementation 'com.gitee.kjxweb:AmapLocationLiabrary:Tag'
    	}
```
#### 1.在高德API后台申请地图key


#### 2.在manifest中注册后台服务

```

        <meta-data
            android:name="com.amap.api.v2.apikey"
            android:value="替换成你自己的高德地图KEY" />
        <!-- 定位需要的服务 -->
        <service android:name="com.amap.api.location.APSService" />
        <service android:name="com.example.AmapLocationlibrary.AmapBackGroundService"
              android:enabled="true"
              android:exported="false"
              android:foregroundServiceType="location"
              />

```
#### 3.AndroidManifest注册权限权限
```
        <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" />
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.WAKE_LOCK" />
        <uses-permission android:name="android.permission.VIBRATE" />
        <!-- 解锁屏幕 -->
        <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
        <!-- 获取设备信息 -->
        <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />

        <!-- 针对android 26(O)申请前台服务权限  允许常规应用程序使用Service.startForeground-->
        <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
        <!--针对android 10(Q)申请后台定位权限-->
        <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />

```
#### 4.Android6.0后动态申请权限
```
 private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    //权限检测
    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(permissions)
                .subscribe(permission -> {
                    if (permission.granted) {
                        //开始初始化地图

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
```
#### 5.具体使用参照demo

    建议在Application里初始化
    UtilsContextOfAmap.init(this);


package me.kjx.amaplocation;

import android.content.Context;
import androidx.annotation.Keep;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;


@Keep
public final class UtilsContextOfAmap {

    private static Context context;

    private static Boolean isShowLog;


    private UtilsContextOfAmap() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    @Keep
    public static void init(Context context,Boolean isShowLog,Boolean isNeedAggress,String mapApiKey) {
        //传递整个app生命周期的上下文，避免内存泄露
        UtilsContextOfAmap.context = context.getApplicationContext();

        UtilsContextOfAmap.isShowLog=isShowLog;

        //初始化地图
//        String mapApiKey="";
        MapsInitializer.setApiKey(mapApiKey);
        AMapLocationClient.setApiKey(mapApiKey);
        ServiceSettings.getInstance().setApiKey(mapApiKey);

        //兼容低版本没有这个类
        if (isNeedAggress)
        {
            try {
                //隐私政策合规
                MapsInitializer.updatePrivacyShow(context, true, true);
                MapsInitializer.updatePrivacyAgree(context,true);
                //隐私政策合规
                AMapLocationClient.updatePrivacyShow(context, true, true);
                AMapLocationClient.updatePrivacyAgree(context,true);
                //隐私政策合规
                ServiceSettings.updatePrivacyShow(context, true, true);
                ServiceSettings.updatePrivacyAgree(context,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    @Keep
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    @Keep
    public static Boolean getIsShowLog() {
        return isShowLog;
    }

}
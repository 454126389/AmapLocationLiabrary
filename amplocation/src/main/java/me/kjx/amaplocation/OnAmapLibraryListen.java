package me.kjx.amaplocation;

import android.app.Notification;

import androidx.annotation.Keep;

import com.amap.api.location.AMapLocation;

/**
 * Created by BLiYing on 2018/6/3.
 */
@Keep
public interface OnAmapLibraryListen {
    @Keep
    interface LocationListen{
        @Keep
        void getCurrentAmapLocation(AMapLocation aMapLocation);

    }

    @Keep
    interface DistanceListen{
        void getDistance(double distance);

    }

    @Keep
    interface NotificationListen{
        void getNotificationListen(Notification notification);
    }

    @Keep
    interface DrawTraceListen{
        void drawTrace();
    }

}

package com.example.amaplocation;

import android.content.Context;
import androidx.annotation.Keep;


@Keep
public final class UtilsContextOfAmap {

    private static Context context;

    private UtilsContextOfAmap() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    @Keep
    public static void init(Context context) {
        //传递整个app生命周期的上下文，避免内存泄露
        UtilsContextOfAmap.context = context.getApplicationContext();
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

}
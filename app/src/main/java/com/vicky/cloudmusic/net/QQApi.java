package com.vicky.cloudmusic.net;

import com.vicky.android.baselib.net.ApiHelper;
import com.vicky.android.baselib.net.HttpApi;
import com.vicky.cloudmusic.utils.WYUtils;

/**
 * Created by vicky on 2017/8/29.
 */
public class QQApi extends HttpApi<IQQApi> {

    public IQQApi getApi(){
        return ApiHelper.get(IQQApi.class,null,null);
    }

}

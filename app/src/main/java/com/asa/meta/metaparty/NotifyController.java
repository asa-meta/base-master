package com.asa.meta.metaparty;

import android.content.Context;

public class NotifyController {
    public static final String defultChannlId = "123123";
    public static final String defultChannlName = "456789";


    public static NotifyHelper notifyProgress(Context context){
      return   NotifyHelper.buildNotifyHelper(context).setNotificationId(132).setProgressBuilder(defultChannlId, NotifyHelper.NotifyInfo.build().setTitle("下载测试").setContent("下载测试2"));
    }

}

package com.tencent.qcloud.netdemo.ServiceSample;

import android.util.Log;

import com.tencent.cos.xml.model.service.GetServiceRequest;
import com.tencent.cos.xml.model.service.GetServiceResult;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class GetServiceSample {
    GetServiceRequest getServiceRequest;
    QServiceCfg qServiceCfg;

    public GetServiceSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getServiceRequest = new GetServiceRequest();
        getServiceRequest.setSign(600,null,null);
        try {
            GetServiceResult getServiceResult =
                    qServiceCfg.cosXmlService.getService(getServiceRequest);
            Log.w("XIAO",getServiceResult.printHeaders());
            if(getServiceResult.getHttpCode() >= 300){
                Log.w("XIAO",getServiceResult.printError());
            }
            resultHelper.cosXmlResult = getServiceResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

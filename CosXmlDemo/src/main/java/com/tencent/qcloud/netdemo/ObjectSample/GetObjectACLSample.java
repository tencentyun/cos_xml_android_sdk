package com.tencent.qcloud.netdemo.ObjectSample;

import android.util.Log;

import com.tencent.cos.xml.model.object.GetObjectACLRequest;
import com.tencent.cos.xml.model.object.GetObjectACLResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/5/31.
 * author bradyxiao
 */
public class GetObjectACLSample {
    QServiceCfg qServiceCfg;
    GetObjectACLRequest getObjectACLRequest;
    public GetObjectACLSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getObjectACLRequest = new GetObjectACLRequest();
        getObjectACLRequest.setBucket(qServiceCfg.bucket);
        getObjectACLRequest.setCosPath("/test1.jpg");
        getObjectACLRequest.setSign(600,null,null);
        try {
            GetObjectACLResult getObjectACLResult =
                   qServiceCfg.cosXmlService.getObjectACL(getObjectACLRequest);
            Log.w("XIAO",getObjectACLResult.printHeaders());
            if(getObjectACLResult.getHttpCode() >= 300){
                Log.w("XIAO",getObjectACLResult.printError());
            }else{
                Log.w("XIAO",getObjectACLResult.printBody());
            }
            resultHelper.cosXmlResult = getObjectACLResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }

}

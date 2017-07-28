package com.tencent.qcloud.netdemo.ObjectSample;

import android.util.Log;

import com.tencent.cos.xml.model.object.HeadObjectRequest;
import com.tencent.cos.xml.model.object.HeadObjectResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;



/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class HeadObjectSample {
    HeadObjectRequest headObjectRequest;
    QServiceCfg qServiceCfg;
    public HeadObjectSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        headObjectRequest = new HeadObjectRequest();
        headObjectRequest.setBucket(qServiceCfg.bucket);
       // headObjectRequest.setCosPath("/test/gone.mkv");  //文件存在
        headObjectRequest.setCosPath("/test1.jpg");  //文件不存在
       // headObjectRequest.setIfModifiedSince("");
        headObjectRequest.setSign(600,null,null);

        try {
            HeadObjectResult headObjectResult =
                   qServiceCfg.cosXmlService.headObject(headObjectRequest);
            Log.w("XIAO","headers :\n " + headObjectResult.printHeaders());
            if(headObjectResult.getHttpCode() >= 300){
                Log.w("XIAO","error :\n " +headObjectResult.printError());
            }
            resultHelper.cosXmlResult = headObjectResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.GetBucketLocationRequest;
import com.tencent.cos.xml.model.bucket.GetBucketLocationResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class GetBucketLocationSample {
    GetBucketLocationRequest getBucketLocationRequest;
    QServiceCfg qServiceCfg;
    public GetBucketLocationSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getBucketLocationRequest = new GetBucketLocationRequest();
        getBucketLocationRequest.setBucket(qServiceCfg.bucket);
        getBucketLocationRequest.setSign(600,null,null);
        try {
            GetBucketLocationResult getBucketLocationResult =
                    qServiceCfg.cosXmlService.getBucketLocation(getBucketLocationRequest);
            Log.w("XIAO",getBucketLocationResult.printHeaders());
            if(getBucketLocationResult.getHttpCode() >= 300){
                Log.w("XIAO",getBucketLocationResult.printError());
            }else{
                Log.w("XIAO","" + getBucketLocationResult.printBody());
            }
            resultHelper.cosXmlResult = getBucketLocationResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

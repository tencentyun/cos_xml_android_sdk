package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.HeadBucketRequest;
import com.tencent.cos.xml.model.bucket.HeadBucketResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class HeadBucketSample {
    HeadBucketRequest headBucketRequest;
    QServiceCfg qServiceCfg;

    public HeadBucketSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        headBucketRequest = new HeadBucketRequest();
        headBucketRequest.setBucket(qServiceCfg.bucket);
        headBucketRequest.setSign(600,null,null);
        try {
            HeadBucketResult headBucketResult =
                   qServiceCfg.cosXmlService.headBucket(headBucketRequest);
            Log.w("XIAO",headBucketResult.printHeaders());
            if(headBucketResult.getHttpCode() >= 300){
                Log.w("XIAO",headBucketResult.printError());
            }
            resultHelper.cosXmlResult = headBucketResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

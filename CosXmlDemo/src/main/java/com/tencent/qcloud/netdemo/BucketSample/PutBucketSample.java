package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.PutBucketRequest;
import com.tencent.cos.xml.model.bucket.PutBucketResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class PutBucketSample {
    PutBucketRequest putBucketRequest;
    QServiceCfg qServiceCfg;

    public PutBucketSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        putBucketRequest = new PutBucketRequest();
        putBucketRequest.setBucket("xy100");
        putBucketRequest.setSign(600,null,null);
        try {
            PutBucketResult putBucketResult =
                   qServiceCfg.cosXmlService.putBucket(putBucketRequest);
            Log.w("XIAO",putBucketResult.printHeaders());
            if(putBucketResult.getHttpCode() >= 300){
                Log.w("XIAO",putBucketResult.printError());
            }
            resultHelper.cosXmlResult = putBucketResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

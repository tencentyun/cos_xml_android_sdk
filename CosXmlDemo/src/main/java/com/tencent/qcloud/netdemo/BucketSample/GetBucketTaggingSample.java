package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.GetBucketTaggingRequest;
import com.tencent.cos.xml.model.bucket.GetBucketTaggingResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class GetBucketTaggingSample {
    GetBucketTaggingRequest getBucketTaggingRequest;
    QServiceCfg qServiceCfg;

    public GetBucketTaggingSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getBucketTaggingRequest = new GetBucketTaggingRequest();
        getBucketTaggingRequest.setBucket(qServiceCfg.bucket);
        getBucketTaggingRequest.setSign(600,null,null);
        try {
            GetBucketTaggingResult getBucketTaggingResult =
                   qServiceCfg.cosXmlService.getBucketTagging(getBucketTaggingRequest);
            Log.w("XIAO",getBucketTaggingResult.printHeaders());
            if(getBucketTaggingResult.getHttpCode() >= 300){
                Log.w("XIAO",getBucketTaggingResult.printError());
            }
            resultHelper.cosXmlResult = getBucketTaggingResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}
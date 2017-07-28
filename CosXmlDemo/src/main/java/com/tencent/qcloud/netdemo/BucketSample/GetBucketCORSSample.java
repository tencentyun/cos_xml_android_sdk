package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.GetBucketCORSRequest;
import com.tencent.cos.xml.model.bucket.GetBucketCORSResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class GetBucketCORSSample {
    GetBucketCORSRequest getBucketCORSRequest;
    QServiceCfg qServiceCfg;

    public GetBucketCORSSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getBucketCORSRequest = new GetBucketCORSRequest();
        getBucketCORSRequest.setBucket(qServiceCfg.bucket);
        getBucketCORSRequest.setSign(600,null,null);
        try {
            GetBucketCORSResult getBucketCORSResult =
                    qServiceCfg.cosXmlService.getBucketCORS(getBucketCORSRequest);
            Log.w("XIAO",getBucketCORSResult.printHeaders());
            if(getBucketCORSResult.getHttpCode() >= 300){
                Log.w("XIAO",getBucketCORSResult.printError());
            }
            resultHelper.cosXmlResult = getBucketCORSResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

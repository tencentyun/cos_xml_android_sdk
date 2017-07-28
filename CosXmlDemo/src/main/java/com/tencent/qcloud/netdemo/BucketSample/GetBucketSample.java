package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.GetBucketRequest;
import com.tencent.cos.xml.model.bucket.GetBucketResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class GetBucketSample {
    GetBucketRequest getBucketRequest;
    QServiceCfg qServiceCfg;

    public GetBucketSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getBucketRequest = new GetBucketRequest();
        getBucketRequest.setBucket("xy6");
        getBucketRequest.setMaxKeys(2);
        getBucketRequest.setSign(600,null,null);
        try {
            GetBucketResult getBucketResult =
                     qServiceCfg.cosXmlService.getBucket(getBucketRequest);
            Log.w("XIAO",getBucketResult.printHeaders());
            Log.w("XIAO","etag =" + getBucketResult.getETag());
            if(getBucketResult.getHttpCode() >= 300){
                Log.w("XIAO",getBucketResult.printError());
            }
            resultHelper.cosXmlResult = getBucketResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

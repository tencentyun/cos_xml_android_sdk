package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.GetBucketACLRequest;
import com.tencent.cos.xml.model.bucket.GetBucketACLResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class GetBucketACLSample {
    GetBucketACLRequest getBucketACLRequest;
    QServiceCfg qServiceCfg;

    public GetBucketACLSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getBucketACLRequest = new GetBucketACLRequest();
        getBucketACLRequest.setBucket(qServiceCfg.bucket);
        getBucketACLRequest.setSign(600,null,null);
        try {
            GetBucketACLResult getBucketACLResult =
                    qServiceCfg.cosXmlService.getBucketACL(getBucketACLRequest);
            Log.w("XIAO",getBucketACLResult.printHeaders());
            if(getBucketACLResult.getHttpCode() >= 300){
                Log.w("XIAO",getBucketACLResult.printError());
            }
            resultHelper.cosXmlResult = getBucketACLResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

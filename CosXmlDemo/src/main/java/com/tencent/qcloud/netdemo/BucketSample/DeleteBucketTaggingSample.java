package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.DeleteBucketTaggingRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketTaggingResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class DeleteBucketTaggingSample {
    DeleteBucketTaggingRequest deleteBucketTaggingRequest;
    QServiceCfg qServiceCfg;

    public DeleteBucketTaggingSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        deleteBucketTaggingRequest = new DeleteBucketTaggingRequest();
        deleteBucketTaggingRequest.setBucket(qServiceCfg.bucket);
        deleteBucketTaggingRequest.setSign(600,null,null);

        try {
            DeleteBucketTaggingResult deleteBucketTaggingResult =
                    qServiceCfg.cosXmlService.deleteBucketTagging(deleteBucketTaggingRequest);
            Log.w("XIAO",deleteBucketTaggingResult.printHeaders());
            if(deleteBucketTaggingResult.getHttpCode() >= 300){
                Log.w("XIAO",deleteBucketTaggingResult.printError());
            }
            resultHelper.cosXmlResult = deleteBucketTaggingResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

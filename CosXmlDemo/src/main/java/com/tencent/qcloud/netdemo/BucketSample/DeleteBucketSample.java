package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.DeleteBucketRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class DeleteBucketSample {
    DeleteBucketRequest deleteBucketRequest;
    QServiceCfg qServiceCfg;

    public DeleteBucketSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        deleteBucketRequest = new DeleteBucketRequest();
        deleteBucketRequest.setBucket("xy3");
        deleteBucketRequest.setSign(600,null,null);
        try {
            DeleteBucketResult deleteBucketResult =
                   qServiceCfg.cosXmlService.deleteBucket(deleteBucketRequest);
            Log.w("XIAO",deleteBucketResult.printHeaders());
            if(deleteBucketResult.getHttpCode() >= 300){
                Log.w("XIAO",deleteBucketResult.printError());
            }
            resultHelper.cosXmlResult = deleteBucketResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

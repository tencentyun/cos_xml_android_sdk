package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.DeleteBucketCORSRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketCORSResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class DeleteBucketCORSSample {
    DeleteBucketCORSRequest deleteBucketCORSRequest;
    QServiceCfg qServiceCfg;

    public DeleteBucketCORSSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        deleteBucketCORSRequest = new DeleteBucketCORSRequest();
        deleteBucketCORSRequest.setBucket(qServiceCfg.bucket);
        deleteBucketCORSRequest.setSign(600,null,null);
        try {
            DeleteBucketCORSResult deleteBucketCORSResult =
                     qServiceCfg.cosXmlService.deleteBucketCORS(deleteBucketCORSRequest);
            Log.w("XIAO",deleteBucketCORSResult.printHeaders());
            if(deleteBucketCORSResult.getHttpCode() >= 300){
                Log.w("XIAO",deleteBucketCORSResult.printError());
            }else{
                Log.w("XIAO","" + deleteBucketCORSResult.printBody());
            }
            resultHelper.cosXmlResult = deleteBucketCORSResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

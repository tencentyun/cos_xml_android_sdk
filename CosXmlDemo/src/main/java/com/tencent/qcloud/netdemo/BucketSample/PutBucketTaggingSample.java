package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.PutBucketTaggingRequest;
import com.tencent.cos.xml.model.bucket.PutBucketTaggingResult;
import com.tencent.cos.xml.model.tag.Tag;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class PutBucketTaggingSample {
    PutBucketTaggingRequest putBucketTaggingRequest;
    QServiceCfg qServiceCfg;

    public PutBucketTaggingSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        putBucketTaggingRequest = new PutBucketTaggingRequest();
        putBucketTaggingRequest.setBucket(qServiceCfg.bucket);
        Tag tag = new Tag();
        tag.key = "1";
        tag.value = "value_1";
        putBucketTaggingRequest.setTagList(tag);
        Tag tag2 = new Tag();
        tag2.key = "2";
        tag2.value = "value_2";
        putBucketTaggingRequest.setTagList(tag2);
        putBucketTaggingRequest.setSign(600,null,null);
        try {
            PutBucketTaggingResult putBucketTaggingResult =
                 qServiceCfg.cosXmlService.putBucketTagging(putBucketTaggingRequest);
            Log.w("XIAO",putBucketTaggingResult.printHeaders());
            if(putBucketTaggingResult.getHttpCode() >= 300){
                Log.w("XIAO",putBucketTaggingResult.printError());
            }
            resultHelper.cosXmlResult = putBucketTaggingResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

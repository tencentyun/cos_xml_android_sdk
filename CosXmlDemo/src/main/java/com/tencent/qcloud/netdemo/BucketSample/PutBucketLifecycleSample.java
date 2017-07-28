package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.model.bucket.PutBucketLifecycleRequest;
import com.tencent.cos.xml.model.bucket.PutBucketLifecycleResult;
import com.tencent.cos.xml.model.tag.AbortIncompleteMultiUpload;
import com.tencent.cos.xml.model.tag.Expiration;
import com.tencent.cos.xml.model.tag.Rule;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/2.
 * author bradyxiao
 */
public class PutBucketLifecycleSample {
    PutBucketLifecycleRequest putBucketLifecycleRequest;
    QServiceCfg qServiceCfg;

    public PutBucketLifecycleSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        putBucketLifecycleRequest = new PutBucketLifecycleRequest();
        putBucketLifecycleRequest.setBucket(qServiceCfg.bucket);

        Rule rule = new Rule();
        rule.id = "lifeID";
        rule.status = "Enabled";
        //配置未完成分块上传的定期删除规则
        rule.abortIncompleteMultiUpload = new AbortIncompleteMultiUpload();
        rule.abortIncompleteMultiUpload.daysAfterInitiation = "1";
        putBucketLifecycleRequest.setRuleList(rule);

//        Rule rule2 = new Rule();
//        rule2.id = "lifeID2";
//        rule2.status = "Enabled";
//        //配置文件的定期删除规则
//        rule2.expiration = new Expiration();
//        rule2.expiration.days = "1";
//        putBucketLifecycleRequest.setRuleList(rule2);

        putBucketLifecycleRequest.setSign(600,null,null);
        try {
            PutBucketLifecycleResult putBucketLifecycleResult =
                    qServiceCfg.cosXmlService.putBucketLifecycle(putBucketLifecycleRequest);
            Log.w("XIAO",putBucketLifecycleResult.printHeaders());
            if(putBucketLifecycleResult.getHttpCode() >= 300){
                Log.w("XIAO",putBucketLifecycleResult.printError());
            }
            resultHelper.cosXmlResult = putBucketLifecycleResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

package com.tencent.qcloud.netdemo.BucketSample;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.CosXmlResultListener;
import com.tencent.cos.xml.model.bucket.PutBucketCORSRequest;
import com.tencent.cos.xml.model.bucket.PutBucketCORSResult;
import com.tencent.cos.xml.model.tag.CORSRule;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultActivity;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class PutBucketCORSSample {
    PutBucketCORSRequest putBucketCORSRequest;
    QServiceCfg qServiceCfg;

    public PutBucketCORSSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        putBucketCORSRequest = new PutBucketCORSRequest();
        putBucketCORSRequest.setBucket(qServiceCfg.bucket);
        putBucketCORSRequest.setSign(600,null,null);
        CORSRule corsRule = new CORSRule();
        corsRule.id = "123";
        corsRule.allowedOrigin = "http://www.qcloud.com";
        corsRule.maxAgeSeconds = "5000";
        List<String> methods = new LinkedList<>();
        methods.add("put");
        methods.add("post");
        methods.add("get");
        corsRule.allowedMethod = methods;

        List<String> headers = new LinkedList<>();
        headers.add("host");
        headers.add("content-type");
        headers.add("authorizion");
        corsRule.allowedHeader = headers;

        List<String> exposeHeaders = new LinkedList<>();
        exposeHeaders.add("x-cos-metha-1");
        exposeHeaders.add("x-cos-metha-2");
        exposeHeaders.add("x-cos-metha-3");
        corsRule.exposeHeader = exposeHeaders;

        putBucketCORSRequest.setCORSRuleList(corsRule);
        try {
            PutBucketCORSResult putBucketCORSResult =
                    qServiceCfg.cosXmlService.putBucketCORS(putBucketCORSRequest);
            Log.w("XIAO",putBucketCORSResult.printHeaders());
            if(putBucketCORSResult.getHttpCode() >= 300){
                Log.w("XIAO",putBucketCORSResult.printError());
            }
            resultHelper.cosXmlResult = putBucketCORSResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }

    /**
     *
     * 采用异步回调操作
     *
     */
    public void startAsync(final Activity activity){
        putBucketCORSRequest = new PutBucketCORSRequest();
        putBucketCORSRequest.setBucket(qServiceCfg.bucket);
        putBucketCORSRequest.setSign(600,null,null);
        CORSRule corsRule = new CORSRule();
        corsRule.id = "123";
        corsRule.allowedOrigin = "http://www.qcloud.com";
        corsRule.maxAgeSeconds = "5000";
        List<String> methods = new LinkedList<>();
        methods.add("put");
        methods.add("post");
        methods.add("get");
        corsRule.allowedMethod = methods;

        List<String> headers = new LinkedList<>();
        headers.add("host");
        headers.add("content-type");
        headers.add("authorizion");
        corsRule.allowedHeader = headers;

        List<String> exposeHeaders = new LinkedList<>();
        exposeHeaders.add("x-cos-metha-1");
        exposeHeaders.add("x-cos-metha-2");
        exposeHeaders.add("x-cos-metha-3");
        corsRule.exposeHeader = exposeHeaders;

        putBucketCORSRequest.setCORSRuleList(corsRule);
        qServiceCfg.cosXmlService.putBucketCORSAsync(putBucketCORSRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(cosXmlResult.printHeaders())
                        .append(cosXmlResult.printBody());
                Log.w("XIAO", "success = " + stringBuilder.toString());
                show(activity, stringBuilder.toString());
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(cosXmlResult.printHeaders())
                        .append(cosXmlResult.printError());
                Log.w("XIAO", "failed = " + stringBuilder.toString());
                show(activity, stringBuilder.toString());
            }
        });
    }

    private void show(Activity activity, String message){
        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra("RESULT", message);
        activity.startActivity(intent);
    }
}

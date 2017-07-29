package com.tencent.qcloud.netdemo.BucketSample;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.CosXmlResultListener;
import com.tencent.cos.xml.model.bucket.GetBucketRequest;
import com.tencent.cos.xml.model.bucket.GetBucketResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultActivity;
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

    /**
     *
     * 采用同步操作
     *
     */
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

    /**
     *
     * 采用异步回调操作
     *
     */
    public void startAsync(final Activity activity) {
        getBucketRequest = new GetBucketRequest();
        getBucketRequest.setBucket("xy6");
        getBucketRequest.setMaxKeys(2);
        getBucketRequest.setSign(600, null, null);
        qServiceCfg.cosXmlService.getBucketAsync(getBucketRequest, new CosXmlResultListener() {
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

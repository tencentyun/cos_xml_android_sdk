package com.tencent.qcloud.netdemo.ObjectSample;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.CosXmlResultListener;
import com.tencent.cos.xml.model.object.AbortMultiUploadRequest;
import com.tencent.cos.xml.model.object.AbortMultiUploadResult;
import com.tencent.qcloud.netdemo.ResultActivity;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/5/31.
 * author bradyxiao
 */
public class AbortMultiUploadSample {
    AbortMultiUploadRequest abortMultiUploadRequest;
    QServiceCfg qServiceCfg;
    public AbortMultiUploadSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        abortMultiUploadRequest = new AbortMultiUploadRequest();
        abortMultiUploadRequest.setBucket(qServiceCfg.bucket);
        abortMultiUploadRequest.setCosPath("/test/multi.txt");
        abortMultiUploadRequest.setUploadId("149628627143ed87e434d19b54a61d79e578cdc64192894de571c73f41d79772e0c0170e58");
        abortMultiUploadRequest.setSign(600,null,null);
        try {
            AbortMultiUploadResult abortMultiUploadResult =
                 qServiceCfg.cosXmlService.abortMultiUpload(abortMultiUploadRequest);
            Log.w("XIAO",abortMultiUploadResult.printHeaders());
            if(abortMultiUploadResult.getHttpCode() >= 300){
                Log.w("XIAO",abortMultiUploadResult.printError());
            }
            resultHelper.cosXmlResult = abortMultiUploadResult;
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
        abortMultiUploadRequest = new AbortMultiUploadRequest();
        abortMultiUploadRequest.setBucket(qServiceCfg.bucket);
        abortMultiUploadRequest.setCosPath("/test/multi.txt");
        abortMultiUploadRequest.setUploadId("149628627143ed87e434d19b54a61d79e578cdc64192894de571c73f41d79772e0c0170e58");
        abortMultiUploadRequest.setSign(600,null,null);
        qServiceCfg.cosXmlService.abortMultiUploadAsync(abortMultiUploadRequest, new CosXmlResultListener() {
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

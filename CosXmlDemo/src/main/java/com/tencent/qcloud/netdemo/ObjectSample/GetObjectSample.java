package com.tencent.qcloud.netdemo.ObjectSample;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.CosXmlResultListener;
import com.tencent.cos.xml.model.object.GetObjectRequest;
import com.tencent.cos.xml.model.object.GetObjectResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultActivity;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.QCloudProgressListener;
import com.tencent.qcloud.network.exception.QCloudException;

import java.io.File;

/**
 * Created by bradyxiao on 2017/6/7.
 * author bradyxiao
 */
public class GetObjectSample {
    GetObjectRequest getObjectRequest;
    QServiceCfg qServiceCfg;
    public GetObjectSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        getObjectRequest = new GetObjectRequest(Environment.getExternalStorageDirectory().getPath());
        getObjectRequest.setBucket(qServiceCfg.bucket);
        getObjectRequest.setCosPath("/415.txt");
        getObjectRequest.setSign(600,null,null);
        getObjectRequest.setRange(1);
        getObjectRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                Log.w("XIAO","progress = "+progress+" max = "+max);
            }
        });
        try {
            GetObjectResult getObjectResult = qServiceCfg.cosXmlService.getObject(getObjectRequest);
            resultHelper.cosXmlResult = getObjectResult;
            Log.w("XIAO","headers :\n " + getObjectResult.printHeaders());
            if(getObjectResult.getHttpCode() >= 300){
                Log.w("XIAO","error :\n " +getObjectResult.printError());
            }
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
        getObjectRequest = new GetObjectRequest(Environment.getExternalStorageDirectory().getPath());
        getObjectRequest.setBucket(qServiceCfg.bucket);
        getObjectRequest.setCosPath("/415.txt");
        getObjectRequest.setSign(600,null,null);
        getObjectRequest.setRange(1);
        getObjectRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                Log.w("XIAO","progress = "+progress+" max = "+max);
            }
        });
        qServiceCfg.cosXmlService.getObjectAsync(getObjectRequest, new CosXmlResultListener() {
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

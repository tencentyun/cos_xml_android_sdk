package com.tencent.qcloud.netdemo.ObjectSample;


import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.CosXmlResultListener;
import com.tencent.cos.xml.model.object.AppendObjectRequest;
import com.tencent.cos.xml.model.object.AppendObjectResult;
import com.tencent.qcloud.netdemo.ResultActivity;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.QCloudProgressListener;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/5/31.
 * author bradyxiao
 */
public class AppendObjectSample {
    AppendObjectRequest appendObjectRequest;
    QServiceCfg qServiceCfg;
    public AppendObjectSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        appendObjectRequest = new AppendObjectRequest();
        appendObjectRequest.setBucket(qServiceCfg.bucket);
        appendObjectRequest.setCosPath("/appentTest.txt");
        appendObjectRequest.setPosition(0);
        appendObjectRequest.setSign(600,null,null);
        appendObjectRequest.setSrcPath(Environment.getExternalStorageDirectory().getPath() + "/init.txt");
        //appendObjectRequest.setData(new String("this is a append object test by data").getBytes());
        appendObjectRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                Log.w("XIAO","progress =" + progress * 1.0/max);
            }
        });
        try {
            AppendObjectResult appendObjectResult =
                   qServiceCfg.cosXmlService.appendObject(appendObjectRequest);
            resultHelper.cosXmlResult = appendObjectResult;
            Log.w("XIAO",appendObjectResult.printHeaders());
            if(appendObjectResult.getHttpCode() >= 300){
                Log.w("XIAO",appendObjectResult.printError());
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
        appendObjectRequest = new AppendObjectRequest();
        appendObjectRequest.setBucket(qServiceCfg.bucket);
        appendObjectRequest.setCosPath("/appentTest.txt");
        appendObjectRequest.setPosition(0);
        appendObjectRequest.setSign(600,null,null);
        appendObjectRequest.setSrcPath(Environment.getExternalStorageDirectory().getPath() + "/init.txt");
        //appendObjectRequest.setData(new String("this is a append object test by data").getBytes());
        appendObjectRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                Log.w("XIAO","progress =" + progress * 1.0/max);
            }
        });
        qServiceCfg.cosXmlService.appendObjectAsync(appendObjectRequest, new CosXmlResultListener() {
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

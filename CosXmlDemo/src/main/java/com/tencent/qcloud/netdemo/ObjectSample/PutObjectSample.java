package com.tencent.qcloud.netdemo.ObjectSample;

import android.os.Environment;
import android.util.Log;

import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.netdemo.common.SHA1Utils;
import com.tencent.qcloud.network.QCloudProgressListener;
import com.tencent.qcloud.network.QCloudRequest;
import com.tencent.qcloud.network.QCloudResult;
import com.tencent.qcloud.network.QCloudResultListener;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class PutObjectSample {
    PutObjectRequest putObjectRequest;
    QServiceCfg qServiceCfg;

    volatile boolean isCancel = false;

    public PutObjectSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        putObjectRequest = new PutObjectRequest();
        putObjectRequest.setBucket(qServiceCfg.bucket);
        putObjectRequest.setCosPath("/Java编程思想第四版.pdf");
        putObjectRequest.setSrcPath(Environment.getExternalStorageDirectory().getPath() + "/Java编程思想第4版.pdf");
        //putObjectRequest.setXCOSContentSha1(SHA1Utils.getSHA1FromPath(Environment.getExternalStorageDirectory().getPath() + "/test1.jpg"));

        putObjectRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                float result = (float) (progress * 100.0/max);
                Log.w("XIAO","progress =" + (long)result + "%"  + " ------------" + progress + "/" + max);
            }
        });
        putObjectRequest.setSign(600,null,null);




        try {
            final PutObjectResult putObjectResult =
                    qServiceCfg.cosXmlService.putObject(putObjectRequest);

            Log.w("XIAO",putObjectResult.printHeaders());
            if(putObjectResult.getHttpCode() >= 300){
                Log.w("XIAO",putObjectResult.printError());
                StringBuilder stringBuilder = new StringBuilder("Error\n");
                stringBuilder.append(putObjectResult.error.code)
                        .append(putObjectResult.error.message)
                        .append(putObjectResult.error.resource)
                        .append(putObjectResult.error.requestId)
                        .append(putObjectResult.error.traceId);
                Log.w("TEST",stringBuilder.toString());
            }else{
                Log.w("TEST",putObjectResult.accessUrl);
            }
            resultHelper.cosXmlResult = putObjectResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

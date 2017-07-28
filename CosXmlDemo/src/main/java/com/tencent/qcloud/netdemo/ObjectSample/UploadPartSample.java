package com.tencent.qcloud.netdemo.ObjectSample;

import android.os.Environment;
import android.util.Log;

import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.model.object.UploadPartResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.QCloudProgressListener;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class UploadPartSample {
    UploadPartRequest uploadPartRequest;
    QServiceCfg qServiceCfg;

    public UploadPartSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucket(qServiceCfg.bucket);
        uploadPartRequest.setCosPath("/test/Java并发编程的艺术2.pdf");
        uploadPartRequest.setUploadId("1501150487f70fb5ee1c1b46b034ed1ac41edd0b3e34618e4314f5bb9b7d153b18a0c29720");
        uploadPartRequest.setPartNumber(1);
        uploadPartRequest.setSign(600,null,null);
        uploadPartRequest.setSrcPath(Environment.getExternalStorageDirectory().getPath() + "/Java并发编程的艺术2.pdf");
        uploadPartRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                Log.w("XIAO","progress =" + progress * 1.0/max);
            }
        });
        try {
            UploadPartResult uploadPartResult =
                    qServiceCfg.cosXmlService.uploadPart(uploadPartRequest);
            Log.w("XIAO",uploadPartResult.printHeaders());
            if(uploadPartResult.getHttpCode() >= 300){
                Log.w("XIAO",uploadPartResult.printError());
            }else{
                Log.w("XIAO","etag= " + uploadPartResult.getETag());
            }
            resultHelper.cosXmlResult = uploadPartResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO", "exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

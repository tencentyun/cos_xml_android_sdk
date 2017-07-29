package com.tencent.qcloud.netdemo.CancelSample;

import android.os.Environment;
import android.util.Log;

import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.QCloudProgressListener;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class CancelPutObjectRequestSample {

    PutObjectRequest putObjectRequest;
    QServiceCfg qServiceCfg;

    public CancelPutObjectRequestSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public PutObjectResult start(){
        putObjectRequest = new PutObjectRequest();
        putObjectRequest.setBucket(qServiceCfg.bucket);
//        putObjectRequest.setCosPath("/1.mp4");
//        putObjectRequest.setSrcPath(Environment.getExternalStorageDirectory().getPath() + "/1.mp4");
//        putObjectRequest.setXCOSContentSha1(SHA1Utils.getSHA1FromPath(Environment.getExternalStorageDirectory().getPath() + "/1.mp4"));

        /***/
        putObjectRequest.setCosPath("/data.txt");
        //byte[] data = new String("put object through byte[]").getBytes();
        putObjectRequest.setSrcPath(Environment.getExternalStorageDirectory()+"/eclipse.tar");
        //putObjectRequest.setData(data);
        //putObjectRequest.setXCOSContentSha1(SHA1Utils.getSHA1FromBytes(data,0,data.length));

        putObjectRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                Log.w("XIAO","progress =" + progress * 1.0/max);
            }
        });
        putObjectRequest.setSourceSerializer(new CosXmlSignatureSourceSerializer(5000));
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    qServiceCfg.cosXmlService.cancel(putObjectRequest);
                }
            }).start();


            PutObjectResult putObjectResult =
                    (PutObjectResult) qServiceCfg.cosXmlService.putObject(putObjectRequest);

            return putObjectResult;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            e.printStackTrace();
        }
        return null;
    }
}

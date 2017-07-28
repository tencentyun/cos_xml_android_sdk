package com.tencent.qcloud.netdemo.ObjectSample;

import android.os.Environment;
import android.util.Log;

import com.tencent.cos.xml.common.ResumeData;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.AbortMultiUploadResult;
import com.tencent.cos.xml.model.object.MultipartUploadHelper;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.QCloudProgressListener;
import com.tencent.qcloud.network.QCloudRequest;
import com.tencent.qcloud.network.QCloudResult;
import com.tencent.qcloud.network.QCloudResultListener;
import com.tencent.qcloud.network.exception.QCloudException;

import java.io.IOException;

/**
 * Created by bradyxiao on 2017/6/5.
 * author bradyxiao
 */
public class MultipartUploadHelperSample {
    MultipartUploadHelper multipartUploadHelper;
    QServiceCfg qServiceCfg;
    volatile boolean isAbort = false;

    volatile ResumeData cancelResult;
//    volatile AbortMultiUploadResult cancelResult;

    public MultipartUploadHelperSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start() {
        ResultHelper resultHelper = new ResultHelper();
        multipartUploadHelper = new MultipartUploadHelper(qServiceCfg.cosXmlService);
        multipartUploadHelper.setBucket("xy2");
        multipartUploadHelper.setCosPath("/Java并发编程的艺术.pdf");
        multipartUploadHelper.setSliceSize(1024 * 1024);
        multipartUploadHelper.setSrcPath(Environment.getExternalStorageDirectory().getPath() + "/Java并发编程的艺术.pdf");
        multipartUploadHelper.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                float result = (float) (progress * 100.0 / max);
                Log.w("XIAO", "progress =" + (long) result + "%" + " ------------" + progress + "/" + max);
//                if(isAbort){
//                    Log.w("XIAO_RESUME","resume");
//                    Log.w("XIAO_RESUME", "progress =" + (long) result + "%" + " ------------" + progress + "/" + max);
//                }
//                if (result > 3.0f && !isAbort) {
//                    isAbort = true;
//                    cancelResult = multipartUploadHelper.cancel();
//                }
            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(!isAbort);
//                cancelResult = multipartUploadHelper.cancel();
////                try {
////                    cancelResult = multipartUploadHelper.abort();
////                } catch (QCloudException e) {
////                    e.printStackTrace();
////                }
//            }
//        }).start();
        try {
            resultHelper.cosXmlResult = multipartUploadHelper.upload();
            Log.w("XIAO",resultHelper.cosXmlResult.accessUrl);
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO_NEW", "exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
//            while (cancelResult == null) ;
//            Log.w("XIAO_NEW", cancelResult.bucket + "|" + cancelResult.cosPath + "|" +
//                    cancelResult.uploadId + "|" + cancelResult.sliceSize + "|");
//            try {
//                resultHelper.cosXmlResult = multipartUploadHelper.resume(cancelResult);
//                Log.w("XIAO_NEW",resultHelper.cosXmlResult.printHeaders() + "|" + resultHelper.cosXmlResult.printBody() + "|" +
//                        resultHelper.cosXmlResult.printError());
//                return resultHelper;
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                resultHelper.exception = (QCloudException) e1;
//            }
            return resultHelper;
        }
    }
}

package com.tencent.qcloud.netdemo.ObjectSample;

import android.os.Environment;
import android.util.Log;

import com.tencent.cos.xml.model.object.CompleteMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadResult;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.MD5Utils;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/5/31.
 * author bradyxiao
 */
public class CompleteMultiUploadSample {
    CompleteMultiUploadRequest completeMultiUploadRequest;
    QServiceCfg qServiceCfg;

    public CompleteMultiUploadSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        completeMultiUploadRequest = new CompleteMultiUploadRequest();
        completeMultiUploadRequest.setBucket(qServiceCfg.bucket);
        completeMultiUploadRequest.setCosPath("/test/Java并发编程的艺术.pdf");
        completeMultiUploadRequest.setUploadId("1501150487f70fb5ee1c1b46b034ed1ac41edd0b3e34618e4314f5bb9b7d153b18a0c29720");
        completeMultiUploadRequest.setPartNumberAndETag(1,MD5Utils.getMD5FromPath(Environment.getExternalStorageDirectory().getPath() + "/Java并发编程的艺术.pdf"));
        //completeMultiUploadRequest.setPartNumberAndETag(1, MD5Utils.getMD5FromPath(Environment.getExternalStorageDirectory().getPath() + "/init.txt"));
        completeMultiUploadRequest.setSign(600,null,null);
        try {
            CompleteMultiUploadResult completeMultiUploadResult =
                    qServiceCfg.cosXmlService.completeMultiUpload(completeMultiUploadRequest);
            Log.w("XIAO",completeMultiUploadResult.printHeaders());
            if(completeMultiUploadResult.getHttpCode() >= 300){
                Log.w("XIAO",completeMultiUploadResult.printError());
            }else{
                Log.w("XIAO","" + completeMultiUploadResult.printBody());
                Log.w("XIAO","accessUrl =" + completeMultiUploadResult.accessUrl);
            }
            resultHelper.cosXmlResult = completeMultiUploadResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

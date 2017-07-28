package com.tencent.qcloud.netdemo.ObjectSample;

import android.util.Log;

import com.tencent.cos.xml.model.object.InitMultipartUploadRequest;
import com.tencent.cos.xml.model.object.InitMultipartUploadResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class InitMultipartUploadSample {
    InitMultipartUploadRequest initMultipartUploadRequest;
    QServiceCfg qServiceCfg;

    public InitMultipartUploadSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        initMultipartUploadRequest = new InitMultipartUploadRequest();
        initMultipartUploadRequest.setBucket(qServiceCfg.bucket);
        initMultipartUploadRequest.setCosPath("/test/Java并发编程的艺术2.pdf");
        initMultipartUploadRequest.setSign(600,null,null);
        try {
            InitMultipartUploadResult initMultipartUploadResult =
                  qServiceCfg.cosXmlService.initMultipartUpload(initMultipartUploadRequest);
            Log.w("XIAO",initMultipartUploadResult.printHeaders());
            if(initMultipartUploadResult.getHttpCode() >= 300){
                Log.w("XIAO",initMultipartUploadResult.error.toString());
            }else {
                Log.w("XIAO","" + initMultipartUploadResult.printBody());
                Log.w("XIAO","uploadId =" + initMultipartUploadResult.initMultipartUpload.uploadId);
            }
            resultHelper.cosXmlResult = initMultipartUploadResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

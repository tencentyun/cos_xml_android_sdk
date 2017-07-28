package com.tencent.qcloud.netdemo.ObjectSample;

import android.util.Log;

import com.tencent.cos.xml.model.object.DeleteObjectRequest;
import com.tencent.cos.xml.model.object.DeleteObjectResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/5/31.
 * author bradyxiao
 */
public class DeleteObjectSample {
    QServiceCfg qServiceCfg;
    DeleteObjectRequest deleteObjectRequest;
    public DeleteObjectSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }
    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        deleteObjectRequest = new DeleteObjectRequest();
        deleteObjectRequest.setBucket(qServiceCfg.bucket);
        deleteObjectRequest.setCosPath("/appentTest.txt");
        deleteObjectRequest.setSign(600,null,null);
        try {
            DeleteObjectResult deleteObjectResult =
                    qServiceCfg.cosXmlService.deleteObject(deleteObjectRequest);
            Log.w("XIAO",deleteObjectResult.printHeaders());
            if(deleteObjectResult.getHttpCode() >= 300){
                Log.w("XIAO",deleteObjectResult.printError());
            }
            resultHelper.cosXmlResult = deleteObjectResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }

}

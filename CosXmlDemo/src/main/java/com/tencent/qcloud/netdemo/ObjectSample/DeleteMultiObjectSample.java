package com.tencent.qcloud.netdemo.ObjectSample;

import android.util.Log;

import com.tencent.cos.xml.model.object.DeleteMultiObjectRequest;
import com.tencent.cos.xml.model.object.DeleteMultiObjectResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/5/31.
 * author bradyxiao
 */
public class DeleteMultiObjectSample {
    DeleteMultiObjectRequest deleteMultiObjectRequest;
    QServiceCfg qServiceCfg;

    public DeleteMultiObjectSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        deleteMultiObjectRequest = new DeleteMultiObjectRequest();
        deleteMultiObjectRequest.setBucket(qServiceCfg.bucket);
        deleteMultiObjectRequest.setQuiet(false);
        deleteMultiObjectRequest.setObjectList("test/2");
        deleteMultiObjectRequest.setObjectList("2/1491967729774.jpg");
        deleteMultiObjectRequest.setObjectList("2/1491967730522.jpg");
        deleteMultiObjectRequest.setObjectList("2/1491968133919.jpg");
        deleteMultiObjectRequest.setObjectList("2/1491968135135.jpg");
        deleteMultiObjectRequest.setObjectList("2/1491968134646.jpg");
        deleteMultiObjectRequest.setSign(600,null,null);
        try {
            DeleteMultiObjectResult deleteMultiObjectResult =
                     qServiceCfg.cosXmlService.deleteMultiObject(deleteMultiObjectRequest);
            Log.w("XIAO",deleteMultiObjectResult.printHeaders());
            if(deleteMultiObjectResult.getHttpCode() >= 300){
                Log.w("XIAO",deleteMultiObjectResult.printError());
            }
            resultHelper.cosXmlResult = deleteMultiObjectResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }

}

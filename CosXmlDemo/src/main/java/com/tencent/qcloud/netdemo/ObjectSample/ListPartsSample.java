package com.tencent.qcloud.netdemo.ObjectSample;

import android.util.Log;

import com.tencent.cos.xml.model.object.ListPartsRequest;
import com.tencent.cos.xml.model.object.ListPartsResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class ListPartsSample {
    ListPartsRequest listPartsRequest;
    QServiceCfg qServiceCfg;

    public ListPartsSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        listPartsRequest = new ListPartsRequest();
        listPartsRequest.setBucket(qServiceCfg.bucket);
        listPartsRequest.setCosPath("/test/Java并发编程的艺术2.pdf");
        listPartsRequest.setUploadId("1501150487f70fb5ee1c1b46b034ed1ac41edd0b3e34618e4314f5bb9b7d153b18a0c29720");
        listPartsRequest.setSign(600,null,null);
        try {
            ListPartsResult listPartsResult =
                    qServiceCfg.cosXmlService.listParts(listPartsRequest);
            Log.w("XIAO",listPartsResult.printHeaders());
            if(listPartsResult.getHttpCode() >= 300){
                Log.w("XIAO",listPartsResult.printError());
            }else {
                Log.w("XIAO",listPartsResult.printBody());
            }
            resultHelper.cosXmlResult = listPartsResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }

}

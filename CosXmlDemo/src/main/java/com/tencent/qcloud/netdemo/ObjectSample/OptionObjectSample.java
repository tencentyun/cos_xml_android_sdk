package com.tencent.qcloud.netdemo.ObjectSample;

import android.util.Log;

import com.tencent.cos.xml.model.object.OptionObjectRequest;
import com.tencent.cos.xml.model.object.OptionObjectResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

import java.util.List;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class OptionObjectSample {
    OptionObjectRequest optionObjectRequest;
    QServiceCfg qServiceCfg;

    public OptionObjectSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        optionObjectRequest = new OptionObjectRequest();
        optionObjectRequest.setBucket(qServiceCfg.bucket);
        optionObjectRequest.setCosPath("/test1.jpg");
        optionObjectRequest.setOrigin("http://www.qcloud.com");
        optionObjectRequest.setAccessControlMethod("get");
        optionObjectRequest.setAccessControlHeaders("host");
        optionObjectRequest.setSign(600,null,null);
        try {
            OptionObjectResult optionObjectResult =
                     qServiceCfg.cosXmlService.optionObject(optionObjectRequest);
            Log.w("XIAO",optionObjectResult.printHeaders());
            if(optionObjectResult.getHttpCode() >= 300){
                Log.w("XIAO",optionObjectResult.printError());
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                List<String> accessControlAllowMethods = optionObjectResult.getAccessControlAllowMethods();
                List<String> accessControlAllowHeaders = optionObjectResult.getAccessControlAllowHeaders();
                List<String> accessControlExposeHeaders = optionObjectResult.getAccessControlExposeHeaders();
                if(accessControlAllowHeaders != null){
                    int size = accessControlAllowHeaders.size();
                    for(int i = 0; i < size -1; ++ i){
                        stringBuilder.append(accessControlAllowHeaders.get(i)).append(",");
                    }
                    stringBuilder.append(accessControlAllowHeaders.get(size -1)).append("\n");
                }
                if(accessControlAllowMethods != null){
                    int size = accessControlAllowMethods.size();
                    for(int i = 0; i < size -1; ++ i){
                        stringBuilder.append(accessControlAllowMethods.get(i)).append(",");
                    }
                    stringBuilder.append(accessControlAllowMethods.get(size -1)).append("\n");
                }
                if(accessControlExposeHeaders != null){
                    int size = accessControlExposeHeaders.size();
                    for(int i = 0; i < size -1; ++ i){
                        stringBuilder.append(accessControlExposeHeaders.get(i)).append(",");
                    }
                    stringBuilder.append(accessControlExposeHeaders.get(size -1)).append("\n");
                }
                Log.w("XIAO", stringBuilder.toString());
            }
            resultHelper.cosXmlResult = optionObjectResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

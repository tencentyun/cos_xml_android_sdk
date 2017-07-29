package com.tencent.qcloud.netdemo.ObjectSample;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.tencent.cos.xml.common.Permission;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.CosXmlResultListener;
import com.tencent.cos.xml.model.object.PutObjectACLRequest;
import com.tencent.cos.xml.model.object.PutObjectACLResult;
import com.tencent.cos.xml.sign.CosXmlSignatureSourceSerializer;
import com.tencent.qcloud.netdemo.ResultActivity;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class PutObjectACLSample {
    PutObjectACLRequest putObjectACLRequest;
    QServiceCfg qServiceCfg;

    public PutObjectACLSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        putObjectACLRequest = new PutObjectACLRequest();
        putObjectACLRequest.setBucket(qServiceCfg.bucket);
        putObjectACLRequest.setCosPath("/test1.jpg");
        putObjectACLRequest.setXCOSACL("public-read");
        List<String> readIdList = new ArrayList<>();
        readIdList.add("uin/2779643970:uin/2779643970");
        readIdList.add("uin/2779643970:uin/151453739");
        putObjectACLRequest.setXCOSGrantReadWithUIN(readIdList);
        List<String> writeIdList = new ArrayList<>();
        writeIdList.add("uin/2779643970:uin/2779643970");
        writeIdList.add("uin/2779643970:uin/151453739");
        putObjectACLRequest.setXCOSGrantWriteWithUIN(writeIdList);
        Set<String> header = new HashSet<>();
        header.add("content-length");
        header.add("content-type");
        header.add("date");
        putObjectACLRequest.setSign(600,null,null);
        try {
            PutObjectACLResult putObjectACLResult =
                    qServiceCfg.cosXmlService.putObjectACL(putObjectACLRequest);
            Log.w("XIAO",putObjectACLResult.printHeaders());
            if(putObjectACLResult.getHttpCode() >= 300){
                Log.w("XIAO",putObjectACLResult.printError());
            }
            resultHelper.cosXmlResult = putObjectACLResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }

    /**
     *
     * 采用异步回调操作
     *
     */
    public void startAsync(final Activity activity){
        putObjectACLRequest = new PutObjectACLRequest();
        putObjectACLRequest.setBucket(qServiceCfg.bucket);
        putObjectACLRequest.setCosPath("/test1.jpg");
        putObjectACLRequest.setXCOSACL("public-read");
        List<String> readIdList = new ArrayList<>();
        readIdList.add("uin/2779643970:uin/2779643970");
        readIdList.add("uin/2779643970:uin/151453739");
        putObjectACLRequest.setXCOSGrantReadWithUIN(readIdList);
        List<String> writeIdList = new ArrayList<>();
        writeIdList.add("uin/2779643970:uin/2779643970");
        writeIdList.add("uin/2779643970:uin/151453739");
        putObjectACLRequest.setXCOSGrantWriteWithUIN(writeIdList);
        Set<String> header = new HashSet<>();
        header.add("content-length");
        header.add("content-type");
        header.add("date");
        putObjectACLRequest.setSign(600,null,null);
        qServiceCfg.cosXmlService.putObjectACLAsync(putObjectACLRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(cosXmlResult.printHeaders())
                        .append(cosXmlResult.printBody());
                Log.w("XIAO", "success = " + stringBuilder.toString());
                show(activity, stringBuilder.toString());
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(cosXmlResult.printHeaders())
                        .append(cosXmlResult.printError());
                Log.w("XIAO", "failed = " + stringBuilder.toString());
                show(activity, stringBuilder.toString());
            }
        });
    }

    private void show(Activity activity, String message){
        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra("RESULT", message);
        activity.startActivity(intent);
    }
}

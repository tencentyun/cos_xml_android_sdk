package com.tencent.qcloud.netdemo.BucketSample;

import android.util.Log;

import com.tencent.cos.xml.common.Permission;
import com.tencent.cos.xml.model.bucket.PutBucketACLRequest;
import com.tencent.cos.xml.model.bucket.PutBucketACLResult;
import com.tencent.qcloud.netdemo.ResultHelper;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.exception.QCloudException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by bradyxiao on 2017/6/1.
 * author bradyxiao
 */
public class PutBucketACLSample {
    PutBucketACLRequest putBucketACLRequest;
    QServiceCfg qServiceCfg;

    public PutBucketACLSample(QServiceCfg qServiceCfg){
        this.qServiceCfg = qServiceCfg;
    }

    public ResultHelper start(){
        ResultHelper resultHelper = new ResultHelper();
        putBucketACLRequest = new PutBucketACLRequest();
        putBucketACLRequest.setBucket(qServiceCfg.bucket);
        putBucketACLRequest.setXCOSACL("public-read");
        List<String> readIdList = new ArrayList<>();
        readIdList.add("uin/2779643970:uin/2779643970");
        readIdList.add("uin/2779643970:uin/151453739");
        putBucketACLRequest.setXCOSGrantReadWithUIN(readIdList);
        List<String> writeIdList = new ArrayList<>();
        writeIdList.add("uin/2779643970:uin/2779643970");
        writeIdList.add("uin/2779643970:uin/151453739");
        putBucketACLRequest.setXCOSGrantWriteWithUIN(writeIdList);
        Set<String> headerSet = new HashSet<>();
        headerSet.add("x-cos-acl");
        headerSet.add("x-cos-grant-read");
        headerSet.add("x-cos-grant-write");
        putBucketACLRequest.setSign(600,null,null);
        try {
            PutBucketACLResult putBucketACLResult =
                     qServiceCfg.cosXmlService.putBucketACL(putBucketACLRequest);
            Log.w("XIAO",putBucketACLResult.printHeaders());
            if(putBucketACLResult.getHttpCode() >= 300){
                Log.w("XIAO",putBucketACLResult.printError());
            }
            resultHelper.cosXmlResult = putBucketACLResult;
            return resultHelper;
        } catch (QCloudException e) {
            Log.w("XIAO","exception =" + e.getExceptionType() + "; " + e.getDetailMessage());
            resultHelper.exception = e;
            return resultHelper;
        }
    }
}

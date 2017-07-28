package com.tencent.qcloud.netdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.CosXmlResultListener;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.qcloud.netdemo.common.QServiceCfg;
import com.tencent.qcloud.network.QCloudProgressListener;
import com.tencent.qcloud.network.QCloudRequest;
import com.tencent.qcloud.network.QCloudResult;
import com.tencent.qcloud.network.QCloudResultListener;
import com.tencent.qcloud.network.exception.QCloudException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button serviceBtn;
    Button bucketBtn;
    Button objectBtn;
    Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck== PackageManager.PERMISSION_GRANTED) {

        } else if (permissionCheck <= PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        serviceBtn = (Button)findViewById(R.id.serviceTest);
        objectBtn = (Button)findViewById(R.id.objectTest);
        bucketBtn = (Button)findViewById(R.id.bucketTest);
        exitBtn = (Button)findViewById(R.id.kill);
        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ServiceDemoActivity.class);
                startActivity(intent);
            }
        });
        objectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ObjectDemoActivity.class);
                startActivity(intent);
            }
        });
        bucketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BucketDemoActivity.class);
                startActivity(intent);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // android.os.Process.killProcess(Process.myPid());
                putObjectAync();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.w("XIAO","requestCode =" + requestCode + "; permission =" + permissions[1] + "; grantResult =" + grantResults[0]);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void putObjectAync(){
        QServiceCfg qServiceCfg = new QServiceCfg(this);
        PutObjectRequest putObjectRequest;
        putObjectRequest = new PutObjectRequest();
        putObjectRequest.setBucket(qServiceCfg.bucket);
        String cosPath = "/gradle for android.pdf";
        putObjectRequest.setCosPath(cosPath);
        putObjectRequest.setSrcPath(Environment.getExternalStorageDirectory().getPath() + "/gradle for android.pdf");
        //putObjectRequest.setXCOSContentSha1(SHA1Utils.getSHA1FromPath(Environment.getExternalStorageDirectory().getPath() + "/test1.jpg"));

        putObjectRequest.setProgressListener(new QCloudProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                float result = (float) (progress * 100.0/max);
                Log.w("XIAO","progress =" + (long)result + "%"  + " ------------" + progress + "/" + max);
            }
        });
        putObjectRequest.setSign(600,null,null);

        putObjectRequest.setXCOSACL("public-read");

        List<String> readIdList = new ArrayList<>();
        readIdList.add("uin/2779643970:uin/2779643970");
        readIdList.add("uin/2779643970:uin/151453739");
        putObjectRequest.setXCOSGrantReadWithUIN(readIdList);

        List<String> writeIdList = new ArrayList<>();
        writeIdList.add("uin/2779643970:uin/2779643970");
        writeIdList.add("uin/2779643970:uin/151453739");
        putObjectRequest.setXCOSGrantWriteWithUIN(writeIdList);

        qServiceCfg.cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                Log.w("XIAO","success =" + result.printHeaders() + "|" + result.printBody());
                Log.w("XIAO","accessUrl =" + result.accessUrl);
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlResult result) {
                Log.w("XIAO","failed =" + result.printHeaders() + "|" + result.printError());
            }
        });

    }

}

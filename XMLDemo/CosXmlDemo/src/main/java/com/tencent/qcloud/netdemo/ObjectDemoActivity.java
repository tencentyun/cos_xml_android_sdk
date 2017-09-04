package com.tencent.qcloud.netdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.qcloud.netdemo.ObjectSample.AbortMultiUploadSample;
import com.tencent.qcloud.netdemo.ObjectSample.AppendObjectSample;
import com.tencent.qcloud.netdemo.ObjectSample.CompleteMultiUploadSample;
import com.tencent.qcloud.netdemo.ObjectSample.DeleteMultiObjectSample;
import com.tencent.qcloud.netdemo.ObjectSample.DeleteObjectSample;
import com.tencent.qcloud.netdemo.ObjectSample.GetObjectACLSample;
import com.tencent.qcloud.netdemo.ObjectSample.GetObjectSample;
import com.tencent.qcloud.netdemo.ObjectSample.HeadObjectSample;
import com.tencent.qcloud.netdemo.ObjectSample.InitMultipartUploadSample;
import com.tencent.qcloud.netdemo.ObjectSample.ListPartsSample;
import com.tencent.qcloud.netdemo.ObjectSample.MultipartUploadHelperSample;
import com.tencent.qcloud.netdemo.ObjectSample.OptionObjectSample;
import com.tencent.qcloud.netdemo.ObjectSample.PutObjectACLSample;
import com.tencent.qcloud.netdemo.ObjectSample.PutObjectSample;
import com.tencent.qcloud.netdemo.ObjectSample.UploadPartSample;
import com.tencent.qcloud.netdemo.common.QServiceCfg;

public class ObjectDemoActivity extends AppCompatActivity implements View.OnClickListener{

    TextView backText;
    Button appendObject;
    Button getObject;
    Button getObjectACL;
    Button putObject;
    Button putObjectACL;
    Button deleteObject;
    Button deleteMultipleObject;
    Button headObject;
    Button optionsObject;
    Button initiateMultipartUpload;
    Button uploadPart;
    Button listParts;
    Button completeMultipartUpload;
    Button abortMultipartUpload;
    Button multipartHelper;
    QServiceCfg qServiceCfg;
    ProgressDialog progressDialog;
    private Handler mainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    progressDialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtras(msg.getData());
                    intent.setClass(ObjectDemoActivity.this,ResultActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    progressDialog.dismiss();
                    Toast.makeText(ObjectDemoActivity.this,"请确定选择了操作",Toast.LENGTH_SHORT).show();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_demo);
        backText = (TextView)findViewById(R.id.back);

        qServiceCfg = new QServiceCfg(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("运行中......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        backText.setOnClickListener(this);

        appendObject = (Button)findViewById(R.id.appendObject);
        getObject = (Button)findViewById(R.id.getObject);
        getObjectACL = (Button)findViewById(R.id.getObjectACL);
        putObject = (Button)findViewById(R.id.putObject);
        putObjectACL = (Button)findViewById(R.id.putObjectACL);
        deleteObject = (Button)findViewById(R.id.deleteObject);
        deleteMultipleObject = (Button)findViewById(R.id.deleteMultipleObject);
        headObject = (Button)findViewById(R.id.headObject);
        optionsObject = (Button)findViewById(R.id.optionsObject);
        initiateMultipartUpload = (Button)findViewById(R.id.initiateMultipartUpload);
        uploadPart = (Button)findViewById(R.id.uploadPart);
        listParts = (Button)findViewById(R.id.listParts);
        completeMultipartUpload = (Button)findViewById(R.id.completeMultipartUpload);
        abortMultipartUpload = (Button)findViewById(R.id.abortMultipartUpload);
        multipartHelper = (Button)findViewById(R.id.multipartHelper);


        appendObject.setOnClickListener(this);
        getObject.setOnClickListener(this);
        getObjectACL.setOnClickListener(this);
        putObject.setOnClickListener(this);
        putObjectACL.setOnClickListener(this);
        deleteObject.setOnClickListener(this);
        deleteMultipleObject.setOnClickListener(this);
        headObject.setOnClickListener(this);
        optionsObject.setOnClickListener(this);
        initiateMultipartUpload.setOnClickListener(this);
        uploadPart.setOnClickListener(this);
        listParts.setOnClickListener(this);
        completeMultipartUpload.setOnClickListener(this);
        abortMultipartUpload.setOnClickListener(this);
        multipartHelper.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.back){
            finish();
        }else{
            start(id);
            //startAsync(id);
        }
    }

    public void start(final int id) {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultHelper result = null;
                switch (id) {
                    case R.id.appendObject:
                        AppendObjectSample appendObjectSample = new AppendObjectSample(qServiceCfg);
                        result = appendObjectSample.start();
                        break;
                    case R.id.getObject:
                        GetObjectSample getObjectSample = new GetObjectSample(qServiceCfg);
                        result =getObjectSample.start();
                        break;
                    case R.id.getObjectACL:
                        GetObjectACLSample getObjectACLSample = new GetObjectACLSample(qServiceCfg);
                        result = getObjectACLSample.start();
                        break;
                    case R.id.putObject:
                        PutObjectSample putObjectSample = new PutObjectSample(qServiceCfg);
                        result = putObjectSample.start();
                        break;
                    case R.id.putObjectACL:
                        PutObjectACLSample putObjectACLSample = new PutObjectACLSample(qServiceCfg);
                        result = putObjectACLSample.start();
                        break;
                    case R.id.deleteObject:
                        DeleteObjectSample deleteObjectSample = new DeleteObjectSample(qServiceCfg);
                        result = deleteObjectSample.start();
                        break;
                    case R.id.deleteMultipleObject:
                        DeleteMultiObjectSample deleteMultiObjectSample = new DeleteMultiObjectSample(qServiceCfg);
                        result = deleteMultiObjectSample.start();
                        break;
                    case R.id.headObject:
                        HeadObjectSample headObjectSample = new HeadObjectSample(qServiceCfg);
                        result = headObjectSample.start();
                        break;
                    case R.id.optionsObject:
                        OptionObjectSample optionObjectSample = new OptionObjectSample(qServiceCfg);
                        result = optionObjectSample.start();
                        break;
                    case R.id.initiateMultipartUpload:
                        InitMultipartUploadSample initMultipartUploadSample = new InitMultipartUploadSample(qServiceCfg);
                        result = initMultipartUploadSample.start();
                        break;
                    case R.id.uploadPart:
                        UploadPartSample uploadPartSample = new UploadPartSample(qServiceCfg);
                        result = uploadPartSample.start();
                        break;
                    case R.id.listParts:
                        ListPartsSample listPartsSample = new ListPartsSample(qServiceCfg);
                        result = listPartsSample.start();
                        break;
                    case R.id.completeMultipartUpload:
                        CompleteMultiUploadSample completeMultiUploadSample = new CompleteMultiUploadSample(qServiceCfg);
                        result = completeMultiUploadSample.start();
                        break;
                    case R.id.abortMultipartUpload:
                        AbortMultiUploadSample abortMultiUploadSample = new AbortMultiUploadSample(qServiceCfg);
                        result = abortMultiUploadSample.start();
                        break;
                    case R.id.multipartHelper:
                        MultipartUploadHelperSample multipartUploadHelperSample = new MultipartUploadHelperSample(qServiceCfg);
                        result = multipartUploadHelperSample.start();
                        break;
                }
                if(result != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("RESULT",result.showMessage());
                    Message msg = mainHandler.obtainMessage();
                    msg.what = 0;
                    msg.setData(bundle);
                    mainHandler.sendMessage(msg);
                }else{
                    Message msg = mainHandler.obtainMessage();
                    msg.what = 1;
                    mainHandler.sendMessage(msg);
                }
            }
        }).start();
    }
    private void startAsync(int id){
        switch (id) {
            case R.id.appendObject:
                AppendObjectSample appendObjectSample = new AppendObjectSample(qServiceCfg);
                appendObjectSample.startAsync(this);
                break;
            case R.id.getObject:
                GetObjectSample getObjectSample = new GetObjectSample(qServiceCfg);
                getObjectSample.startAsync(this);
                break;
            case R.id.getObjectACL:
                GetObjectACLSample getObjectACLSample = new GetObjectACLSample(qServiceCfg);
                getObjectACLSample.startAsync(this);
                break;
            case R.id.putObject:
                PutObjectSample putObjectSample = new PutObjectSample(qServiceCfg);
                putObjectSample.startAsync(this);
                break;
            case R.id.putObjectACL:
                PutObjectACLSample putObjectACLSample = new PutObjectACLSample(qServiceCfg);
                putObjectACLSample.startAsync(this);
                break;
            case R.id.deleteObject:
                DeleteObjectSample deleteObjectSample = new DeleteObjectSample(qServiceCfg);
                deleteObjectSample.startAsync(this);
                break;
            case R.id.deleteMultipleObject:
                DeleteMultiObjectSample deleteMultiObjectSample = new DeleteMultiObjectSample(qServiceCfg);
                deleteMultiObjectSample.startAsync(this);
                break;
            case R.id.headObject:
                HeadObjectSample headObjectSample = new HeadObjectSample(qServiceCfg);
                headObjectSample.startAsync(this);
                break;
            case R.id.optionsObject:
                OptionObjectSample optionObjectSample = new OptionObjectSample(qServiceCfg);
                optionObjectSample.startAsync(this);
                break;
            case R.id.initiateMultipartUpload:
                InitMultipartUploadSample initMultipartUploadSample = new InitMultipartUploadSample(qServiceCfg);
                initMultipartUploadSample.startAsync(this);
                break;
            case R.id.uploadPart:
                UploadPartSample uploadPartSample = new UploadPartSample(qServiceCfg);
                uploadPartSample.startAsync(this);
                break;
            case R.id.listParts:
                ListPartsSample listPartsSample = new ListPartsSample(qServiceCfg);
                listPartsSample.startAsync(this);
                break;
            case R.id.completeMultipartUpload:
                CompleteMultiUploadSample completeMultiUploadSample = new CompleteMultiUploadSample(qServiceCfg);
                completeMultiUploadSample.startAsync(this);
                break;
            case R.id.abortMultipartUpload:
                AbortMultiUploadSample abortMultiUploadSample = new AbortMultiUploadSample(qServiceCfg);
                abortMultiUploadSample.startAsync(this);
                break;
        }
    }

}

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

import com.tencent.qcloud.netdemo.BucketSample.DeleteBucketCORSSample;
import com.tencent.qcloud.netdemo.BucketSample.DeleteBucketLifecycleSample;
import com.tencent.qcloud.netdemo.BucketSample.DeleteBucketSample;
import com.tencent.qcloud.netdemo.BucketSample.DeleteBucketTaggingSample;
import com.tencent.qcloud.netdemo.BucketSample.GetBucketACLSample;
import com.tencent.qcloud.netdemo.BucketSample.GetBucketCORSSample;
import com.tencent.qcloud.netdemo.BucketSample.GetBucketLifecycleSample;
import com.tencent.qcloud.netdemo.BucketSample.GetBucketLocationSample;
import com.tencent.qcloud.netdemo.BucketSample.GetBucketSample;
import com.tencent.qcloud.netdemo.BucketSample.GetBucketTaggingSample;
import com.tencent.qcloud.netdemo.BucketSample.HeadBucketSample;
import com.tencent.qcloud.netdemo.BucketSample.ListMultiUploadsSample;
import com.tencent.qcloud.netdemo.BucketSample.PutBucketACLSample;
import com.tencent.qcloud.netdemo.BucketSample.PutBucketCORSSample;
import com.tencent.qcloud.netdemo.BucketSample.PutBucketLifecycleSample;
import com.tencent.qcloud.netdemo.BucketSample.PutBucketSample;
import com.tencent.qcloud.netdemo.BucketSample.PutBucketTaggingSample;
import com.tencent.qcloud.netdemo.common.QServiceCfg;

public class BucketDemoActivity extends AppCompatActivity implements View.OnClickListener{
    TextView backText;
    Button getBucket;
    Button getBucketACL;
    Button getBucketCORS;
    Button getBucketLocation;
    Button getBucketLifecycle;
    Button getBucketTagging;
    Button putBucket;
    Button putBucketACL;
    Button putBucketCORS;
    Button putBucketLifecycle;
    Button putBucketTagging;
    Button deleteBucket;
    Button deleteBucketCORS;
    Button deleteBucketLifecycle;
    Button deleteBucketTagging;
    Button headBucket;
    Button ListMultiUploads;
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
                    intent.setClass(BucketDemoActivity.this,ResultActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    progressDialog.dismiss();
                    Toast.makeText(BucketDemoActivity.this,"请确定选择了操作",Toast.LENGTH_SHORT).show();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_demo);

        backText = (TextView)findViewById(R.id.back);

        qServiceCfg = new QServiceCfg(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("运行中......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        backText.setOnClickListener(this);

        getBucket = (Button)findViewById(R.id.getBucket);
        getBucketACL = (Button)findViewById(R.id.getBucketACL);
        getBucketCORS = (Button)findViewById(R.id.getBucketCORS);
        getBucketLocation = (Button)findViewById(R.id.getBucketLocation);
        getBucketLifecycle = (Button)findViewById(R.id.getBucketLifecycle);
        getBucketTagging = (Button)findViewById(R.id.getBucketTagging);
        putBucket = (Button)findViewById(R.id.putBucket);
        putBucketACL = (Button)findViewById(R.id.putBucketACL);
        putBucketCORS = (Button)findViewById(R.id.putBucketCORS);
        putBucketLifecycle = (Button)findViewById(R.id.putBucketLifecycle);
        putBucketTagging = (Button)findViewById(R.id.putBucketTagging);
        deleteBucket = (Button)findViewById(R.id.deleteBucket);
        deleteBucketCORS = (Button)findViewById(R.id.deleteBucketCORS);
        deleteBucketLifecycle = (Button)findViewById(R.id.deleteBucketLifecycle);
        deleteBucketTagging = (Button)findViewById(R.id.deleteBucketTagging);
        headBucket = (Button)findViewById(R.id.headBucket);
        ListMultiUploads = (Button)findViewById(R.id.ListMultiUploads);

        getBucket.setOnClickListener(this);
        getBucketACL.setOnClickListener(this);
        getBucketCORS.setOnClickListener(this);
        getBucketLocation.setOnClickListener(this);
        getBucketLifecycle.setOnClickListener(this);
        getBucketTagging.setOnClickListener(this);
        putBucket.setOnClickListener(this);
        putBucketACL.setOnClickListener(this);
        putBucketCORS.setOnClickListener(this);
        putBucketLifecycle.setOnClickListener(this);
        putBucketTagging.setOnClickListener(this);
        deleteBucket.setOnClickListener(this);
        deleteBucketCORS.setOnClickListener(this);
        deleteBucketLifecycle.setOnClickListener(this);
        deleteBucketTagging.setOnClickListener(this);
        headBucket.setOnClickListener(this);
        ListMultiUploads.setOnClickListener(this);

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
                    case R.id.getBucket:
                        GetBucketSample getBucketSample = new GetBucketSample(qServiceCfg);
                        result = getBucketSample.start();
                        break;
                    case R.id.getBucketACL:
                        GetBucketACLSample getBucketACLSample = new GetBucketACLSample(qServiceCfg);
                        result =getBucketACLSample.start();
                        break;
                    case R.id.getBucketCORS:
                        GetBucketCORSSample getBucketCORSSample = new GetBucketCORSSample(qServiceCfg);
                        result = getBucketCORSSample.start();
                        break;
                    case R.id.getBucketLocation:
                        GetBucketLocationSample getBucketLocationSample = new GetBucketLocationSample(qServiceCfg);
                        result = getBucketLocationSample.start();
                        break;
                    case R.id.getBucketLifecycle:
                        GetBucketLifecycleSample getBucketLifecycleSample = new GetBucketLifecycleSample(qServiceCfg);
                        result = getBucketLifecycleSample.start();
                        break;
                    case R.id.getBucketTagging:
                        GetBucketTaggingSample getBucketTaggingSample = new GetBucketTaggingSample(qServiceCfg);
                        result = getBucketTaggingSample.start();
                        break;
                    case R.id.putBucket:
                        PutBucketSample putBucketSample = new PutBucketSample(qServiceCfg);
                        result = putBucketSample.start();
                        break;
                    case R.id.putBucketACL:
                        PutBucketACLSample putBucketACLSample = new PutBucketACLSample(qServiceCfg);
                        result = putBucketACLSample.start();
                        break;
                    case R.id.putBucketCORS:
                        PutBucketCORSSample putBucketCORSSample = new PutBucketCORSSample(qServiceCfg);
                        result = putBucketCORSSample.start();
                        break;
                    case R.id.putBucketLifecycle:
                        PutBucketLifecycleSample putBucketLifecycleSample = new PutBucketLifecycleSample(qServiceCfg);
                        result = putBucketLifecycleSample.start();
                        break;
                    case R.id.putBucketTagging:
                        PutBucketTaggingSample putBucketTaggingSample = new PutBucketTaggingSample(qServiceCfg);
                        result = putBucketTaggingSample.start();
                        break;
                    case R.id.deleteBucket:
                        DeleteBucketSample deleteBucketSample = new DeleteBucketSample(qServiceCfg);
                        result = deleteBucketSample.start();
                        break;
                    case R.id.deleteBucketCORS:
                        DeleteBucketCORSSample deleteBucketCORSSample = new DeleteBucketCORSSample(qServiceCfg);
                        result = deleteBucketCORSSample.start();
                        break;
                    case R.id.deleteBucketLifecycle:
                        DeleteBucketLifecycleSample deleteBucketLifecycleSample = new DeleteBucketLifecycleSample(qServiceCfg);
                        result = deleteBucketLifecycleSample.start();
                        break;
                    case R.id.deleteBucketTagging:
                        DeleteBucketTaggingSample deleteBucketTaggingSample = new DeleteBucketTaggingSample(qServiceCfg);
                        result = deleteBucketTaggingSample.start();
                        break;
                    case R.id.headBucket:
                        HeadBucketSample headBucketSample = new HeadBucketSample(qServiceCfg);
                        result = headBucketSample.start();
                        break;
                    case R.id.ListMultiUploads:
                        ListMultiUploadsSample listMultiUploadsSample = new ListMultiUploadsSample(qServiceCfg);
                        result = listMultiUploadsSample.start();
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

    private void startAsync(int id) {
        switch (id) {
            case R.id.getBucket:
                GetBucketSample getBucketSample = new GetBucketSample(qServiceCfg);
                getBucketSample.startAsync(this);
                break;
            case R.id.getBucketACL:
                GetBucketACLSample getBucketACLSample = new GetBucketACLSample(qServiceCfg);
                getBucketACLSample.startAsync(this);
                break;
            case R.id.getBucketCORS:
                GetBucketCORSSample getBucketCORSSample = new GetBucketCORSSample(qServiceCfg);
                getBucketCORSSample.startAsync(this);
                break;
            case R.id.getBucketLocation:
                GetBucketLocationSample getBucketLocationSample = new GetBucketLocationSample(qServiceCfg);
                getBucketLocationSample.startAsync(this);
                break;
            case R.id.getBucketLifecycle:
                GetBucketLifecycleSample getBucketLifecycleSample = new GetBucketLifecycleSample(qServiceCfg);
                getBucketLifecycleSample.startAsync(this);
                break;
            case R.id.getBucketTagging:
                GetBucketTaggingSample getBucketTaggingSample = new GetBucketTaggingSample(qServiceCfg);
                getBucketTaggingSample.startAsync(this);
                break;
            case R.id.putBucket:
                PutBucketSample putBucketSample = new PutBucketSample(qServiceCfg);
                putBucketSample.startAsync(this);
                break;
            case R.id.putBucketACL:
                PutBucketACLSample putBucketACLSample = new PutBucketACLSample(qServiceCfg);
                putBucketACLSample.startAsync(this);
                break;
            case R.id.putBucketCORS:
                PutBucketCORSSample putBucketCORSSample = new PutBucketCORSSample(qServiceCfg);
                putBucketCORSSample.startAsync(this);
                break;
            case R.id.putBucketLifecycle:
                PutBucketLifecycleSample putBucketLifecycleSample = new PutBucketLifecycleSample(qServiceCfg);
                putBucketLifecycleSample.startAsync(this);
                break;
            case R.id.putBucketTagging:
                PutBucketTaggingSample putBucketTaggingSample = new PutBucketTaggingSample(qServiceCfg);
                putBucketTaggingSample.startAsync(this);
                break;
            case R.id.deleteBucket:
                DeleteBucketSample deleteBucketSample = new DeleteBucketSample(qServiceCfg);
                deleteBucketSample.startAsync(this);
                break;
            case R.id.deleteBucketCORS:
                DeleteBucketCORSSample deleteBucketCORSSample = new DeleteBucketCORSSample(qServiceCfg);
                deleteBucketCORSSample.startAsync(this);
                break;
            case R.id.deleteBucketLifecycle:
                DeleteBucketLifecycleSample deleteBucketLifecycleSample = new DeleteBucketLifecycleSample(qServiceCfg);
                deleteBucketLifecycleSample.startAsync(this);
                break;
            case R.id.deleteBucketTagging:
                DeleteBucketTaggingSample deleteBucketTaggingSample = new DeleteBucketTaggingSample(qServiceCfg);
                deleteBucketTaggingSample.startAsync(this);
                break;
            case R.id.headBucket:
                HeadBucketSample headBucketSample = new HeadBucketSample(qServiceCfg);
                headBucketSample.startAsync(this);
                break;
            case R.id.ListMultiUploads:
                ListMultiUploadsSample listMultiUploadsSample = new ListMultiUploadsSample(qServiceCfg);
                listMultiUploadsSample.startAsync(this);
                break;
        }
    }

}

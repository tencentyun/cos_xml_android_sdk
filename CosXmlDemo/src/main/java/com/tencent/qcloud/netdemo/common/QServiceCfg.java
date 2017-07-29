package com.tencent.qcloud.netdemo.common;

import android.content.Context;
import android.util.Log;

import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.sign.CosXmlCredentialProvider;
import com.tencent.cos.xml.sign.CosXmlSignaturePair;
import com.tencent.qcloud.network.exception.QCloudException;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by bradyxiao on 2017/5/31.
 * author bradyxiao
 */
public class QServiceCfg {
    public String bucket = "bucket 名称";
    public String appid = "appid";
    public String region = "bucket所属地域";
    public CosXmlService cosXmlService;

    public QServiceCfg(Context context){
        CosXmlServiceConfig cosXmlServiceConfig = new CosXmlServiceConfig(appid,region);
        cosXmlServiceConfig.setSocketTimeout(450000);
        cosXmlService = new CosXmlService(context,cosXmlServiceConfig,
                new MyCredentialProvider("输入secretId","输入secretkey",600));
    }

    /***
     * 根据需要，获取签名：
     *  1）继承 CosXmlCredentialProvider；
     *  2）@Override signaturePair()
     *  3）在 signaturePair()方法中根据环境，自定义signKey的获取形式：
     *  如 最简单的方法：直接提供 secretKey + signKeyTime,计算 secretKeyToSignKey（secretKey，signKeyTime）
     *  或者为了安全考虑：将计算 secretKeyToSignKey（secretKey，signKeyTime）放在服务端实现，然后下发给sdk
     *  参考 SafeCredentialProvider
     */

    /**直接获取方法*/
    private class MyCredentialProvider extends CosXmlCredentialProvider{
        private String secretKey;
        private long duration;

        public MyCredentialProvider(String secretId, String secretKey, long keyDuration) {
            super(secretId);
            this.secretKey = secretKey;
            this.duration = keyDuration;
        }

        @Override
        public CosXmlSignaturePair signaturePair() throws QCloudException {
            long current = System.currentTimeMillis() / 1000;
            long expired = current + duration;
            String keyTime = current+";"+expired;
            return new CosXmlSignaturePair(secretKeyToSignKey(secretKey, keyTime), keyTime);
        }

        private String secretKeyToSignKey(String secretKey, String keyTime) {
            String signKey = null;
            try {
                if (secretKey == null) {
                    throw new IllegalArgumentException("secretKey is null");
                }
                if (keyTime == null) {
                    throw new IllegalArgumentException("qKeyTime is null");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            try {
                byte[] byteKey = secretKey.getBytes("utf-8");
                SecretKey hmacKey = new SecretKeySpec(byteKey, "HmacSHA1");
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(hmacKey);
                signKey = StringUtils.toHexString(mac.doFinal(keyTime.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            return signKey;
        }
    }

    /** 安全获取方法 */
    private class SafeCredentialProvider extends CosXmlCredentialProvider{
        private String url;
        public SafeCredentialProvider(String secretId, String url) {
            super(secretId);
            this.url = url;
        }
        @Override
        public CosXmlSignaturePair signaturePair() throws QCloudException {
            String signKey = null;
            String keyTime = null;
            try {
                URL signKeyUrl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) signKeyUrl.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = bufferedReader.readLine();
                if(line == null){
                    Log.w("SafeCredentialProvider","failed to get signKey");
                    return  null;
                }
                JSONObject json = new JSONObject(line);
                if(json.has("signKey")){
                    signKey = json.getString("signKey");
                }
                if(json.has("keyTime")){
                    keyTime = json.getString("keyTime");
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new CosXmlSignaturePair(signKey, keyTime);
        }

    }

}

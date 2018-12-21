## 仓库已弃用
新版 SDK 已迁移到 [cos-xml-sdk](https://github.com/tencentyun/qcloud-sdk-android)。

**COS XML ANDROID SDK 导入的第一种方式：**

工程中导入下列 jar 包：
- cos-xml-android-sdk-1.1.jar

- qcloud-network-android-sdk-1.1.jar
 
- okhttp-3.8.1.jar

- okio-1.13.0.jar
 
- slf4j-android-1.6.1-RC1.jar

- xstream-1.4.7.jar


**COS XML ANDROID SDK 导入的第二种方式：**

在build.gradle中，添加如下依赖:

```

dependencies { 

	compile 'com.tencent.cos:cosxml:1+@aar'
	compile 'com.tencent.qcloud:network:1.1'
	compile 'com.squareup.okhttp3:okhttp:3.8.1'
	compile 'org.slf4j:slf4j-android:1.6.1-RC1'
	compile ('com.thoughtworks.xstream:xstream:1.4.7') {
	exclude group: 'xmlpull', module: 'xmlpull'
	}

}
```
**更多示例可参考Demo**

**常用的类**

```
//签名获取类

CosXmlCredentialProvider.
SmartSessionCredentialProvider

//sdk配置设置类

CosXmlServiceConfig.

//cos 服务类

CosXmlService.
```

**CosXmlService 常用的API接口：**

```
//简单上传文件

PutObjectResult putObject(PutObjectRequest request) throws QCloudException;//（同步操作）

void putObjectAsync(PutObjectRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//初始化分片上传

InitMultipartUploadResult initMultipartUpload(InitMultipartUploadRequest request) throws QCloudException;//（同步操作）

void initMultipartUploadAsync(InitMultipartUploadRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//上传分片块

UploadPartResult uploadPart(UploadPartRequest request) throws QCloudException;//（同步操作）

void uploadPartAsync(UploadPartRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//列出已上传的分片块

ListPartsResult listParts(ListPartsRequest request) throws QCloudException;//（同步操作）

void listPartsAsync(ListPartsRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//完成 分片上传

CompleteMultiUploadResult completeMultiUpload(CompleteMultiUploadRequest request) throws QCloudException;//（同步操作）

void completeMultiUploadAsync(CompleteMultiUploadRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


// 舍弃并删除已上传的块

AbortMultiUploadResult abortMultiUpload(AbortMultiUploadRequest request) throws QCloudException;//（同步操作）

void abortMultiUploadAsync(AbortMultiUploadRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


// 删除单个文件

DeleteObjectResult deleteObject(DeleteObjectRequest request) throws QCloudException;//（同步操作）

void deleteObjectAsync(DeleteObjectRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


// 删除多个文件

DeleteMultiObjectResult deleteMultiObject(DeleteMultiObjectRequest request) throws QCloudException;//（同步操作）

void deleteMultiObjectAsync(DeleteMultiObjectRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//下载文件

GetObjectResult getObject(GetObjectRequest request) throws QCloudException;//（同步操作）

void getObjectAsync(GetObjectRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//授权

PutObjectACLResult putObjectACL(PutObjectACLRequest request) throws QCloudException ;//（同步操作）

void putObjectACLAsync(PutObjectACLRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//查看权限

GetObjectACLResult getObjectACL(GetObjectACLRequest request) throws QCloudException;//（同步操作）

void getObjectACLAsync(GetObjectACLRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）


//文件信息

HeadObjectResult headObject(HeadObjectRequest request) throws QCloudException;//（同步操作）

void headObjectAsync(HeadObjectRequest request, final CosXmlResultListener cosXmlResultListener);//（异步回调操作）
```




## Change Log

#### **cos-xml-android-sdk-1.1.jar**
- 增加CAM鉴权接口 SmartSessionCredentialProvider



## PermissionsHelper

> **项目地址:[https://github.com/didikee/PermissionsHelper](https://github.com/didikee/PermissionsHelper)**

### Gradle
```
dependencies {
    compile 'com.didikee:permissionsHelper:0.1.4'
}
```

> **9组 危险权限,按照你的项目需求申请,不要盲目,切勿贪婪.更不要偷懒复制粘贴**

	<!-- Dangerous Permissions start -->
    <!--PHONE-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.CALL_PHONE"/>
    <uses-permission android:name="android.permission.READ_CALL_LOG"/>
    <uses-permission android:name="android.permission.ADD_VOICEMAIL"/>
    <uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
    <uses-permission android:name="android.permission.USE_SIP"/>
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
    <!--CALENDAR-->
    <uses-permission android:name="android.permission.READ_CALENDAR"/>
    <uses-permission android:name="android.permission.WRITE_CALENDAR"/>
    <!--CAMERA-->
    <uses-permission android:name="android.permission.CAMERA"/>
    <!--CONTACTS-->
    <uses-permission android:name="android.permission.READ_CONTACTS"/>
    <uses-permission android:name="android.permission.WRITE_CONTACTS"/>
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    <!--LOCATION-->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <!--MICROPHONE-->
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <!--SENSORS-->
    <uses-permission android:name="android.permission.BODY_SENSORS"/>
    <!--SMS-->
    <uses-permission android:name="android.permission.SEND_SMS"/>
    <uses-permission android:name="android.permission.RECEIVE_SMS"/>
    <uses-permission android:name="android.permission.READ_SMS"/>
    <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>
    <uses-permission android:name="android.permission.RECEIVE_MMS"/>
    <!--STORAGE-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <!-- Dangerous Permissions end -->

> 开始使用=.=
> 第一步: 配置需要的权限
```
// app所需要的全部危险权限
    static final String[] PERMISSIONS = new String[]{
            DangerousPermissions.CALENDAR,
            DangerousPermissions.CAMERA,
            DangerousPermissions.CONTACTS,
            DangerousPermissions.LOCATION,
            DangerousPermissions.MICROPHONE,
            DangerousPermissions.PHONE,
            DangerousPermissions.STORAGE,
            DangerousPermissions.SENSORS,
            DangerousPermissions.SMS
    };
    private PermissionsHelper permissionsHelper;
```

>  第二步: 增加需要的回调设置

```
private void checkPermissions() {
        permissionsHelper = new PermissionsHelper(this,PERMISSIONS);
        if (permissionsHelper.checkAllPermissions(PERMISSIONS)){
            permissionsHelper.onDestroy();
            //do nomarl
        }else {
            //申请权限
            permissionsHelper.startRequestNeedPermissions();
        }
        permissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {


            @Override
            public void onAllNeedPermissionsGranted() {
            //做原先的业务代码
                Log.d("test","onAllNeedPermissionsGranted");
            }

            @Override
            public void onPermissionsDenied() {
            //拒绝了,如何处理?(视情况而定)
                Log.d("test","onPermissionsDenied");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionsHelper.onActivityResult(requestCode, resultCode, data);
    }
```

> NOTICE: 如果出现`You need to use a Theme.AppCompat theme (or descendant) with this activity.`将Activity的主题应用为如下主题.

```
<style name="PermissionTheme" parent="@style/Theme.AppCompat.Light">
        <item name="android:windowNoTitle">true</item>
        <item name="android:fitsSystemWindows">true</item>
        <item name="android:colorBackgroundCacheHint">@null</item>
        <item name="android:windowAnimationStyle">@style/AppAnimation</item>
        <item name="android:textCursorDrawable">@null</item>
    </style>
```

实际配置类似如下:
```
<activity
       android:name="com.fanwe.activity.WelcomeActivity"
       android:theme="@style/PermissionTheme">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
 </activity>
```

> **完整的实例代码如下:**

```
public class WelcomeActivity extends BaseActivity {
    /**
     * 迎导页显示的时间
     */
    private static final long ADVS_DISPLAY_TIME = 3 * 1000;
    private SDTimer mTimer = new SDTimer();

    // app所需要的全部危险权限
    static final String[] PERMISSIONS = new String[]{
            DangerousPermissions.CAMERA,
            DangerousPermissions.CONTACTS,
            DangerousPermissions.PHONE,
            DangerousPermissions.STORAGE,
    };
    private PermissionsHelper permissionsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setmTitleType(TitleType.TITLE_NONE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
        checkPermissions();
    }

    private void init() {
        //....业务代码
        initUserInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
		permissionsHelper.onDestroy();
    }

    private void checkPermissions() {
        permissionsHelper = new PermissionsHelper(this,PERMISSIONS);
        if (permissionsHelper.checkAllPermissions(PERMISSIONS)){
            permissionsHelper.onDestroy();
            //do nomarl
            init();
        }else {
            //申请权限
            permissionsHelper.startRequestNeedPermissions();
        }
        permissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {


            @Override
            public void onAllNeedPermissionsGranted() {
                init();
                Log.d("test","onAllNeedPermissionsGranted");
            }

            @Override
            public void onPermissionsDenied() {
                WelcomeActivity.this.finish();
                Log.d("test","onPermissionsDenied");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionsHelper.onActivityResult(requestCode, resultCode, data);
    }
}

```

### 运行时 申请权限 图示 ###
![http://oahzrw11n.bkt.clouddn.com/device-2016-08-06-144845.png](http://oahzrw11n.bkt.clouddn.com/device-2016-08-06-144845.png)

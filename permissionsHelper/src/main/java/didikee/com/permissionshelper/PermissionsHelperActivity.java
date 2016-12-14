package didikee.com.permissionshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import didikee.com.permissionshelper.info.DialogInfo;
import didikee.com.permissionshelper.permission.DangerousPermissions;

/**
 * Created by didik 
 * Created time 2016/12/13
 * Description:
 * == Dangerous Permissions start  ==
 * == PHONE
 * uses-permission android:name="android.permission.READ_PHONE_STATE"
 * uses-permission android:name="android.permission.CALL_PHONE"
 * uses-permission android:name="android.permission.READ_CALL_LOG"
 * uses-permission android:name="android.permission.ADD_VOICEMAIL"
 * uses-permission android:name="android.permission.WRITE_CALL_LOG"
 * uses-permission android:name="android.permission.USE_SIP"
 * uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"
 * == CALENDAR
 * uses-permission android:name="android.permission.READ_CALENDAR"
 * uses-permission android:name="android.permission.WRITE_CALENDAR"
 * == CAMERA
 * uses-permission android:name="android.permission.CAMERA"
 * == CONTACTS
 * uses-permission android:name="android.permission.READ_CONTACTS"
 * uses-permission android:name="android.permission.WRITE_CONTACTS"
 * uses-permission android:name="android.permission.GET_ACCOUNTS"
 * == LOCATION
 * uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
 * uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"
 * == MICROPHONE
 * uses-permission android:name="android.permission.RECORD_AUDIO"
 * == SENSORS
 * uses-permission android:name="android.permission.BODY_SENSORS"
 * == SMS
 * uses-permission android:name="android.permission.SEND_SMS"
 * uses-permission android:name="android.permission.RECEIVE_SMS"
 * uses-permission android:name="android.permission.READ_SMS"
 * uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"
 * uses-permission android:name="android.permission.RECEIVE_MMS"
 * == STORAGE
 * uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
 * uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
 * == Dangerous Permissions end ==
 */

public abstract class PermissionsHelperActivity extends AppCompatActivity {

    protected List<String> permissions = new ArrayList<>();
    protected PermissionsHelper permissionsHelper;
    private static final String[] PERMISSIONS = new String[]{
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentLayout());
        setDangerousPermissions(permissions);
        startFlow();
    }

    protected abstract void setDangerousPermissions(List<String> permissions);

    @LayoutRes
    protected abstract int setContentLayout();

    protected abstract void startFlow();

    protected DialogInfo setDialogInfo(DialogInfo dialogInfo){
        return dialogInfo;
    }

    protected void onNegativeButtonClick(){
        Log.d("PermissionsHelper","onNegativeButtonClick");
    }

    protected abstract void allPermissionsGranted();

    protected abstract void permissionsDenied();

    protected abstract void shouldNOTShowRequest();

    protected void beforeRequestFinalPermissions(PermissionsHelper helper){
        helper.continueRequestPermissions();
    }
    protected abstract Boolean isFirstTime();

    private void initParams(){
        permissionsHelper.setParams(setDialogInfo(new DialogInfo()));
    }

    /**
     * Core
     */
    protected final void checkPermissions() {
        List<String> finalPermissions = checkParams(permissions);
        if (finalPermissions == null || finalPermissions.size() <= 0) {
            allPermissionsGranted();
            return;
        }
        String[] checkPermissions = new String[finalPermissions.size()];
        checkPermissions = finalPermissions.toArray(checkPermissions);
        for (String checkPermission : checkPermissions) {
            Log.e("test","checkPermission: "+checkPermission);
        }
        permissionsHelper = new PermissionsHelper(this, checkPermissions,isFirstTime());
        permissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper
                .onAllNeedPermissionsGrantedListener() {


            @Override
            public void onAllNeedPermissionsGranted() {
                allPermissionsGranted();
            }

            @Override
            public void onPermissionsDenied() {
                permissionsDenied();
            }

            @Override
            public void hasLockForever() {
                shouldNOTShowRequest();
            }

            @Override
            public void onBeforeRequestFinalPermissions(PermissionsHelper helper) {
                beforeRequestFinalPermissions(helper);
            }
        });

        permissionsHelper.setonNegativeButtonClickListener(new PermissionsHelper.onNegativeButtonClickListener() {


            @Override
            public void negativeButtonClick() {
                onNegativeButtonClick();
            }
        });
        if (permissionsHelper.checkAllPermissions(checkPermissions)) {
            permissionsHelper.onDestroy();
            allPermissionsGranted();//没有需要申请的权限
        } else {
            //申请权限
            permissionsHelper.startRequestNeedPermissions();
            initParams();
        }
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

    /**
     * 检测传入的permission是否是危险权限
     * @param permissions dangerous permissions {@link DangerousPermissions}
     * @return final permissions need to request
     */
    private List<String> checkParams(List<String> permissions) {
        if (permissions == null || permissions.size() <= 0) return null;
        List<String> allPermissions = Arrays.asList(PERMISSIONS);
        List<String> finalPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (allPermissions.contains(permission)) {
                finalPermissions.add(permission);
            }
        }
        return finalPermissions;
    }
}

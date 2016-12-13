package com.didikee.example;

import android.util.Pair;
import android.widget.Toast;

import java.util.List;

import didikee.com.permissionshelper.PermissionsHelperActivity;

public class MainActivity extends PermissionsHelperActivity {

    @Override
    protected void setDangerousPermissions(List<String> permissions) {
//        permissions.add(DangerousPermissions.STORAGE);
//        permissions.add(DangerousPermissions.CALENDAR);
//        permissions.add(DangerousPermissions.PHONE);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void startFlow() {
        //do something
        checkPermissions();
    }

    @Override
    protected boolean showSettingDialog() {
        return true;
    }

    @Override
    protected Pair<String, String> setTitleAndContent() {
        return new Pair<>("权限申请","这些权限是我们app必须的"+"\n"+"你不给就不要用了!");
    }

    @Override
    protected Pair<String, String> setButtonText() {
        return new Pair<>("去设置","取消");
    }

    @Override
    protected void onNegativeButtonClick() {
        super.onNegativeButtonClick();
    }

    @Override
    protected void allPermissionsGranted() {
        Toast.makeText(this, "allPermissionsGranted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void permissionsDenied() {
        Toast.makeText(this, "permissionsDenied", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void shouldNOTShowRequest() {
        Toast.makeText(this, "永远不需要展示", Toast.LENGTH_SHORT).show();
    }
}

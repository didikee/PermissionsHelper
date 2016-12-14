package com.didikee.example;

import android.widget.Toast;

import java.util.List;

import didikee.com.permissionshelper.PermissionsHelperActivity;
import didikee.com.permissionshelper.info.DialogInfo;
import didikee.com.permissionshelper.permission.DangerousPermissions;

public class MainActivity extends PermissionsHelperActivity {

    @Override
    protected void setDangerousPermissions(List<String> permissions) {
        permissions.add(DangerousPermissions.STORAGE);
        permissions.add(DangerousPermissions.CALENDAR);
        permissions.add(DangerousPermissions.PHONE);
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
    protected DialogInfo setDialogInfo(DialogInfo dialogInfo) {
        dialogInfo.setTitle("权限申请");
        dialogInfo.setContent("这些权限是我们app必须的"+"\\n"+"你不给就不要用了!");
        dialogInfo.setPositiveButtonText("去设置");
        dialogInfo.setNegativeButtonText("取消");
        dialogInfo.showDialog(true);
        return dialogInfo;
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
        //{ 1. length =0
        // }
    }

    @Override
    protected Boolean isFirstTime() {
        boolean first = (boolean) SPUtils.getData(this, "first", false);
        if (!first){
            SPUtils.putData(this,"first",true);
        }
        return !first;
    }
}

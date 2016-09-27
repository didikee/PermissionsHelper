package didikee.com.permissionshelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import didikee.com.permissionshelper.permission.PermissionsChecker;

/**
 * Created by didikee on 2016/8/6.
 * 权限检测并获取
 */
public class PermissionsHelper {

    public static final int PERMISSION_REQUEST_CODE = 44;
    public static final int PERMISSION_REQUEST_SETTING = 45;
    private String[] mNeedPermissions;//需要的权限

    private Activity mActivity;
    private PermissionsChecker mChecker;
    private onAllNeedPermissionsGrantedListener mListener;
    private boolean isShowing = false;
    private String mTitle="权限不足";
    private String mContent="需要必须的权限才能正常使用本应用";

    public PermissionsHelper(Activity activity, String[] mNeedPermissions) {
        this.mActivity = activity;
        this.mNeedPermissions = mNeedPermissions;
        mChecker = new PermissionsChecker(mActivity);
    }


    public void onDestroy() {
        mChecker.onDestroy();
        mActivity = null;
        mChecker = null;
        mNeedPermissions = null;
        mListener = null;
    }

    /**
     * 检测是不是所有的权限都有了
     * @param permissions
     * @return true 所有的都有了,否则返回 false
     */
    public boolean checkAllPermissions(String... permissions) {
        return mChecker.checkSelfPermissions(permissions);
    }

    /**
     * 接收来自Activity的权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        isShowing = false;
        if (hasDestroy()) {
            return;
        }
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 &&
                hasAllPermissionsGranted(grantResults)) {
            allPermissionsGranted();
        }else if (!isShowing){
            showSettingPermissionDialog();
        }
    }

    /**
     *
     * @param grantResults
     * @return true 有所有权限
     *         false 还有未申请的权限
     */
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            return false;
        }
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    // 请求权限兼容低版本
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(mActivity, permissions, PERMISSION_REQUEST_CODE);
    }

    // 全部权限均已获取
    private void allPermissionsGranted() {
        if (mListener != null) {
            mListener.onAllNeedPermissionsGranted();
        }
        onDestroy();
    }

    public void startRequestNeedPermissions() {
        if (Build.VERSION.SDK_INT < 23) {
            allPermissionsGranted();
        }else {
            requestNeedPermissions();
        }
    }

    private void requestNeedPermissions(){
        if (mChecker.checkSelfPermissions(mNeedPermissions)) {
            allPermissionsGranted(); // 全部权限都已获取
        } else {
            requestPermissions(mNeedPermissions); // 请求权限
        }
    }

    // 显示缺失权限提示
    private void showSettingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mTitle);
        builder.setMessage(mContent);

        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onPermissionsDenied();
                }
                onDestroy();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 启动app设置界面,用户手动获取权限
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:"+ mActivity.getPackageName()));
                mActivity.startActivityForResult(intent,PERMISSION_REQUEST_SETTING);
            }
        });

        builder.setCancelable(false);

        builder.show();
    }

    public void setPermissionDialogTitle(String title){
        this.mTitle=title;
    }

    public void setPermissionDialogContent(String content){
        this.mContent=content;
    }

    /**
     * 接收来自设置界面回来的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PERMISSION_REQUEST_SETTING) {
            requestNeedPermissions();
        }
    }

    public interface onAllNeedPermissionsGrantedListener {
        void onAllNeedPermissionsGranted();//全部许可了,已经获得了所有权限

        void onPermissionsDenied();//被拒绝了,只要有一个权限被拒绝那么就会调用
    }

    public void setonAllNeedPermissionsGrantedListener(onAllNeedPermissionsGrantedListener
                                                               listener) {
        this.mListener = listener;
    }

    /**
     * 是不是已经销毁了
     * @return
     */
    public boolean hasDestroy() {
        if (mActivity == null || mChecker == null || mNeedPermissions == null) {
            return true;
        }
        return false;
    }
}

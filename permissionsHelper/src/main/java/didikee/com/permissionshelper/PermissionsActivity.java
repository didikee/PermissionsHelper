//package didikee.com.permissionshelper;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import didikee.com.permissionshelper.permission.DangerousPermissions;
//import didikee.com.permissionshelper.PermissionsHelper;
//
//
//public class PermissionsActivity extends AppCompatActivity {
//    // app所需要的全部危险权限
//    static final String[] PERMISSIONS = new String[]{
//            DangerousPermissions.CALENDAR,
//            DangerousPermissions.CAMERA,
//            DangerousPermissions.CONTACTS,
//            DangerousPermissions.LOCATION,
//            DangerousPermissions.MICROPHONE,
//            DangerousPermissions.PHONE,
//            DangerousPermissions.STORAGE,
//            DangerousPermissions.SENSORS,
//            DangerousPermissions.SMS
//    };
//    private PermissionsHelper permissionsHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_permissions);
//        checkPermissions();
//    }
//    private void checkPermissions() {
//        permissionsHelper = new PermissionsHelper(this,PERMISSIONS);
//        if (permissionsHelper.checkAllPermissions(PERMISSIONS)){
//            permissionsHelper.onDestroy();
//            //do nomarl
//        }else {
//            //申请权限
//            permissionsHelper.startRequestNeedPermissions();
//        }
//        permissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {
//
//
//            @Override
//            public void onAllNeedPermissionsGranted() {
//                Log.d("test","onAllNeedPermissionsGranted");
//            }
//
//            @Override
//            public void onPermissionsDenied() {
//                Log.d("test","onPermissionsDenied");
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        permissionsHelper.onActivityResult(requestCode, resultCode, data);
//    }
//}

package didikee.com.permissionshelper.permission;

import android.Manifest;

/**
 * Created by didik on 2016/8/5.
 * Dangerous Permissions 危险权限
 */
public final class DangerousPermissions {
    /**
     * Google doc:
     * If an app requests a dangerous permission listed in its manifest,
     * and the app already has another dangerous permission in the same
     * permission group, the system immediately grants the permission
     * without any interaction with the user. For example, if an app had
     * previously requested and been granted the READ_CONTACTS permission,
     * and it then requests WRITE_CONTACTS, the system immediately grants that permission.
     */

    /*
    *   permission group : PHONE
    * 	READ_PHONE_STATE
	*   CALL_PHONE
	*   READ_CALL_LOG
	*   WRITE_CALL_LOG
	*   ADD_VOICEMAIL
	*   USE_SIP
	*   PROCESS_OUTGOING_CALLS
    */
    public static final String PHONE= Manifest.permission.READ_PHONE_STATE;

    /**
     *  permission group : CALENDAR
     *  READ_CALENDAR
     *  WRITE_CALENDAR
     */
    public static final String CALENDAR= Manifest.permission.READ_CALENDAR;

    /**
     *  permission group : CAMERA
     *  CAMERA
     */
    public static final String CAMERA= Manifest.permission.CAMERA;

    /**
     *  permission group : CONTACTS
     *  READ_CONTACTS
     *  WRITE_CONTACTS
     *  GET_ACCOUNTS
     */
    public static final String CONTACTS= Manifest.permission.READ_CONTACTS;

    /**
     *  permission group : LOCATION
     *  ACCESS_FINE_LOCATION
     *  ACCESS_COARSE_LOCATION
     */
    public static final String LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;

    /**
     *  permission group : MICROPHONE
     *  RECORD_AUDIO
     */
    public static final String MICROPHONE= Manifest.permission.RECORD_AUDIO;

    /**
     *  permission group : SENSORS
     *  BODY_SENSORS
     */
    public static final String SENSORS= Manifest.permission.BODY_SENSORS;

    /**
     *  permission group : SMS
     *  SEND_SMS
     *  RECEIVE_SMS
     *  READ_SMS
     *  RECEIVE_WAP_PUSH
     *  RECEIVE_MMS
     */
    public static final String SMS= Manifest.permission.SEND_SMS;

    /**
     *  permission group : STORAGE
     *  READ_EXTERNAL_STORAGE
     *  WRITE_EXTERNAL_STORAGE
     */
    public static final String STORAGE= Manifest.permission.WRITE_EXTERNAL_STORAGE;

}

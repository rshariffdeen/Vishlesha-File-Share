package com.vishlesha.app;

/**
 * Created by ridwan on 1/1/16.
 */
public class GlobalConstant {

    public static final int PORT_LISTEN = 1234;
    public static final int PORT_CLIENT = 1235;

    //Error Codes
    public static final int ERR_CODE_GENERAL = 0;

    public static final int ERR_CODE_REG_COMMAND = 10;
    public static final int ERR_CODE_REG_USERNAME = 11;
    public static final int ERR_CODE_REG_IPPORT = 12;
    public static final int ERR_CODE_REG_FULL = 13;

    public static final int ERR_CODE_UNREG_INVALID = 20;



    //Error Message
    public static final String ERR_MSG_GENERAL = "Generic Error Message: Command not Understood";
    public static final String ERR_MSG_REG_COMMAND = "Some error in Command";
    public static final String ERR_MSG_REG_USERNAME = "Username already registered";
    public static final String ERR_MSG_REG_IP_PORT = "IP and Port already registered";
    public static final String ERR_MSG_REG_BS_FULL = "Can't register. Server Full";

    public static final String ERR_MSG_UNREG_INVALID = "IP and Port not found or Incorrect Command";


    public static final String REGISTER_SUCCESS_RESPONSE = "Successfully Registered";
    public static final String UNREGISTER_SUCCESS_RESPONSE = "Successfully Unregistered";
    public static final String UNREGISTER_ERROR_RESPONSE = "IP and Port not found in BS register";


    private static String username;
    private static boolean testMode;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GlobalConstant.username = username;
    }

    public static boolean isTestMode() {
        return testMode;
    }

    public static void setTestMode(boolean testMode) {
        GlobalConstant.testMode = testMode;
    }
}

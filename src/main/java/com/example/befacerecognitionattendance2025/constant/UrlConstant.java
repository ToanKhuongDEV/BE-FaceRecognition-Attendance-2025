package com.example.befacerecognitionattendance2025.constant;

public class UrlConstant {


    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
        public static final String REFRESH = PRE_FIX + "/refresh";
    }
    public static class Employee {
        private static final String PRE_FIX = "/accounts";
        public static final String COMMON = PRE_FIX;
        public static final String ID = PRE_FIX + "/{id}";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password";
    }
    public static class Department {
        private static final String PRE_FIX = "/departments";
        public static final String COMMON = PRE_FIX;
        public static final String ID = PRE_FIX + "/{id}";
        public static final String ADD_EMPLOYEE = ID + "/department";
    }
}

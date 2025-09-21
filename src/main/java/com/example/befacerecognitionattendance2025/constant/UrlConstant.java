package com.example.befacerecognitionattendance2025.constant;

public class UrlConstant {


    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
    }
    public static class Employee {
        private static final String PRE_FIX = "/accounts";
        public static final String COMMON = PRE_FIX;
        public static final String ID = PRE_FIX + "/{id}";
    }
}

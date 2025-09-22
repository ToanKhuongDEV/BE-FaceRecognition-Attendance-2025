package com.example.befacerecognitionattendance2025.constant;

public class ErrorMessage {

    public static String INVALID_IMAGE_FILE = "chỉ được upload ảnh";
    public static final String UNAUTHORIZED = "Xin lỗi, bạn cần cung cấp thông tin xác thực để thực hiện hành động này";
    public static final String FORBIDDEN = "Xin lỗi, bạn không có quyền để thực hiện hành động này";
    public static final String INVALID_TOKEN = "Token không hợp lệ hoặc hết hạn";


    public static class Validation {
        public static final String NOT_BLANK = "Không thể trống";
        public static final String INVALID_EMAIL = "Sai định dạng email";
        public static final String INVALID_PASSWORD = "Password phải ≥8 ký tự, có ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt";
        public static final String INVALID_USERNAME = "Username phải dài 5-30 ký tự và chỉ chứa chữ, số, dấu gạch dưới hoặc chấm";
        public static String INVALID_PHONE_NUMBER = "Số điện thoại không hợp lệ";

    }

    public static class Auth {
        public static final String ERR_INCORRECT_CREDENTIALS = "Username hoặc mật khẩu không chính xác";
    }

    public static class Employee{
        public static final String EMAIL_EXISTS = "Email đã tồn tại";
        public static final String NOT_ENOUGH_AGE = "Nhân viên phải đủ 18 tuổi";
        public static final String NOT_FOUND = "Không tìm thấy nhân viên";
    }

}

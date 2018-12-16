package bsi.mpoo.istock.services;

public class Constants {

    public static class Status {
        public static final int ACTIVE = 1;
        public static final int INACTIVE = 2;
        public static final int FIRST_ACCESS_FOR_USER = 3;
    }

    public static class Image{
        public static final int REQUEST_CODE = 1;
        public static final int ORIENTATION_OUT_OF_BOUNDS = 9;
        public static final int REQUIRED_SIZE = 100;
        public static final int SCALE_BASE = 1;
        public static final int OFFSET = 0;
        public static final int QUALITY = 70;
        public static final int SAFE_ANGLE = 0;
        public static final int FIRST_PIXEL_CORDINATE_SOURCE_X = 0;
        public static final int FIRST_PIXEL_CORDINATE_SOURCE_Y = 0;
    }

    public static class UserTypes{
        public static final int ADMINISTRATOR = 1;
        public static final int SALESMAN = 2;
        public static final int PRODUCER = 3;
        public static final int IS_THE_ADMINISTRATOR = -1;
    }

}

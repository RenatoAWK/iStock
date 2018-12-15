package bsi.mpoo.istock.services;

public enum ImageEnum {
    REQUEST_CODE(1),
    ORIENTATION_OUT_OF_BOUNDS(9),
    REQUIRED_SIZE(100),
    SCALE_BASE(1),
    OFFSET(0),
    QUALITY(70),
    SAFE_ANGLE(0);

    private int value;

    ImageEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}

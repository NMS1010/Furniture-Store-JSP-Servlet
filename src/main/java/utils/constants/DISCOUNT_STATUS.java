package utils.constants;

import java.util.HashMap;

public class DISCOUNT_STATUS {
    public static final int  EXPIRED = 0;
    public static final int  ACTIVE = 1;
    public static final int  IN_ACTIVE = 2;

    public static HashMap<String, Integer> Status = new HashMap<String, Integer>(){
        {
            put("Hết hạn", EXPIRED);
            put("Còn mã", ACTIVE);
            put("Hết mã", IN_ACTIVE);
        }
    };
}

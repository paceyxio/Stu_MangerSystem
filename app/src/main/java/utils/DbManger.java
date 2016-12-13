package utils;

import android.content.Context;

/**
 * Created by xiong on 2016/12/12.
 */
public class DbManger {
    private static MySqliteHelper helper;

    public static MySqliteHelper getIntance(Context context) {
        if (helper == null) {
            helper = new MySqliteHelper(context);
        }
        return helper;
    }
}

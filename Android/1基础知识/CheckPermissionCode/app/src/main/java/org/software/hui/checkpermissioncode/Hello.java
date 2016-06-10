package org.software.hui.checkpermissioncode;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by wangj on 2015/10/23.
 */
public class Hello {
    public static final String PERMISSION_SAY_HELLO = "org.software.hui.checkpermissioncode.permission.SAY_HELLO";
    public static void sayHello(Context context) {
        int checkResult = context.checkCallingOrSelfPermission(PERMISSION_SAY_HELLO);
        if(checkResult != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException("执行sayHello()方法需要有PERMISSION_SAY_HELLO权限");
        }

        System.out.println("Hello you guys!");
    }
}

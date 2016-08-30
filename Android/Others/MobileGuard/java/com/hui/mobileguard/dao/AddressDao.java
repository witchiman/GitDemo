package com.hui.mobileguard.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by HUI on 2016/5/12.
 */
public class AddressDao  {
    public static final String PATH = "data/data/com.hui.mobileguard/files/address.db";
    private static String TAG  = "AddressDao";

    public static String getAddress(String number) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String address = "未知号码";

        if (number.matches("^1[3-8]\\d{9}$")) {//手机号码
            Cursor cursor = db.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)"
                    , new String[]{number.substring(0, 7)});
            if (cursor.moveToNext()) {
                address = cursor.getString(0);
            }
            cursor.close();
        } else if (number.matches("^\\d+$")) {
            switch (number.length()) {
                case 3:
                     switch (number) {
                         case "110":
                             address = "匪警";
                             break;
                         case "120":
                             address = "急救中心";
                             break;
                         case "119":
                             address = "火警";
                             break;
                         case "122":
                             address = "交通事故报警";
                             break;
                         default:
                             break;
                     }
                    break;
                case 4:
                    address = "模拟器";
                    break;
                case 5:
                    if (address.equals("12306")) {
                        address = "火车票预订";
                    } else {
                        address = "客服电话";
                    }
                    break;
                case 7:
                case 8:
                    address = "本地电话";
                    break;
                default:
                    if (number.startsWith("0") && number.length()>10) {
                        Cursor cursor = db.rawQuery("select location from data2 where area=?",
                                new String[]{number.substring(1, 4)});
                        if (cursor.moveToNext()) {
                            address = cursor.getString(0);
                        } else {
                            cursor.close();
                            cursor = db.rawQuery("select location from data2 where area=?",
                                    new String[] {number.substring(1,3)});
                            if (cursor.moveToNext()) {
                                address = cursor.getString(0);
                            }
                            cursor.close();
                        }
                    }
                    break;
            }
        }
        db.close();
        return address;
    }
}

package com.wandao.myapplication;

import android.os.RemoteException;
import android.util.Log;

import static com.wandao.myapplication.activity.MyApplication.mService;

public class LockController {
    private static LockController singleton;
    private static int doorNumber = 8;
    public static int doorStatus[] = new int[32];
    public static int doorStatusBoard2[]=new int[32];    //第二个锁板的状态
   // public static byte[] bRecv ={(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0x20,(byte)0x20,(byte)0x20,(byte)0x20};
 // {0xFF ,0xFF ,0xFF ,0xFF ,0x20 ,0x20 ,0x20 ,0x20 }
    public static byte[] bRecv;
    private LockController() {
    }

    public static synchronized LockController getInstance() {
        if (singleton == null) {
            singleton = new LockController();
        }
        return singleton;
    }

    public void updateSingleLockStatus(int doorNumber) {
        byte[] bRecv = new byte[0];
        try {
            bRecv = mService.lockControlQueryDoorStatus(2000, Byte.valueOf(0 + ""));
            printByteToHexString(bRecv, bRecv.length, "lockControlQueryDoorStatus");

            if (doorStatus[doorNumber - 1] == 1) {
                //已经关上了
            } else {
                //没有关上
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    public void updateAllLockStatus() {
        try {
            if (mService != null) {
                bRecv = mService.lockControlQueryDoorStatus(1000, Byte.valueOf(0 + ""));
                if (bRecv != null)
                    printByteToHexString(bRecv, bRecv.length, "lockControlQueryDoorStatus");
                //0xBB ,0x00 ,0x06 ,0x00 ,0x02 ,0x87 ,0x80 ,0x00 ,0x20 ,0x20 ,0x20 ,0xA0 ,0x98 ,0x66
                //{0xBB ,0x00 ,0x06 ,0x00 ,0x02 ,0x87 ,0x80 ,0x00 ,0x20 ,0x20 ,0x20 ,0xA0 ,0x98 ,0x66 }
                //{0xBB ,0x00 ,0x06 ,0x00 ,0x02 ,0xA7 ,0xFF ,0xFF ,0x20 ,0x20 ,0x20 ,0xA6 ,0x4C ,0x66 }


                if (bRecv != null && (bRecv.length == 6 || bRecv.length == 8)) {
                    //TODO bRecv[0] 25-32 号锁状态
                    //TODO bRecv[1] 17-27  号锁状态
                    //TODO bRecv[2] 9-16  号锁状态
                    //TODO bRecv[3] 1-8  号锁状态
                    int a = Integer.parseInt(String.format("0x%02X", bRecv[3]).split("0x")[1], 16);
                    int b = Integer.parseInt(String.format("0x%02X", bRecv[2]).split("0x")[1], 16);
                    int c = Integer.parseInt(String.format("0x%02X", bRecv[1]).split("0x")[1], 16);
                    int d = Integer.parseInt(String.format("0x%02X", bRecv[0]).split("0x")[1], 16);
                    byteToDoorStatus(a, b, c, d);

                    //Log.d("666666b",sb.toString());

//                String res = new String(bRecv[3],"UTF-8");
//                Log.d("lock_check_666", bRecv[3]+" qqqqqq "+(int)bRecv[3]);
                    int valueTen = 328;
                    //将其转换为十六进制并输出
                    String strHex = Integer.toHexString(valueTen);
                    Log.d("16t10", valueTen + " [十进制]---->[十六进制] " + strHex);
                    //将十六进制格式化输出
                    String strHex2 = String.format("%08x", valueTen);
                    Log.d("16t10", valueTen + " [十进制]---->[十六进制] " + strHex2);

                }
            }
        } catch (RemoteException e) {
            Log.d("LockController",e.toString());
            e.printStackTrace();
        }
    }
    public void updateAllLockStatusBoard2() {
        try {
            bRecv = mService.lockControlQueryDoorStatus(1000, Byte.valueOf(15 + ""));
            if (bRecv!=null)
                printByteToHexString(bRecv, bRecv.length, "lockControlQueryDoorStatus");
            //0xBB ,0x00 ,0x06 ,0x00 ,0x02 ,0x87 ,0x80 ,0x00 ,0x20 ,0x20 ,0x20 ,0xA0 ,0x98 ,0x66
            //{0xBB ,0x00 ,0x06 ,0x00 ,0x02 ,0x87 ,0x80 ,0x00 ,0x20 ,0x20 ,0x20 ,0xA0 ,0x98 ,0x66 }
            //{0xBB ,0x00 ,0x06 ,0x00 ,0x02 ,0xA7 ,0xFF ,0xFF ,0x20 ,0x20 ,0x20 ,0xA6 ,0x4C ,0x66 }
            if (bRecv != null && (bRecv.length == 6 || bRecv.length == 8)) {
                //TODO bRecv[0] 25-32 号锁状态
                //TODO bRecv[1] 17-27  号锁状态
                //TODO bRecv[2] 9-16  号锁状态
                //TODO bRecv[3] 1-8  号锁状态
                int a = Integer.parseInt(String.format("0x%02X", bRecv[3]).split("0x")[1], 16);
                int b = Integer.parseInt(String.format("0x%02X", bRecv[2]).split("0x")[1], 16);
                int c = Integer.parseInt(String.format("0x%02X", bRecv[1]).split("0x")[1], 16);
                int d = Integer.parseInt(String.format("0x%02X", bRecv[0]).split("0x")[1], 16);
                byteToDoorStatusBoard2(a, b, c,d);

                //Log.d("666666b",sb.toString());

//                String res = new String(bRecv[3],"UTF-8");
//                Log.d("lock_check_666", bRecv[3]+" qqqqqq "+(int)bRecv[3]);
                int valueTen = 328;
                //将其转换为十六进制并输出
                String strHex = Integer.toHexString(valueTen);
                Log.d("16t10", valueTen + " [十进制]---->[十六进制] " + strHex);
                //将十六进制格式化输出
                String strHex2 = String.format("%08x", valueTen);
                Log.d("16t10", valueTen + " [十进制]---->[十六进制] " + strHex2);

            }

        } catch (RemoteException e) {
            Log.d("LockController",e.toString());
            e.printStackTrace();
        }
    }
    public void printByteToHexString(byte[] buffer, int length, String parmMarkTag) {
        StringBuilder hexString = new StringBuilder();
        hexString.append("{");
        for (int i = 0; i < length; i++) {
            hexString.append(String.format("0x%02X ,", buffer[i]));
        }

        hexString.append("}");

        //hexString.replace(0,)
        Log.d(parmMarkTag, hexString.toString().replace(",}", "}"));
        Log.d("lock_check_check", hexString.toString().replace(",}", "}"));
    }

    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }


    public static String byteArrToBinStr(byte b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            result.append(Long.toString(b & 0xff, 2) + ",");
        }
        return result.toString().substring(0, result.length() - 1);
    }

    public void byteToDoorStatus(int byte1, int byte2, int byte3,int byte4) {              //a,b,c
        String str=intToBinaryString(byte4)+intToBinaryString(byte3)+intToBinaryString(byte2)+intToBinaryString(byte1);
        Log.d("23333333333333",str);
        for (int j = str.length()-1; j >= 0; j--) {
            doorStatus[31 - j] = Integer.parseInt(String.valueOf(str.charAt(j)));
        }
    }
    public void byteToDoorStatusBoard2(int byte1, int byte2, int byte3,int byte4) {              //a,b,c
        String str=intToBinaryString(byte4)+intToBinaryString(byte3)+intToBinaryString(byte2)+intToBinaryString(byte1);
        Log.d("23333333333333",str);
        for (int j = str.length()-1; j >= 0; j--) {
            doorStatusBoard2[31 - j] = Integer.parseInt(String.valueOf(str.charAt(j)));
        }
    }

    public String intToBinaryString(int num) {
        String str = "";
        int i = 0;
        while (num != 0) {
            str = num % 2 + str;
//            doorStatus[i] = Integer.parseInt(str);
            num = num / 2;
            i++;
            i = i % 8;
        }
        return str;

    }
}

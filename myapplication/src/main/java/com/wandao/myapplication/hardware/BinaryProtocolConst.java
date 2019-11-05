package com.wandao.myapplication.hardware;

@SuppressWarnings("unused")
public class BinaryProtocolConst {

    public static final byte bTrue = (byte) 0x1;
    public static final byte bFalse = (byte) 0x2;


    public static final byte bRead = (byte) 0x80;
    public static final byte bWrite = (byte) 0xC0;

    public static final byte bCommandComplete = (byte) 0x1;              //命令错误
    public static final byte bCommandError = (byte) 0xF1;                //命令错误
    public static final byte bCommandSizeError = (byte) 0xF2;            //数据长度错误
    public static final byte bCommandChecksumError = (byte) 0xF3;        //数据校验错误
    public static final byte bCommandRepoError = (byte) 0xF4;            //数据响应错误
    public static final byte bCommandReadWriteMarkError = (byte) 0xF6;   //数据读写标记错误
    public static final byte bCommandNothing = (byte) 0xF5;              //无数据,读取专用
    public static final byte bCommandFlashError = (byte) 0xFF;           //数据写入错误
}

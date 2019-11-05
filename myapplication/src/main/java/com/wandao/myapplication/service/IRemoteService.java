/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/l/Project/Cloud/AndroidHardwareCommunication/app/src/main/aidl/com/android/hardware/IRemoteService.aidl
 */
package com.wandao.myapplication.service;
// Declare any non-default types here with import statements

public interface IRemoteService extends android.os.IInterface
{
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements com.wandao.myapplication.service.IRemoteService
    {
        private static final java.lang.String DESCRIPTOR = "com.android.hardware.IRemoteService";
        /** Construct the stub at attach it to the interface. */
        public Stub()
        {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an com.android.hardware.IRemoteService interface,
         * generating a proxy if needed.
         */
        public static com.wandao.myapplication.service.IRemoteService asInterface(android.os.IBinder obj)
        {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof com.wandao.myapplication.service.IRemoteService))) {
                return ((com.wandao.myapplication.service.IRemoteService)iin);
            }
            return new IRemoteService.Stub.Proxy(obj);
        }
        @Override
        public android.os.IBinder asBinder()
        {
            return this;
        }
        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
        {
            switch (code)
            {
                case INTERFACE_TRANSACTION:
                {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_lockControlGetLastError:
                {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result = this.lockControlGetLastError();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_lockControlOpenDoor:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    this.lockControlOpenDoor(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_lockControlSetDoorOpen:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    boolean _result = this.lockControlSetDoorOpen(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_lockControlSendOpenDoorCommand:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    this.lockControlSendOpenDoorCommand(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_lockControlQueryDoorStatus:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte[] _result = this.lockControlQueryDoorStatus(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                }
                case TRANSACTION_lockControlQueryDoorIsOpen:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte _arg2;
                    _arg2 = data.readByte();
                    boolean _result = this.lockControlQueryDoorIsOpen(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_lockControlSendSerialNumber:
                {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    byte _arg1;
                    _arg1 = data.readByte();
                    this.lockControlSendSerialNumber(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_lockControlQuerySerialNumber:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte[] _result = this.lockControlQuerySerialNumber(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                }
                case TRANSACTION_signalControlOpenOutput:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    this.signalControlOpenOutput(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlCloseOutput:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    this.signalControlCloseOutput(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlFlashOutput:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte _arg2;
                    _arg2 = data.readByte();
                    this.signalControlFlashOutput(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlDelayOpenOutput:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte _arg2;
                    _arg2 = data.readByte();
                    this.signalControlDelayOpenOutput(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlDelayCloseOutput:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte _arg2;
                    _arg2 = data.readByte();
                    this.signalControlDelayCloseOutput(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlOpenOutputDelayCloseOutput:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte _arg2;
                    _arg2 = data.readByte();
                    this.signalControlOpenOutputDelayCloseOutput(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlCloseOutputDelayOpenOutput:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte _arg2;
                    _arg2 = data.readByte();
                    this.signalControlCloseOutputDelayOpenOutput(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlSendResponseCommand:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    this.signalControlSendResponseCommand(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_signalControlQueryPowerStatus:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte[] _result = this.signalControlQueryPowerStatus(_arg0);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                }
                case TRANSACTION_signalControlQueryPower:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte _arg1;
                    _arg1 = data.readByte();
                    boolean _result = this.signalControlQueryPower(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_signalControlQueryCommandResult:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte _arg1;
                    _arg1 = data.readByte();
                    byte _arg2;
                    _arg2 = data.readByte();
                    boolean _result = this.signalControlQueryCommandResult(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_signalControlGetLastError:
                {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result = this.signalControlGetLastError();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_signalEnableSystemWatchdog:
                {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0;
                    _arg0 = (0!=data.readInt());
                    int _arg1;
                    _arg1 = data.readInt();
                    boolean _result = this.signalEnableSystemWatchdog(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_signalFeedSystemWatchdog:
                {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result = this.signalFeedSystemWatchdog();
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_signalReadSystemWatchdog:
                {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result = this.signalReadSystemWatchdog();
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_signalSetRePowerModemAndResetStabilityMcu:
                {
                    data.enforceInterface(DESCRIPTOR);
                    this.signalSetRePowerModemAndResetStabilityMcu();
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_stabilitySetStabilityMechanism:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte _arg0;
                    _arg0 = data.readByte();
                    boolean _result = this.stabilitySetStabilityMechanism(_arg0);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_stabilityQueryIntegrateCircuitCardIdentity:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte[] _result = this.stabilityQueryIntegrateCircuitCardIdentity(_arg0);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                }
                case TRANSACTION_stabilityQueryInsideSwitchStatus:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte[] _result = this.stabilityQueryInsideSwitchStatus(_arg0);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                }
                case TRANSACTION_stabilitySetRePowerModemAndResetStabilityMcu:
                {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result = this.stabilitySetRePowerModemAndResetStabilityMcu();
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_stabilityWriteRemoteControl:
                {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0;
                    _arg0 = (0!=data.readInt());
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    java.lang.String _arg4;
                    _arg4 = data.readString();
                    java.lang.String _arg5;
                    _arg5 = data.readString();
                    java.lang.String _arg6;
                    _arg6 = data.readString();
                    boolean _result = this.stabilityWriteRemoteControl(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_stabilityReadRemoteControl:
                {
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result = this.stabilityReadRemoteControl();
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                }
                case TRANSACTION_systemSetEthernetShared:
                {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0;
                    _arg0 = (0!=data.readInt());
                    int _result = this.systemSetEthernetShared(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case TRANSACTION_stabilityGetLastError:
                {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result = this.stabilityGetLastError();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_deviceQuerySerialNumber:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    byte[] _result = this.deviceQuerySerialNumber(_arg0);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
        private static class Proxy implements IRemoteService
        {
            private android.os.IBinder mRemote;
            Proxy(android.os.IBinder remote)
            {
                mRemote = remote;
            }
            @Override
            public android.os.IBinder asBinder()
            {
                return mRemote;
            }
            public java.lang.String getInterfaceDescriptor()
            {
                return DESCRIPTOR;
            }
            @Override
            public java.lang.String lockControlGetLastError() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_lockControlGetLastError, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 立即发送打开制定箱门命令
             *
             * @param parmaDoorNumber 箱门
             */
            @Override
            public void lockControlOpenDoor(byte parmaDoorNumber, byte parmaBoardIdentity) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaDoorNumber);
                    _data.writeByte(parmaBoardIdentity);
                    mRemote.transact(Stub.TRANSACTION_lockControlOpenDoor, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 设置开门数据,
             *
             * @param parmaDoorNumber    箱门
             * @param parmaBoardIdentity 板编号
             */
            @Override
            public boolean lockControlSetDoorOpen(byte parmaDoorNumber, byte parmaBoardIdentity) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaDoorNumber);
                    _data.writeByte(parmaBoardIdentity);
                    mRemote.transact(Stub.TRANSACTION_lockControlSetDoorOpen, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 发送多开门指令
             *
             * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
             * @param parmaNumber 时长
             */
            @Override
            public void lockControlSendOpenDoorCommand(byte parmaUnit, byte parmaNumber) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaUnit);
                    _data.writeByte(parmaNumber);
                    mRemote.transact(Stub.TRANSACTION_lockControlSendOpenDoorCommand, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 成功返回2个byte 的状态,失败返回null ,可以用getLastError获取错误
             *
             * @param parmaTimeOut 超时
             * @return byte[]
             */
            @Override
            public byte[] lockControlQueryDoorStatus(int parmaTimeOut, byte parmaBoardIdentity) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                byte[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    _data.writeByte(parmaBoardIdentity);
                    mRemote.transact(Stub.TRANSACTION_lockControlQueryDoorStatus, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createByteArray();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 成功返回2个byte 的状态,失败返回null ,可以用getLastError获取错误
             *
             * @param parmaTimeOut 超时
             * @return byte[]
             */
            @Override
            public boolean lockControlQueryDoorIsOpen(int parmaTimeOut, byte parmaBoardIdentity, byte parmaDoorNumber) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    _data.writeByte(parmaBoardIdentity);
                    _data.writeByte(parmaDoorNumber);
                    mRemote.transact(Stub.TRANSACTION_lockControlQueryDoorIsOpen, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 设置板子序列号
             *
             * @param parmSerialNumber byte[]
             */
            @Override
            public void lockControlSendSerialNumber(java.lang.String parmSerialNumber, byte parmBoardIdentity) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(parmSerialNumber);
                    _data.writeByte(parmBoardIdentity);
                    mRemote.transact(Stub.TRANSACTION_lockControlSendSerialNumber, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 查询主板序列号
             *
             * @param parmTimeOut 超时时间
             * @return 如果成功则返回序列号, 失败返回null.,序列号为设置则返回"null"字符,通过getLastError可以获取到错误信息.
             */
            @Override
            public byte[] lockControlQuerySerialNumber(int parmTimeOut, byte parmBoardIdentity) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                byte[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmTimeOut);
                    _data.writeByte(parmBoardIdentity);
                    mRemote.transact(Stub.TRANSACTION_lockControlQuerySerialNumber, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createByteArray();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 开启指定端口输出
             *
             * @param parmaPort byte
             */
            @Override
            public void signalControlOpenOutput(byte parmaPort) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaPort);
                    mRemote.transact(Stub.TRANSACTION_signalControlOpenOutput, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 断开指定端口输出
             *
             * @param parmaPort byte
             */
            @Override
            public void signalControlCloseOutput(byte parmaPort) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaPort);
                    mRemote.transact(Stub.TRANSACTION_signalControlCloseOutput, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 闪断电源输出
             *
             * @param parmaPort   byte
             * @param parmaUnit   闪断单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
             * @param parmaNumber 闪断时长
             */
            @Override
            public void signalControlFlashOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaPort);
                    _data.writeByte(parmaUnit);
                    _data.writeByte(parmaNumber);
                    mRemote.transact(Stub.TRANSACTION_signalControlFlashOutput, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 延时开启输出
             *
             * @param parmaPort   byte
             * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
             * @param parmaNumber 闪断时长
             */
            @Override
            public void signalControlDelayOpenOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaPort);
                    _data.writeByte(parmaUnit);
                    _data.writeByte(parmaNumber);
                    mRemote.transact(Stub.TRANSACTION_signalControlDelayOpenOutput, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 延时关闭输出
             *
             * @param parmaPort   byte
             * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
             * @param parmaNumber 时长
             */
            @Override
            public void signalControlDelayCloseOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaPort);
                    _data.writeByte(parmaUnit);
                    _data.writeByte(parmaNumber);
                    mRemote.transact(Stub.TRANSACTION_signalControlDelayCloseOutput, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 开启输出,然后延时关闭
             *
             * @param parmaPort   byte
             * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
             * @param parmaNumber 时长
             */
            @Override
            public void signalControlOpenOutputDelayCloseOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaPort);
                    _data.writeByte(parmaUnit);
                    _data.writeByte(parmaNumber);
                    mRemote.transact(Stub.TRANSACTION_signalControlOpenOutputDelayCloseOutput, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 关闭输出,然后延时开启
             *
             * @param parmaPort   byte
             * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
             * @param parmaNumber 时长
             */
            @Override
            public void signalControlCloseOutputDelayOpenOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaPort);
                    _data.writeByte(parmaUnit);
                    _data.writeByte(parmaNumber);
                    mRemote.transact(Stub.TRANSACTION_signalControlCloseOutputDelayOpenOutput, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 设置执行命令是否响应,默认为响应.值为0x01,设置为 设置为0x02 时,则不响应执行命令结果.
             *
             * @param parmaResponseCommand bytea
             */
            @Override
            public void signalControlSendResponseCommand(byte parmaResponseCommand) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaResponseCommand);
                    mRemote.transact(Stub.TRANSACTION_signalControlSendResponseCommand, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 查询电源状态
             *
             * @param parmaTimeOut 超时时间
             * @return 如果成功则返回电源状态数据, 失败返回null.通过getLastError可以获取到错误信息.
             */
            @Override
            public byte[] signalControlQueryPowerStatus(int parmaTimeOut) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                byte[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    mRemote.transact(Stub.TRANSACTION_signalControlQueryPowerStatus, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createByteArray();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 查询电源状态
             *
             * @param parmaTimeOut 超时时间
             * @return 如果成功则返回电源状态数据, 失败返回null.通过getLastError可以获取到错误信息.
             */
            @Override
            public boolean signalControlQueryPower(int parmaTimeOut, byte parmaPort) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    _data.writeByte(parmaPort);
                    mRemote.transact(Stub.TRANSACTION_signalControlQueryPower, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 查询上次命令执行结果,超时返回false,成功则返回true
             *
             * @param parmaTimeOut     超时
             * @param parmaCommandType 命令类型
             * @param parmaCommand     命令
             * @return bool
             */
            @Override
            public boolean signalControlQueryCommandResult(int parmaTimeOut, byte parmaCommandType, byte parmaCommand) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    _data.writeByte(parmaCommandType);
                    _data.writeByte(parmaCommand);
                    mRemote.transact(Stub.TRANSACTION_signalControlQueryCommandResult, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override
            public java.lang.String signalControlGetLastError() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_signalControlGetLastError, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 开启或者关闭系统看门狗
             *
             * @param parmaIsEnabled 开启还是关闭系统看门狗
             * @param parmaUnit      单位 为0x01时，则表示单位为S，为0x2时候，则表示单位为ms
             * @param parmaNumber    时长
             * @param parmaTimeOut   超时时间
             * @return true|false
             */
            @Override
            public boolean signalEnableSystemWatchdog(boolean parmaIsEnabled, int parmTimeOut) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(((parmaIsEnabled)?(1):(0)));
                    _data.writeInt(parmTimeOut);
                    mRemote.transact(Stub.TRANSACTION_signalEnableSystemWatchdog, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override
            public boolean signalFeedSystemWatchdog() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_signalFeedSystemWatchdog, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override
            public boolean signalReadSystemWatchdog() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_signalReadSystemWatchdog, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 重新打开4G模块电源并复位维稳单片机(硬复位)
             *
             * @return true|false
             */
            @Override
            public void signalSetRePowerModemAndResetStabilityMcu() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_signalSetRePowerModemAndResetStabilityMcu, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            /**
             * 开启还是关闭网络维稳(此设置会持久化)
             *
             * @param parmaIsEnabled 是否开启网络维稳
             * @param parmaLevel     维稳等级 0 都维稳,1 只维稳单片机,2 只维稳ARM 主机
             * @return true|false
             */
            @Override
            public boolean stabilitySetStabilityMechanism(byte parmaLevel) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(parmaLevel);
                    mRemote.transact(Stub.TRANSACTION_stabilitySetStabilityMechanism, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 查询ICCID号
             *
             * @param parmaTimeOut 超时时间
             * @return 如果成功则返回ICCID, 失败返回null.,序列号未设置则返回"null"字符,通过getLastError可以获取到错误信息.
             */
            @Override
            public byte[] stabilityQueryIntegrateCircuitCardIdentity(int parmaTimeOut) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                byte[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    mRemote.transact(Stub.TRANSACTION_stabilityQueryIntegrateCircuitCardIdentity, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createByteArray();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override
            public byte[] stabilityQueryInsideSwitchStatus(int parmaTimeOut) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                byte[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    mRemote.transact(Stub.TRANSACTION_stabilityQueryInsideSwitchStatus, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createByteArray();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 重新打开4G模块电源并复位维稳单片机
             *
             * @return true|false
             */
            @Override
            public boolean stabilitySetRePowerModemAndResetStabilityMcu() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_stabilitySetRePowerModemAndResetStabilityMcu, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 配置远程控制信息(此设置会持久化)
             *
             * @param isEnabled        是否开启远程控制
             * @param szProductSecret  产品标记
             * @param szDeviceSecret   设备标记
             * @param szBrokerAddress  服务器地址
             * @param szBrokerClientId 设备唯一编号
             * @param szBrokerUserName 登录用户名
             * @param szBrokerPassword 登录密码
             * @return true|false
             */
            @Override
            public boolean stabilityWriteRemoteControl(boolean isEnabled, java.lang.String szProductSecret, java.lang.String szDeviceSecret, java.lang.String szBrokerAddress, java.lang.String szBrokerClientId, java.lang.String szBrokerUserName, java.lang.String szBrokerPassword) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(((isEnabled)?(1):(0)));
                    _data.writeString(szProductSecret);
                    _data.writeString(szDeviceSecret);
                    _data.writeString(szBrokerAddress);
                    _data.writeString(szBrokerClientId);
                    _data.writeString(szBrokerUserName);
                    _data.writeString(szBrokerPassword);
                    mRemote.transact(Stub.TRANSACTION_stabilityWriteRemoteControl, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 读取远程配置数据.
             *
             * @return byte[]
             */
            @Override
            public byte[] stabilityReadRemoteControl() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                byte[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_stabilityReadRemoteControl, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createByteArray();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override
            public int systemSetEthernetShared(boolean parmaIsEnabled) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(((parmaIsEnabled)?(1):(0)));
                    mRemote.transact(Stub.TRANSACTION_systemSetEthernetShared, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override
            public java.lang.String stabilityGetLastError() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_stabilityGetLastError, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * 查询主板序列号
             *
             * @param parmaTimeOut 超时时间
             * @return 如果成功则返回序列号, 失败返回null.,序列号为设置则返回"null"字符,通过getLastError可以获取到错误信息.
             */
            @Override
            public byte[] deviceQuerySerialNumber(int parmaTimeOut) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                byte[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(parmaTimeOut);
                    mRemote.transact(Stub.TRANSACTION_deviceQuerySerialNumber, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createByteArray();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }
        static final int TRANSACTION_lockControlGetLastError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_lockControlOpenDoor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_lockControlSetDoorOpen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_lockControlSendOpenDoorCommand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
        static final int TRANSACTION_lockControlQueryDoorStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
        static final int TRANSACTION_lockControlQueryDoorIsOpen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
        static final int TRANSACTION_lockControlSendSerialNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
        static final int TRANSACTION_lockControlQuerySerialNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
        static final int TRANSACTION_signalControlOpenOutput = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
        static final int TRANSACTION_signalControlCloseOutput = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
        static final int TRANSACTION_signalControlFlashOutput = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
        static final int TRANSACTION_signalControlDelayOpenOutput = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
        static final int TRANSACTION_signalControlDelayCloseOutput = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
        static final int TRANSACTION_signalControlOpenOutputDelayCloseOutput = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
        static final int TRANSACTION_signalControlCloseOutputDelayOpenOutput = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
        static final int TRANSACTION_signalControlSendResponseCommand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
        static final int TRANSACTION_signalControlQueryPowerStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
        static final int TRANSACTION_signalControlQueryPower = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
        static final int TRANSACTION_signalControlQueryCommandResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
        static final int TRANSACTION_signalControlGetLastError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
        static final int TRANSACTION_signalEnableSystemWatchdog = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
        static final int TRANSACTION_signalFeedSystemWatchdog = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
        static final int TRANSACTION_signalReadSystemWatchdog = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
        static final int TRANSACTION_signalSetRePowerModemAndResetStabilityMcu = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
        static final int TRANSACTION_stabilitySetStabilityMechanism = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
        static final int TRANSACTION_stabilityQueryIntegrateCircuitCardIdentity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
        static final int TRANSACTION_stabilityQueryInsideSwitchStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
        static final int TRANSACTION_stabilitySetRePowerModemAndResetStabilityMcu = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
        static final int TRANSACTION_stabilityWriteRemoteControl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
        static final int TRANSACTION_stabilityReadRemoteControl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
        static final int TRANSACTION_systemSetEthernetShared = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
        static final int TRANSACTION_stabilityGetLastError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
        static final int TRANSACTION_deviceQuerySerialNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
    }
    public java.lang.String lockControlGetLastError() throws android.os.RemoteException;
    /**
     * 立即发送打开制定箱门命令
     *
     * @param parmaDoorNumber 箱门
     */
    public void lockControlOpenDoor(byte parmaDoorNumber, byte parmaBoardIdentity) throws android.os.RemoteException;
    /**
     * 设置开门数据,
     *
     * @param parmaDoorNumber    箱门
     * @param parmaBoardIdentity 板编号
     */
    public boolean lockControlSetDoorOpen(byte parmaDoorNumber, byte parmaBoardIdentity) throws android.os.RemoteException;
    /**
     * 发送多开门指令
     *
     * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
     * @param parmaNumber 时长
     */
    public void lockControlSendOpenDoorCommand(byte parmaUnit, byte parmaNumber) throws android.os.RemoteException;
    /**
     * 成功返回2个byte 的状态,失败返回null ,可以用getLastError获取错误
     *
     * @param parmaTimeOut 超时
     * @return byte[]
     */
    public byte[] lockControlQueryDoorStatus(int parmaTimeOut, byte parmaBoardIdentity) throws android.os.RemoteException;
    /**
     * 成功返回2个byte 的状态,失败返回null ,可以用getLastError获取错误
     *
     * @param parmaTimeOut 超时
     * @return byte[]
     */
    public boolean lockControlQueryDoorIsOpen(int parmaTimeOut, byte parmaBoardIdentity, byte parmaDoorNumber) throws android.os.RemoteException;
    /**
     * 设置板子序列号
     *
     * @param parmSerialNumber byte[]
     */
    public void lockControlSendSerialNumber(java.lang.String parmSerialNumber, byte parmBoardIdentity) throws android.os.RemoteException;
    /**
     * 查询主板序列号
     *
     * @param parmTimeOut 超时时间
     * @return 如果成功则返回序列号, 失败返回null.,序列号为设置则返回"null"字符,通过getLastError可以获取到错误信息.
     */
    public byte[] lockControlQuerySerialNumber(int parmTimeOut, byte parmBoardIdentity) throws android.os.RemoteException;
    /**
     * 开启指定端口输出
     *
     * @param parmaPort byte
     */
    public void signalControlOpenOutput(byte parmaPort) throws android.os.RemoteException;
    /**
     * 断开指定端口输出
     *
     * @param parmaPort byte
     */
    public void signalControlCloseOutput(byte parmaPort) throws android.os.RemoteException;
    /**
     * 闪断电源输出
     *
     * @param parmaPort   byte
     * @param parmaUnit   闪断单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
     * @param parmaNumber 闪断时长
     */
    public void signalControlFlashOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException;
    /**
     * 延时开启输出
     *
     * @param parmaPort   byte
     * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
     * @param parmaNumber 闪断时长
     */
    public void signalControlDelayOpenOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException;
    /**
     * 延时关闭输出
     *
     * @param parmaPort   byte
     * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
     * @param parmaNumber 时长
     */
    public void signalControlDelayCloseOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException;
    /**
     * 开启输出,然后延时关闭
     *
     * @param parmaPort   byte
     * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
     * @param parmaNumber 时长
     */
    public void signalControlOpenOutputDelayCloseOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException;
    /**
     * 关闭输出,然后延时开启
     *
     * @param parmaPort   byte
     * @param parmaUnit   单位 为0x01时，则表示闪断单位为S，为0x2时候，则表示单位为ms
     * @param parmaNumber 时长
     */
    public void signalControlCloseOutputDelayOpenOutput(byte parmaPort, byte parmaUnit, byte parmaNumber) throws android.os.RemoteException;
    /**
     * 设置执行命令是否响应,默认为响应.值为0x01,设置为 设置为0x02 时,则不响应执行命令结果.
     *
     * @param parmaResponseCommand bytea
     */
    public void signalControlSendResponseCommand(byte parmaResponseCommand) throws android.os.RemoteException;
    /**
     * 查询电源状态
     *
     * @param parmaTimeOut 超时时间
     * @return 如果成功则返回电源状态数据, 失败返回null.通过getLastError可以获取到错误信息.
     */
    public byte[] signalControlQueryPowerStatus(int parmaTimeOut) throws android.os.RemoteException;
    /**
     * 查询电源状态
     *
     * @param parmaTimeOut 超时时间
     * @return 如果成功则返回电源状态数据, 失败返回null.通过getLastError可以获取到错误信息.
     */
    public boolean signalControlQueryPower(int parmaTimeOut, byte parmaPort) throws android.os.RemoteException;
    /**
     * 查询上次命令执行结果,超时返回false,成功则返回true
     *
     * @param parmaTimeOut     超时
     * @param parmaCommandType 命令类型
     * @param parmaCommand     命令
     * @return bool
     */
    public boolean signalControlQueryCommandResult(int parmaTimeOut, byte parmaCommandType, byte parmaCommand) throws android.os.RemoteException;
    public java.lang.String signalControlGetLastError() throws android.os.RemoteException;
    /**
     * 开启或者关闭系统看门狗
     *
     * @param parmaIsEnabled 开启还是关闭系统看门狗
     * @param parmaUnit      单位 为0x01时，则表示单位为S，为0x2时候，则表示单位为ms
     * @param parmaNumber    时长
     * @param parmaTimeOut   超时时间
     * @return true|false
     */
    public boolean signalEnableSystemWatchdog(boolean parmaIsEnabled, int parmTimeOut) throws android.os.RemoteException;
    public boolean signalFeedSystemWatchdog() throws android.os.RemoteException;
    public boolean signalReadSystemWatchdog() throws android.os.RemoteException;
    /**
     * 重新打开4G模块电源并复位维稳单片机(硬复位)
     *
     * @return true|false
     */
    public void signalSetRePowerModemAndResetStabilityMcu() throws android.os.RemoteException;
    /**
     * 开启还是关闭网络维稳(此设置会持久化)
     *
     * @param parmaIsEnabled 是否开启网络维稳
     * @param parmaLevel     维稳等级 0 都维稳,1 只维稳单片机,2 只维稳ARM 主机
     * @return true|false
     */
    public boolean stabilitySetStabilityMechanism(byte parmaLevel) throws android.os.RemoteException;
    /**
     * 查询ICCID号
     *
     * @param parmaTimeOut 超时时间
     * @return 如果成功则返回ICCID, 失败返回null.,序列号未设置则返回"null"字符,通过getLastError可以获取到错误信息.
     */
    public byte[] stabilityQueryIntegrateCircuitCardIdentity(int parmaTimeOut) throws android.os.RemoteException;
    public byte[] stabilityQueryInsideSwitchStatus(int parmaTimeOut) throws android.os.RemoteException;
    /**
     * 重新打开4G模块电源并复位维稳单片机
     *
     * @return true|false
     */
    public boolean stabilitySetRePowerModemAndResetStabilityMcu() throws android.os.RemoteException;
    /**
     * 配置远程控制信息(此设置会持久化)
     *
     * @param isEnabled        是否开启远程控制
     * @param szProductSecret  产品标记
     * @param szDeviceSecret   设备标记
     * @param szBrokerAddress  服务器地址
     * @param szBrokerClientId 设备唯一编号
     * @param szBrokerUserName 登录用户名
     * @param szBrokerPassword 登录密码
     * @return true|false
     */
    public boolean stabilityWriteRemoteControl(boolean isEnabled, java.lang.String szProductSecret, java.lang.String szDeviceSecret, java.lang.String szBrokerAddress, java.lang.String szBrokerClientId, java.lang.String szBrokerUserName, java.lang.String szBrokerPassword) throws android.os.RemoteException;
    /**
     * 读取远程配置数据.
     *
     * @return byte[]
     */
    public byte[] stabilityReadRemoteControl() throws android.os.RemoteException;
    public int systemSetEthernetShared(boolean parmaIsEnabled) throws android.os.RemoteException;
    public java.lang.String stabilityGetLastError() throws android.os.RemoteException;
    /**
     * 查询主板序列号
     *
     * @param parmaTimeOut 超时时间
     * @return 如果成功则返回序列号, 失败返回null.,序列号为设置则返回"null"字符,通过getLastError可以获取到错误信息.
     */
    public byte[] deviceQuerySerialNumber(int parmaTimeOut) throws android.os.RemoteException;
}

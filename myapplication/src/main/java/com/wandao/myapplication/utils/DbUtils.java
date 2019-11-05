package com.wandao.myapplication.utils;

import android.content.Context;

import com.wandao.myapplication.activity.MyApplication;
import com.wandao.myapplication.bean.DayLogBean;
import com.wandao.myapplication.bean.MonthLogBean;
import com.wandao.myapplication.bean.WeekLogBean;
import com.wandao.myapplication.greendao.Box;
import com.wandao.myapplication.greendao.DaoSession;
import com.wandao.myapplication.greendao.ExceptionLog;
import com.wandao.myapplication.greendao.ExceptionLogDao;
import com.wandao.myapplication.greendao.Log;
import com.wandao.myapplication.greendao.LogDao;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.greendao.UserPersonalTime;

import org.greenrobot.greendao.query.QueryBuilder;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DbUtils {
    private final static int STORAGE_NORMAL_STATUS = 0;  // 正常存
    private final static int FEATCH_NORMAL_STATUS = 1;  // 正常取
    private final static int FEATCH_LATE_STATUS = 2;   //存件晚
    private final static int FEATCH_EXPECTION_STATUS = 3; //紧急取件 （特权取件）
    String[] title__day = {"工号", "部门", "姓名", "柜门号", "存入时间", "存入状态", "取出时间", "取出状态", "应急", "备注"};
    String[] title_week = {"工号", "部门", "姓名", "柜门号", "正常存入字数", "超时存入次数", "超时存入日期", "未存次数", "未存日期", "正常取出", "应急取出", "应急取出日期", "二次存取次数", "二次存取日期"};
    String[] title_month = {"工号", "部门", "姓名", "柜门号", "本月汇总", "正常存入次数", "超时存入次数", "超时存入日期", "未存次数", "未存日期", "正常取出", "应急取出", "应急取出日期", "二次存取次数", "二次存取日期"};
    private static DaoSession daoSession = MyApplication.getDaoSession();
    static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    static DateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    static SimpleDateFormat format3 = new SimpleDateFormat("HH:mm");
    private static DbUtils s = null;

    private DbUtils() {
    }

    public static DbUtils getInstance() {
        if (s == null)//和上面的相比只是多增加了一次判断
        {
            synchronized (DbUtils.class) {
                if (s == null)
                    s = new DbUtils();
                return s;
            }
        }
        return s;
    }


    public static int getDoorNumber() {
        List<Box> users = daoSession.loadAll(Box.class);
        if (users.size() != 0)
            return users.size();
        else return 20;
    }

    public boolean getDoorHealthStatus(int number) {
        List<Box> boxes = daoSession.loadAll(Box.class);
        for (Box box : boxes) {
            if (box.getStatus() == 5)
                return false;
        }
        return true;
    }



    public static void insertData(List<?> s) {
//        for (int i = 0; i < 1000; i++) {
        daoSession.insert(s);
    }

    public static void initBoxNumber(int number) {
        for (int i = 1; i <= number; i++) {
            Box box = new Box(Long.parseLong(i + ""), 0, 0, 0, false);
            daoSession.insertOrReplace(box);
            android.util.Log.d("4567899qqqq", box.toString());
        }
    }
    public static void initBoxNumberJY() {
//        for (int i=1;i<=9;i++)
//        {
//            Box box = new Box(Long.parseLong(i + ""), 0, 0, 1, true);
//            daoSession.insertOrReplace(box);
//        }
//
//
//
//        for (int i = 11; i <= 15; i++) {
//            Box box1 = new Box(Long.parseLong(i + ""), 0, 0, 1, true);
//            daoSession.insertOrReplace(box1);
//        }
//        Box box2 = new Box((long)19, 0, 0, 1, false);
//        daoSession.insertOrReplace(box2);



        Box box16 = new Box((long)16, 0, 0, 0, true);
        Box box17 = new Box((long)17, 0, 0, 1, true);

        Box box18 = new Box((long)18, 0, 0, 0, true);
        Box box19 = new Box((long)19, 0, 0, 1, true);
        Box box20 = new Box((long)20, 0, 0, 0, true);



        daoSession.insertOrReplace(box16);
        daoSession.insertOrReplace(box17);
        daoSession.insertOrReplace(box18);
        daoSession.insertOrReplace(box19);
        daoSession.insertOrReplace(box20);



    }

    public static boolean insertUser(User user) {
        try {
            daoSession.insertOrReplace(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteUser(User user) {
        //Box box =queryBox(user.getBoxId());
            if (user.getBoxId()!=0) {
                Box box = new Box(Long.parseLong(user.getBoxId() + ""), 0, 0, 0, false);

//        box.setBind(false);
//        box.setStatus(0);
                daoSession.insertOrReplace(box);
                daoSession.delete(user);
            }else {
                daoSession.delete(user);
            }


//    for(int i=0;i<userList .size();i++)
//    {
//        UserDao userDao=daoSession.getUserDao();
//        userDao.delete(userList.get(i));
//    }
    }

    public static User queryUser(int id) {
        List<User> users = daoSession.loadAll(User.class);
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public static List queryAllUser() {
        List<User> users = daoSession.loadAll(User.class);
        return users;
    }

    public static List queryAllUserWithoutAdmin() {
        List<User> users = daoSession.loadAll(User.class);
        List<User> mUser=new ArrayList<User>();
        for(User user:users)
        {
            if (user.getId()!=0&&user.getController()==false)
                mUser.add(user);
//            if (user.getBoxId()==0||user.getController()==true||user.getName()==null||user.getId()==null)
//                users.remove(user);
        }
        return mUser;
    }

    public List queryLogAll(Date start, Date end) {                                             //查询全部记录
        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
        List<Log> logQueryBuilder = qb.where(LogDao.Properties.Time.ge(start), LogDao.Properties.Time.le(end)).list();
        return logQueryBuilder;
    }


    public User queryUser(long id) {          //查询用户
        android.util.Log.d("45678", id + "");
        List<User> userList = daoSession.loadAll(User.class);
        for (User user : userList) {
            if (user.getId() == id)
                return user;
        }
        return null;
        //    return users.get(0);
    }

    public static Box queryBox(long id) {    //传入boxID              //查询箱ID
//        List<Box> boxs = daoSession.queryRaw(Box.class, " where boxId = ?", id+"");
//        android.util.Log.d("45678999999",boxs.toString());
//        List<Box> boxList = daoSession.loadAll(Box.class);

        List<Box> boxs = daoSession.loadAll(Box.class);
        for (Box box : boxs) {
            if (box.getBoxId() == id) {
                android.util.Log.d("box_number_qqzzzzzzz", box.getBoxId() + " ");
                return box;
            }
        }
        return null;

        //   return boxList.get(0);
        //    return users.get(0);
    }


    public boolean queryBoxIfBind(long id) {    //传入boxID              //查询箱ID
//        List<Box> boxs = daoSession.queryRaw(Box.class, " where boxId = ?", id+"");
//        android.util.Log.d("45678999999",boxs.toString());
//        List<Box> boxList = daoSession.loadAll(Box.class);

        List<Box> boxs = daoSession.loadAll(Box.class);

        for (Box box : boxs) {
            if (box.getBoxId() == id && box.getBind() == true) {
                android.util.Log.d("box_number_qqzzzzzzz", box.getBoxId() + " ");
                return true;
            }
//            if (box.getBind()==true)
//            {
//                android.util.Log.d("box_number_qqzzzzzzz", box.getBoxId() + " ");
//                return box;
//            }
        }
        return false;
        //   return boxList.get(0);
        //    return users.get(0);
    }


    public static long DateToLong(Date date) {         //将日期返回为没有time的00：00时间，用于方便比较大小
        String year, month, day;
        year = date.getYear() + "";
        month = date.getMonth() + "";
        day = date.getDay() + "";
        if (date.getMonth() < 10)
            month = "0" + date.getMonth();
        if (date.getDay() < 10)
            day = "0" + date.getDay();
        String s = year + month + day;
        Long l = Long.parseLong(s);
        return l;
    }

    public static long TimeToLong(Time time) {         //将日期返回为没有time的00：00时间，用于方便比较大小
        String hour, minute, second;
        hour = time.getHours() + "";
        if (time.getMinutes() < 10) {
            minute = "0" + time.getMinutes();
        } else {
            minute = time.getMinutes() + "";
        }
        if (time.getSeconds() < 10) {
            second = "0" + time.getMinutes();
        } else {
            second = time.getMinutes() + "";
        }
        Long t = Long.getLong(hour + minute + second);
        return t;
    }

    public static boolean storageLog(int status, Date date, long userID) {
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        User u = new User();
        //    List<User> students = daoSession.queryRaw(User.class, " where id = ?", userID + "");
        List<User> users = daoSession.loadAll(User.class);
        for (User user : users) {
            if (user.getId() == userID) {
                u = user;
            }
        }
        //Log log = new Log( Long.parseLong(java.util.UUID.randomUUID().toString()), students.get(0).getBoxId(), userID, status, date.getTime());
        Log log = new Log(null, u.getBoxId(), userID, status, date.getTime(), null);
        //  Log log = new Log(, students.get(0).getBoxId(), userID, status, date.getTime());
        daoSession.insert(log);
        //  List<Log> logQueryBuilder = qb.where(LogDao.Properties.StorageTime.ge(start),LogDao.Properties.FetchTime.le(end)).list();
        // Log log=new Log(userID,, int userId,  status,date,
        // Date storageTime)
        return true;
    }


    public static boolean storageExceptionLog(int status, Date date, long userID, String remark) {
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        User u = new User();
        //    List<User> students = daoSession.queryRaw(User.class, " where id = ?", userID + "");
        List<User> users = daoSession.loadAll(User.class);
        for (User user : users) {
            if (user.getId() == userID) {
                u = user;
            }
        }
        ExceptionLog log;
        log = new ExceptionLog(null, u.getBoxId(), userID, status, date.getTime(), remark);


        //  Log log = new Log(, students.get(0).getBoxId(), userID, status, date.getTime());
        //  List<Log> logQueryBuilder = qb.where(LogDao.Properties.StorageTime.ge(start),LogDao.Properties.FetchTime.le(end)).list();
        // Log log=new Log(userID,, int userId,  status,date,
        // Date storageTime)
        return true;
    }





    public static boolean storageLogUrgent(int status,Date date,long userID,String actionName) //存储紧急开门状态
    {
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        User u = new User();
        //    List<User> students = daoSession.queryRaw(User.class, " where id = ?", userID + "");
        List<User> users = daoSession.loadAll(User.class);
        for (User user : users) {
            if (user.getId() == userID) {
                u = user;
            }
        }
        Log log = new Log(null, u.getBoxId(), userID, status, date.getTime(), actionName);
        daoSession.insert(log);
        return true;
    }

    public static boolean storageLogWithRemark(int status, Date date, long userID, String remark) //存储紧急开门状态
    {
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        User u = new User();
        //    List<User> students = daoSession.queryRaw(User.class, " where id = ?", userID + "");
        List<User> users = daoSession.loadAll(User.class);
        for (User user : users) {
            if (user.getId() == userID) {
                u = user;
            }
        }
        Log log = new Log(null, u.getBoxId(), userID, status, date.getTime(), remark);
        daoSession.insert(log);
        return true;
    }
    //public static boolean storageUrgentLog(int status,Date date,Long actionUserID,Box boxID){
//    List<User> students = daoSession.queryRaw(User.class, " where id = ?", actionUserID + "");
//    Log log = new Log( null , students.get(0).getBoxId(), userID, status, date.getTime(),null);
//        return true;
//}
    public static boolean changeBoxStatus(int status, Box box, long time) {
        //Log log = new Log( Long.parseLong(java.util.UUID.randomUUID().toString()), students.get(0).getBoxId(), userID, status, date.getTime());
        box.setStatus(status);
        if (status == 0 || status == 2) {
            box.setStorageTime(time);
        }
        if (status == 1 || status == 3) {
            box.setFetchTime(time);
        }
        daoSession.update(box);
        //  Log log = new Log(, students.get(0).getBoxId(), userID, status, date.getTime());
        //  List<Log> logQueryBuilder = qb.where(LogDao.Properties.StorageTime.ge(start),LogDao.Properties.FetchTime.le(end)).list();
        // Log log=new Log(userID,, int userId,  status,date,
        // Date storageTime)
        return true;
    }
    public static int checkStorageStatus(Date date, long userID) {


        Date startTimeAm;
        Date endTimeAM;
        Date startTimePM;
        Date endTimePM;
        UserPersonalTime upt = DbUtils.getInstance().queryUserPersonalTime(userID);
        Time nowTime = new Time(date.getHours(), date.getMinutes(), 0);
        if (upt != null) {


            android.util.Log.d("currentUPT", upt.getStartTimeAM() + "    " + upt.getEndTimeAM() + "   " + upt.getStartTimePM() + "       " + upt.getEndTimePM() + "   " + date.getTime());
            startTimeAm = new Date();
            startTimeAm.setHours(Integer.parseInt(upt.getStartTimeAM().split(":")[0]));
            startTimeAm.setMinutes(Integer.parseInt(upt.getStartTimeAM().split(":")[1]));


            endTimeAM = new Date();
            endTimeAM.setHours(Integer.parseInt(upt.getEndTimeAM().split(":")[0]));
            endTimeAM.setMinutes(Integer.parseInt(upt.getEndTimeAM().split(":")[1]));


            startTimePM = new Date();
            startTimePM.setHours(Integer.parseInt(upt.getStartTimePM().split(":")[0]));
            startTimePM.setMinutes(Integer.parseInt(upt.getStartTimePM().split(":")[1]));

            endTimePM = new Date();
            endTimePM.setHours(Integer.parseInt(upt.getEndTimePM().split(":")[0]));
            endTimePM.setMinutes(Integer.parseInt(upt.getEndTimePM().split(":")[1]));

            android.util.Log.d("currentUPT23333", nowTime.getTime() + "    " + endTimeAM.getTime() + "   " + startTimePM.getTime() + "       " + endTimePM.getTime() + "   " + date.getTime());

        } else {
            startTimeAm=new Date();
            startTimeAm.setHours(MyApplication.startTimeAM.getHours());
            startTimeAm.setMinutes(MyApplication.startTimeAM.getMinutes());

            endTimeAM=new Date();
            endTimeAM.setHours(MyApplication.endTimeAM.getHours());
            endTimeAM.setMinutes(MyApplication.endTimeAM.getMinutes());


            startTimePM=new Date();
            startTimePM.setHours(MyApplication.startTimePM.getHours());
            startTimePM.setMinutes(MyApplication.startTimePM.getMinutes());

            endTimePM=new Date();
            endTimePM.setHours(MyApplication.endTimePM.getHours());
            endTimePM.setMinutes(MyApplication.endTimePM.getMinutes());
        }
        if (booleanExistTimesTodayLog(DbUtils.getInstance().queryUser(userID).getBoxId()))
            return 5;
        if (date.after(startTimeAm) && date.before(endTimeAM))
            return 2;
        if (date.after(startTimePM) && date.before(endTimePM))
            return 2;
        else return 0;



    }

    public static boolean isStorage(Date date, long userID)        //  判断是否存入手机
    {
//    QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
//       QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Status.eq(STORAGE_NORMAL_STATUS),qb.and(LogDao.Properties.StorageTime.gt(getCurrentDayMillWithoutTime())));
//          List<Log> logList = logQueryBuilder.list(); //查出当前对应的数据

        return true;

    }

    public static boolean booleanFetch(Context context, User user) {         //判断是否可以取出手机
//        QueryBuilder<UserPersonalTime> qb = daoSession.queryBuilder(UserPersonalTime.class);
//        QueryBuilder<UserPersonalTime> up = qb.where(UserPersonalTimeDao.Properties.Id.eq(user.getId()));
//        List<UserPersonalTime> list = up.list();
//        UserPersonalTime userPersonalTime = list.get(0);
        Date startTimeAM, endTimeAM;
        Date startTimePM;
        Date endTimePM;
        UserPersonalTime upt=DbUtils.getInstance().queryUserPersonalTime(user.getId());
        Date date = new Date();
        Time nowTime = new Time(date.getHours(),date.getMinutes(),0);
        if (upt!=null) {
            android.util.Log.d("currentUPT", upt.getStartTimeAM()+"    "+upt.getEndTimeAM()+"   "+upt.getStartTimePM()+"       "+upt.getEndTimePM()+"   "+date.getTime());

            startTimeAM = new Date();
            startTimeAM.setHours(Integer.parseInt(upt.getStartTimeAM().split(":")[0]));
            startTimeAM.setMinutes(Integer.parseInt(upt.getStartTimeAM().split(":")[1]));


            endTimeAM=new Date();
            endTimeAM.setHours(Integer.parseInt(upt.getEndTimeAM().split(":")[0]));
            endTimeAM.setMinutes(Integer.parseInt(upt.getEndTimeAM().split(":")[1]));


            startTimePM=new Date();
            startTimePM.setHours(Integer.parseInt(upt.getStartTimePM().split(":")[0]));
            startTimePM.setMinutes(Integer.parseInt(upt.getStartTimePM().split(":")[1]));

            endTimePM=new Date();
            endTimePM.setHours(Integer.parseInt(upt.getEndTimePM().split(":")[0]));
            endTimePM.setMinutes(Integer.parseInt(upt.getEndTimePM().split(":")[1]));

            android.util.Log.d("currentUPT23333", nowTime.getTime()+"    "+endTimeAM.getTime()+"   "+startTimePM.getTime()+"       "+endTimePM.getTime()+"   "+date.getTime());

            if (date.before(startTimeAM))
                return true;
            if (date.after(endTimeAM) && date.before(startTimePM))
                return true;
            if (date.after(endTimePM))
                return true;

        }
        if (upt==null)
        {
            startTimeAM = new Date();
            startTimeAM.setHours(MyApplication.endTimeAM.getHours());
            startTimeAM.setMinutes(MyApplication.endTimeAM.getMinutes());


            endTimeAM=new Date();
            endTimeAM.setHours(MyApplication.endTimeAM.getHours());
            endTimeAM.setMinutes(MyApplication.endTimeAM.getMinutes());


            startTimePM=new Date();
            startTimePM.setHours(MyApplication.startTimePM.getHours());
            startTimePM.setMinutes(MyApplication.startTimePM.getMinutes());

            endTimePM=new Date();
            endTimePM.setHours(MyApplication.endTimePM.getHours());
            endTimePM.setMinutes(MyApplication.endTimePM.getMinutes());

            android.util.Log.d("currentUPT34445556", nowTime.getTime()+"    "+endTimeAM.getTime()+"   "+startTimePM.getTime()+"       "+endTimePM.getTime()+"   "+date.getTime());
            if (date.before(startTimeAM))
                return true;
                if (date.after(endTimeAM) && date.before(startTimePM))
                    return true;
                if (date.after(endTimePM))
                    return true;
            }

//            if (nowTime>endTimeAM&&nowTime<startTimePM)
//            return true;
//            if (nowTime>endTimePM)
//                return true;
                return false;
//            if (date.getHours() >= Integer.parseInt(upt.getEndTimeAM().split(":")[0]) && date.getMinutes() >= Integer.parseInt(upt.getEndTimeAM().split(":")[1]) && date.getHours() <= Integer.parseInt(upt.getStartTimePM().split(":")[0]) && date.getMinutes() <= Integer.parseInt(upt.getStartTimePM().split(":")[1]))
//                return true;
//            if (date.getHours() >= Integer.parseInt(upt.getEndTimePM().split(":")[0]) && date.getMinutes() >= Integer.parseInt(upt.getEndTimePM().split(":")[1]))
//                return true;
//
//            if (date.getHours() >= MyApplication.endTimeAM.getHours() && date.getMinutes() >= MyApplication.endTimeAM.getMinutes() && date.getHours() <= MyApplication.startTimePM.getHours() && date.getMinutes() <= MyApplication.startTimePM.getMinutes())
//                return true;
//            if (date.getHours() >= MyApplication.endTimePM.getHours() && date.getMinutes() >= MyApplication.endTimePM.getMinutes())
//                return true;
//        }
//            return false;
    }

    public static long getCurrentDayMillWithoutTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date = new Date();
        df.format(date);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        android.util.Log.d("99999999999999999", date.toString() + "             " + date.getTime());
        return date.getTime();
    }
//


    public List queryLogExc(Date start, Date end) {                 //返回紧急取件人员列表
        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
        QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Status.eq(3)).orderAsc(LogDao.Properties.LogId);
        List<Log> logList = logQueryBuilder.list(); //查出当前对应的数据
        return logList;
    }

    public List<Log> queryLog(long startTime, long endTime) {
        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
        QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Status.eq(3)).orderAsc(LogDao.Properties.LogId);
        QueryBuilder<Log> logs = daoSession.queryBuilder(Log.class);
//        QueryBuilder<Student> studentQueryBuilder = qb.where(StudentDao.Properties.Name.eq("一")).orderAsc(StudentDao.Properties.Name);
//        List<Student> studentList = studentQueryBuilder.list(); //查出当前对应的数据
        return null;
    }

    public static boolean booleanExistTimesTodayLog(long boxID) {

        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
        List<Log> logList = qb.where(LogDao.Properties.BoxId.eq(boxID), qb.and(LogDao.Properties.Time.gt(TimeUtil.getTodayStart()),
                LogDao.Properties.Time.le(TimeUtil.getTodayEnd()))).list();
        if (logList.size() > 4)
            return true;
        else return false;
//        QueryBuilder<Student> studentQueryBuilder = qb.where(StudentDao.Properties.Name.eq("一")).orderAsc(StudentDao.Properties.Name);
//        List<Student> studentList = studentQueryBuilder.list(); //查出当前对应的数据
    }
//
//    public List queryLogExc(){                 //返回意外取出人员列表
//        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
//        QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Status.eq(3)).orderAsc(LogDao.Properties.LogId);
//        List<Log> logList = logQueryBuilder.list(); //查出当前对应的数据
//        return logList;
//    }


    //    public void queryLogByTime(Date start,Date end)  //查询log 根据时间
//    {
//        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
//   //     QueryBuilder<Log> studentQueryBuilder = qb.where(UserDao.Properties.eq("一")).orderAsc(UserDao.Properties.Name);
//     //   List<Log> studentList = studentQueryBuilder.list(); //查出当前对应
//    }
//    public void queryLog
//
//    }
    public static List<DayLogBean> initDayLog() {
        List<User> list;
        List<DayLogBean> dayLogBeans = new ArrayList<>();
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        list = qb.list();
        for (User user : list) {
            if (user.getBoxId()!=0) {
                DayLogBean dayLogBean = new DayLogBean();
                dayLogBean.setId(user.getId());
                dayLogBean.setName(user.getName());
                dayLogBean.setDoorNumber(user.getBoxId());
                dayLogBean.setDepartment(user.getDepartment());
                dayLogBeans.add(dayLogBean);
            }
        }
        return dayLogBeans;
    }


    public static List<WeekLogBean> initWeekLog() {
        List<User> list;
        List<WeekLogBean> weekLogBeans = new ArrayList<>();
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        list = qb.list();
        for (User user : list) {
            if (user.getBoxId()!=0) {
                WeekLogBean weekLogBean = new WeekLogBean();
                weekLogBean.setId(user.getId());
                weekLogBean.setName(user.getName());
                weekLogBean.setDoorNumber(user.getBoxId());
                weekLogBean.setDepartment(user.getDepartment());
                weekLogBeans.add(weekLogBean);
            }
        }
        return weekLogBeans;
    }

    public static List<MonthLogBean> initMonthLog() {
        List<User> list;
        List<MonthLogBean> monthLogBeans = new ArrayList<>();
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        list = qb.list();
        for (User user : list) {
            if (user.getBoxId()!=0) {
                MonthLogBean monthLogBean = new MonthLogBean();
                monthLogBean.setId(user.getId());
                monthLogBean.setName(user.getName());
                monthLogBean.setDoorNumber(user.getBoxId());
                monthLogBean.setDepartment(user.getDepartment());
                monthLogBeans.add(monthLogBean);
            }
        }
        return monthLogBeans;
    }

    public static List<DayLogBean> dayLog(long startTime, long endTime) {             //返回日查询情况
        List<DayLogBean> dayLogBeans = initDayLog();
//List<DayLogBean> dayLogBeans=new L;
        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
        QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Time.between(Long.toString(startTime), Long.toString(endTime)));

        //  QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Time.between(0, "1570723200794"));
        List<Log> logs = logQueryBuilder.list();

        //   android.util.Log.d("boxDayLog777", logs.get(0).getTime()+"");
        android.util.Log.d("boxDayLog777", logs.size() + "");
        for (Log log : logs) {
        //    for (DayLogBean dayLogBean : dayLogBeans) {
                for (int i = 0; i < dayLogBeans.size(); i++) {
                    if (dayLogBeans.get(i).getId() == log.getUserId()) {
                        DayLogBean temp = dayLogBeans.get(i);
                        switch (log.getStatus()) {
                            case 0: {
                                try {
                                    if (temp.getStoreTime() == null)
                                        temp.setStoreTime(transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    else {
                                        temp.setStoreTime(temp.getStoreTime() + "    " + transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    }

                                    if (temp.getStoreStatus()==null)
                                    temp.setStoreStatus("正常");
                                    else {
                                        temp.setStoreStatus(temp.getStoreStatus()+",正常");
                                    }
                                    dayLogBeans.set(i, temp);
                                } catch (Exception e) {
                                    android.util.Log.d("Time_error_2333", e.toString());

                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 1: {
                                try {
                                    if (temp.getFetchTime() == null) {
                                        temp.setFetchTime(transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    } else {
                                        temp.setFetchTime(temp.getFetchTime() + "    " + transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    }


                                    if (temp.getFetchStatus()==null)
                                        temp.setFetchStatus("正常");
                                    else {
                                        temp.setFetchStatus(temp.getFetchStatus()+",正常");
                                    }
                                    dayLogBeans.set(i, temp);
                                } catch (Exception e) {
                                    android.util.Log.d("Time_error", e.toString());
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 2: {
                                try {
                                    if (temp.getStoreTime() == null)
                                        temp.setStoreTime(transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    else {
                                        temp.setStoreTime(temp.getStoreTime() + "    " + transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    }



                                    if (temp.getStoreStatus()==null)
                                        temp.setStoreStatus("超时");
                                    else {
                                        temp.setStoreStatus(temp.getStoreStatus()+",超时");
                                    }
                                    dayLogBeans.set(i, temp);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    android.util.Log.d("Time_error_22222", e.toString());
                                }
                                break;
                            }
                            case 3: {
                                try {
                                    if (temp.getFetchTime() == null) {
                                        temp.setFetchTime(transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    } else {
                                        temp.setFetchTime(temp.getFetchTime() + "    " + transferLongToDate("MM月dd日 HH时mm分ss秒", log.getTime()));
                                    }



                                    if (temp.getFetchStatus()==null)
                                        temp.setFetchStatus("紧急");
                                    else {
                                        temp.setFetchStatus(temp.getFetchStatus()+",紧急");
                                    }
                                    temp.setAuthor(log.getActionUserName());



                                    dayLogBeans.set(i, temp);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    android.util.Log.d("Time_error", e.toString());
                                }
                                break;
                            }
                            case 4: {
                                try {
                                    temp.setStoreStatus("未存");
                                    temp.setFetchStatus("未取");
                                    dayLogBeans.set(i, temp);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    android.util.Log.d("Time_error", e.toString());
                                }
                                break;
                            }
                            case 5: {
                                try {
                                    temp.setRemark("多次存取");
                                    dayLogBeans.set(i, temp);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    android.util.Log.d("Time_error", e.toString());
                                }
                                break;
                            }


                        }
                    }
                }
            //        }
        }


//List<DayLogBean> dayLogBeans=new L;
        QueryBuilder<ExceptionLog> exqb = daoSession.queryBuilder(ExceptionLog.class);
        QueryBuilder<ExceptionLog> exLogQueryBuilder = exqb.where(ExceptionLogDao.Properties.Time.between(Long.toString(startTime), Long.toString(endTime)));
        List<ExceptionLog> exLogs = exLogQueryBuilder.list();
        for (ExceptionLog log : exLogs) {
            for (int i = 0; i < dayLogBeans.size(); i++) {
                if (dayLogBeans.get(i).getId() == log.getUserId()) {
                    DayLogBean temp = dayLogBeans.get(i);

                    try {
                        temp.setRemark(temp.getRemark() + "|" + log.getRemark());
                        dayLogBeans.set(i, temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        android.util.Log.d("Time_error", e.toString());
                    }
                    break;

                }
            }
        }









        return dayLogBeans;
    } //日表


    public static List<WeekLogBean> weekLog(long startTime, long endTime) {             //返回日查询情况

        List<WeekLogBean> weekLogBeans = initWeekLog();
//List<DayLogBean> dayLogBeans=new L;
        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
        QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Time.between(startTime, endTime));
        List<Log> logs = logQueryBuilder.list();
        for (Log log : logs) {
            for (int i = 0; i < weekLogBeans.size(); i++) {
                if (weekLogBeans.get(i).getId() == log.getUserId()) {
                    WeekLogBean temp = weekLogBeans.get(i);
                    switch (log.getStatus()) {
                        case 0: {
                            temp.setStorageNormalTimeTotal(temp.getStorageNormalTimeTotal() + 1);
                            weekLogBeans.set(i, temp);
                            break;
                        }
                        case 1: {

                            temp.setFetchNormalTimeTotal(temp.getFetchNormalTimeTotal() + 1);
                            weekLogBeans.set(i, temp);
                            break;
                        }
                        case 2: {

                            temp.setDelayStorageTimeTotal(temp.getDelayStorageTimeTotal() + 1);
                            if (temp.getDelayDays() == null) {
                                temp.setDelayDays(transferLongToDate("MM月dd日 ", log.getTime()));
                            } else {
                                temp.setDelayDays(temp.getDelayDays() + " " + transferLongToDate("MM月dd日 ", log.getTime()));
                            }
                            weekLogBeans.set(i, temp);
                            break;
                        }
                        case 3: {
                            try {

                                temp.setFetchUrgentTimeTotal(temp.getFetchUrgentTimeTotal() + 1);
//                                    if (temp.getFetchUrgentDays().equals("null"))
//                                        temp.setFetchUrgentDays(temp.getFetchUrgentDays());
//                                    else {
                                if (temp.getFetchUrgentDays() == null) {
                                    temp.setFetchUrgentDays(transferLongToDate("MM月dd日", log.getTime()));
                                } else {
                                    temp.setFetchUrgentDays(temp.getFetchUrgentDays() + " " + transferLongToDate("MM月dd日", log.getTime()));
                                }


                                weekLogBeans.set(i, temp);
                            } catch (Exception e) {
                                e.printStackTrace();
                                android.util.Log.d("Time_error", e.toString());
                            }
                            break;
                        }
                        case 4: {
                            try {
                                temp.setNotStorageTime(temp.getNotStorageTime() + 1);


                                if (temp.getNotStorageDays() == null) {
                                    temp.setNotStorageDays(transferLongToDate("MM月dd日", log.getTime()));
                                } else {
                                    temp.setNotStorageDays(temp.getNotStorageDays() + " " + transferLongToDate("MM月dd日", log.getTime()));
                                }
                                weekLogBeans.set(i, temp);
                            } catch (Exception e) {
                                e.printStackTrace();
                                android.util.Log.d("Time_error", e.toString());
                            }
                            break;
                        }
                        case 5: {
                            try {

                                temp.setTwiceFetchTime(temp.getTwiceFetchTime() + 1);

                                if (temp.getTwiceFetchDays() == null) {
                                    temp.setTwiceFetchDays(transferLongToDate("MM月dd日", log.getTime()));
                                } else {
                                    temp.setTwiceFetchDays(temp.getTwiceFetchDays() + " " + transferLongToDate("MM月dd日", log.getTime()));
                                }
                                weekLogBeans.set(i, temp);
                            } catch (Exception e) {
                                e.printStackTrace();
                                android.util.Log.d("Time_error", e.toString());
                            }
                            break;
                        }
                    }
                }
            }
        }

        return weekLogBeans;
    } //周表


    public static List<MonthLogBean> monthLog(long startTime, long endTime) {             //返回日查询情况
        List<MonthLogBean> monthLogBeans = initMonthLog();
//List<DayLogBean> dayLogBeans=new L;
        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
        QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Time.between(startTime, endTime));
        List<Log> logs = logQueryBuilder.list();
        for (Log log : logs) {
            for (int i = 0; i < monthLogBeans.size(); i++) {
                if (monthLogBeans.get(i).getId() == log.getUserId()) {
                    MonthLogBean temp = monthLogBeans.get(i);
                    switch (log.getStatus()) {
                        case 0: {
                            temp.setMonthTimeTotal(temp.getMonthTimeTotal() + 1);
                            temp.setNormalTimeTotal(temp.getNormalTimeTotal() + 1);
                            monthLogBeans.set(i, temp);
                            break;
                        }
                        case 1: {
                            temp.setFetchNormalTime(temp.getFetchNormalTime() + 1);
                            monthLogBeans.set(i, temp);
                            break;
                        }
                        case 2: {
                            temp.setMonthTimeTotal(temp.getMonthTimeTotal() + 1);
                            temp.setDelayTimeTotal(temp.getDelayTimeTotal() + 1);
                            if (temp.getDelayDays() == null) {
                                temp.setDelayDays(transferLongToDate("MM月dd日 ", log.getTime()));
                            } else {
                                temp.setDelayDays(temp.getDelayDays() + " " + transferLongToDate("MM月dd日 ", log.getTime()));
                            }
                            monthLogBeans.set(i, temp);
                            break;
                        }
                        case 3: {
                            try {
                                temp.setUrgentFetchTime(temp.getUrgentFetchTime() + 1);
                                if (temp.getUrgentFetchDays() == null) {
                                    temp.setUrgentFetchDays(transferLongToDate("MM月dd日 ", log.getTime()));
                                } else {
                                    temp.setUrgentFetchDays(temp.getUrgentFetchDays() + " " + transferLongToDate("MM月dd日 ", log.getTime()));
                                }
                                monthLogBeans.set(i, temp);
                            } catch (Exception e) {
                                e.printStackTrace();
                                android.util.Log.d("Time_error", e.toString());
                            }
                            break;
                        }
                        case 4: {
                            try {
                                temp.setNotStorageTime(temp.getNotStorageTime() + 1);
                                if (temp.getNotStorageDays() == null) {
                                    temp.setNotStorageDays(transferLongToDate("MM月dd日", log.getTime()));
                                } else {
                                    temp.setNotStorageDays(temp.getNotStorageDays() + " " + transferLongToDate("MM月dd日", log.getTime()));
                                }
                                monthLogBeans.set(i, temp);
                            } catch (Exception e) {
                                e.printStackTrace();
                                android.util.Log.d("Time_error", e.toString());
                            }
                            break;
                        }
                        case 5: {
                            try {
                                temp.setMonthTimeTotal(temp.getMonthTimeTotal() + 1);
                                temp.setTwiceStorageTime(temp.getTwiceStorageTime() + 1);

                                if (temp.getTwiceStorageDays() == null) {
                                    temp.setTwiceStorageDays(transferLongToDate("MM月dd日", log.getTime()));
                                } else {
                                    temp.setTwiceStorageDays(temp.getTwiceStorageDays() + " " + transferLongToDate("MM月dd日", log.getTime()));
                                }
                                monthLogBeans.set(i, temp);
                            } catch (Exception e) {
                                e.printStackTrace();
                                android.util.Log.d("Time_error", e.toString());
                            }
                            break;
                        }
                    }
                }
            }
        }

        return monthLogBeans;
    } //日表

    public void bindBox(int boxID) {
        Box box = queryBox(boxID);
        if (box != null)
            box.setBind(true);
        daoSession.update(box);
        android.util.Log.d("box+++++", DbUtils.queryBox(boxID).getBind() + "");


    }

    public static ArrayList<String> getUnbindNumber() {     //获取未绑定的箱号
        ArrayList<String> unbindBoxes = new ArrayList<>();
        List<Box> boxes = daoSession.loadAll(Box.class);
        for (Box box : boxes) {
            if (box.getBind() == false) {
                unbindBoxes.add(box.getBoxId() + "");
                android.util.Log.d("box23333", box.getBoxId()+"");
            }
        }
        return unbindBoxes;
    }

    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

//    public void checkDayLog(){
//
//
//
//        QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
//        List<Log> logQueryBuilder = qb.where(LogDao.Properties.Time.ge(start), LogDao.Properties.Time.le(end)).list();
//        return logQueryBuilder;
//        return true;
//    }

    public void DailySummary() {          //进行一天行为总结
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

            Date dateStart = new Date();
            dateStart.setHours(0);
            dateStart.setMinutes(0);
            dateStart.setSeconds(0);
            Date dateEnd = new Date();
            dateEnd.setHours(23);
            dateEnd.setMinutes(59);
            dateEnd.setSeconds(59);
            QueryBuilder<Log> qb = daoSession.queryBuilder(Log.class);
            QueryBuilder<Log> logQueryBuilder = qb.where(LogDao.Properties.Time.between(dateStart, dateEnd));
//    QueryBuilder<User> user = daoSession.queryBuilder(User.class);
//    List <User> users=user.list();
            int doorNumber = DbUtils.getDoorNumber();
            int[] arr = new int[doorNumber];  //记录是否为空状态
            for (int i = 0; i < doorNumber; i++) {
                arr[i] = 0;
            }
            List<Log> logs = logQueryBuilder.list();
            for (Log log : logs) {
                if (log.getStatus() == 0 || log.getStatus() == 2) {
                    arr[log.getBoxId() - 1] = arr[log.getBoxId() - 1] + 1;
                }
                for (int i = 0; i < doorNumber; i++) {
                    if (arr[i] == 0) {
                        long userID = getUserIdByBoxId(arr[i]);
                        if (userID != -1)
                            storageLog(4, new Date(), userID);
                    }
                }
            }

        }
    }


    public long getUserIdByBoxId(int boxID) {
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        List<User> users = qb.list();
        for (User user : users) {
            if (user.getBoxId() == boxID) {
                return user.getId();
            }
        }
        return -1;
    }

    public List<Box> loadAllBox() {
        QueryBuilder<Box> qb = daoSession.queryBuilder(Box.class);
        List<Box> boxes = qb.list();
        return boxes;
    }

    public void addAdmin(int id) {
        QueryBuilder<User> qb = daoSession.queryBuilder(User.class);
        List<User> users = qb.list();
        for (User user : users) {
            if (user.getId() == id) {
                user.setController(true);
                android.util.Log.i("onPermissionsResult:", user.getId() + "");
                daoSession.update(user);
                android.util.Log.i("onPermissionsResult:", "用户权限提升失败");
            }
        }
    }

    public List<User> getAdminUser() {
        List<User> qb = daoSession.loadAll(User.class);
        List<User> users = new ArrayList<>();
        for (User user : qb) {
            if (user.getController() == true) {
                users.add(user);
            }
        }

        return users;
    }

    public void removeAdminUser(int userID) {
        List<User> qb = daoSession.loadAll(User.class);
        List<User> users = new ArrayList<>();
        for (User user : qb) {
            if (user.getController() == true) {
                user.setController(false);
                daoSession.update(user);
            }
        }
    }

    public void insertUserSelfTime(long userID, String startTimeAM, String endTimeAM, String startTimePM, String endTimePM) {
        UserPersonalTime userPersonalTime = new UserPersonalTime();
        userPersonalTime.setId(userID);
        userPersonalTime.setStartTimeAM(startTimeAM);
        userPersonalTime.setEndTimeAM(endTimeAM);
        userPersonalTime.setStartTimePM(startTimePM);
        userPersonalTime.setEndTimePM(endTimePM);
        daoSession.insertOrReplace(userPersonalTime);
    }

    public UserPersonalTime queryUserPersonalTime(long userID) {

        //       List<UserPersonalTime> lists=daoSession.loadAll(UserPersonalTime.class);


        QueryBuilder<UserPersonalTime> qb = daoSession.queryBuilder(UserPersonalTime.class);
        if (qb.list() != null) {
            List<UserPersonalTime> lists = qb.list();
            if (lists.size() != 0)
                for (UserPersonalTime up : lists) {
                    if (up.getId() == userID) {
                        return up;
                    }
                }
        }
        return null;

    }

    public int getUserNumber() {
        List<User> users = daoSession.loadAll(User.class);
        return users.size();
    }


}


package com.wandao.myapplication.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.wandao.myapplication.bean.DayLogBean;
import com.wandao.myapplication.bean.DemoBean;
import com.wandao.myapplication.bean.LogBean;
import com.wandao.myapplication.bean.MonthLogBean;
import com.wandao.myapplication.bean.WeekLogBean;
import com.wandao.myapplication.greendao.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
    private static String filePath = Environment.getExternalStorageDirectory() + "/WDExcel";
    private static String fileDayPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/日报表";
    private static String fileWeekPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/周报表";
    private static String fileMonthPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/月报表";

    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";


    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化Excel表格
     *
     * @param filePath  存放excel文件的路径（path/demo.xls）
     * @param sheetName Excel表格的表名
     * @param colName   excel中包含的列名（可以有多个）
     */
    public static void initExcel(String filePath, String sheetName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                return;
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, filePath, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将制定类型的List写入Excel中
     *
     * @param objList  待写入的list
     * @param fileName
     * @param c
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);

                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < objList.size(); j++) {           //将类型写入到excel  坑啊！！！！！！！！！！
                    LogBean logBean = (LogBean) objList.get(j);
                    List<String> list = new ArrayList<>();
                    list.add(logBean.getName());
                    list.add(String.valueOf(logBean.getId()));
                    list.add(String.valueOf(logBean.isUnusual()));
                    list.add(String.valueOf(logBean.getStoreTime()));
                    list.add(String.valueOf(logBean.getPickTime()));
                    list.add(String.valueOf(logBean.getBoxNumber()));
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }


                writebook.write();
                workbook.close();
                Toast.makeText(c, "导出Excel成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    public static <T> void writeDayObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);

                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {           //将类型写入到excel  坑啊！！！！！！！！！！
                    DayLogBean dayLogBean = (DayLogBean) objList.get(j);
                    List<String> list = new ArrayList<>();
                    list.add(dayLogBean.getId()+"");
                    list.add(String.valueOf(dayLogBean.getDepartment()));
                    list.add(String.valueOf(dayLogBean.getName()));
                    list.add(String.valueOf(dayLogBean.getDoorNumber()));
                    list.add(String.valueOf(dayLogBean.getStoreTime()));
                    list.add(String.valueOf(dayLogBean.getStoreStatus()));
                    list.add(String.valueOf(dayLogBean.getFetchTime()));
                    list.add(String.valueOf(dayLogBean.getFetchStatus()));
                    list.add(String.valueOf(dayLogBean.getAuthor()));
                    list.add(String.valueOf(dayLogBean.getRemark()));
                    Log.d("objList", dayLogBean.getStoreTime() + "");

                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }


                writebook.write();
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    public static <T> void writeWeekObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);

                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < objList.size(); j++) {           //将类型写入到excel  坑啊！！！！！！！！！！
                    WeekLogBean weekLogBean = (WeekLogBean) objList.get(j);
                    List<String> list = new ArrayList<>();
                    list.add(weekLogBean.getId()+"");   //工号
                    list.add(String.valueOf(weekLogBean.getDepartment()));  //部门
                    list.add(String.valueOf(weekLogBean.getName())); //姓名
                    list.add(String.valueOf(weekLogBean.getDoorNumber())); //柜门号
                    list.add(String.valueOf(weekLogBean.getStorageNormalTimeTotal()));  //正常存入次数
                    list.add(String.valueOf(weekLogBean.getDelayStorageTimeTotal()));   //超时存入次数
                    list.add(String.valueOf(weekLogBean.getDelayDays()));    //超时存入日期
                    list.add(String.valueOf(weekLogBean.getNotStorageTime())); //未存次数
                    list.add(String.valueOf(weekLogBean.getNotStorageDays()));  //未存日期
                    list.add(String.valueOf(weekLogBean.getFetchNormalTimeTotal()));  //正常取出
                    list.add(String.valueOf(weekLogBean.getFetchUrgentTimeTotal()));  //应急取出
                    list.add(String.valueOf(weekLogBean.getFetchUrgentDays()));  //应急取出日期
                    list.add(String.valueOf(weekLogBean.getTwiceFetchTime())); //二次存取次数
                    list.add(String.valueOf(weekLogBean.getTwiceFetchDays())); //二次存取日期
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }
                writebook.write();
                workbook.close();
                Toast.makeText(c, "导出周Excel成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }



    public static <T> void writeMonthObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);

                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < objList.size(); j++) {           //将类型写入到excel  坑啊！！！！！！！！！！
                    MonthLogBean monthLogBean = (MonthLogBean) objList.get(j);
                    List<String> list = new ArrayList<>();
                    list.add(monthLogBean.getId()+"");   //工号
                    list.add(String.valueOf(monthLogBean.getDepartment()));  //部门
                    list.add(String.valueOf(monthLogBean.getName())); //姓名
                    list.add(String.valueOf(monthLogBean.getDoorNumber())); //柜门号
                    list.add(String.valueOf(monthLogBean.getMonthTimeTotal()));  //正常存入次数
                    list.add(String.valueOf(monthLogBean.getNormalTimeTotal()));   //正常存入次数
                    list.add(String.valueOf(monthLogBean.getDelayTimeTotal()));    //超时存入次数
                    list.add(String.valueOf(monthLogBean.getDelayDays())); //超时存入日期
                    list.add(String.valueOf(monthLogBean.getNotStorageTime()));  //未存次数
                    list.add(String.valueOf(monthLogBean.getNotStorageDays()));  //未存日期
                    list.add(String.valueOf(monthLogBean.getFetchNormalTime()));  //正常取出
                    list.add(String.valueOf(monthLogBean.getUrgentFetchTime()));  //应急取出
                    list.add(String.valueOf(monthLogBean.getUrgentFetchDays())); //应急取出日期
                    list.add(String.valueOf(monthLogBean.getTwiceStorageTime())); //二次存取次数
                    list.add(String.valueOf(monthLogBean.getTwiceStorageDays())); //二次存取日期
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }
                writebook.write();
                workbook.close();
                Toast.makeText(c, "导出月Excel成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }



    private void exportExcel(Context context, String filePath, String []title1, List<User> user) {


        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }


        String excelFileName = "/demo.xls";


        String[] title = {"姓名", "年龄", "男孩"};
        String sheetName = "demoSheetName";


        List<DemoBean> demoBeanList = new ArrayList<>();
        DemoBean demoBean1 = new DemoBean("张三", 10, true);
        DemoBean demoBean2 = new DemoBean("小红", 12, false);
        DemoBean demoBean3 = new DemoBean("李四", 18, true);
        DemoBean demoBean4 = new DemoBean("王香", 13, false);
        demoBeanList.add(demoBean1);
        demoBeanList.add(demoBean2);
        demoBeanList.add(demoBean3);
        demoBeanList.add(demoBean4);
        filePath = filePath + excelFileName;


        ExcelUtil.initExcel(filePath, sheetName, title);


        ExcelUtil.writeObjListToExcel(demoBeanList, filePath, context);

  //      textView.setText("excel已导出至：" + filePath);

    }

    public static void exportDayExcel(Context context,String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd号 E");

        String format = simpleDateFormat.format(new Date());
        String[] title_day = {"工号", "部门", "姓名", "柜门号", "存入时间", "存入状态", "取出时间", "取出状态", "授权人", "异常存取原因"};
        String sheetNameDay = "日报";
        String excelDayFileName = "/日报"+format+".xls";
        ExcelUtil.initExcel(filePath + excelDayFileName, sheetNameDay, title_day);
        Date dateStart=new Date();
        dateStart.setHours(0);
        dateStart.setMinutes(0);
        dateStart.setSeconds(0);
        Date dateEnd=new Date();
        dateEnd.setHours(23);
        dateEnd.setMinutes(59);
        dateEnd.setSeconds(59);
       ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(dateStart.getTime(),dateEnd.getTime()), filePath+ excelDayFileName, context);
  //      ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(0,1000000000), filePath+ excelDayFileName, context);

    }

    public static void exportDiyDayExcel(Context context, long choose) {      //导出自选日报表
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd号 E");

        String format = simpleDateFormat.format(new Date(choose));
        String[] title_day = {"工号", "部门", "姓名", "柜门号", "存入时间", "存入状态", "取出时间", "取出状态", "授权人", "异常存取原因"};
        String sheetNameDay = "日报";
        String excelDayFileName = "/日报" + format + ".xls";
        ExcelUtil.initExcel(filePath + excelDayFileName, sheetNameDay, title_day);
        Date dateStart = new Date(choose);
        dateStart.setHours(0);
        dateStart.setMinutes(0);
        dateStart.setSeconds(0);
        Date dateEnd = new Date(choose);
        dateEnd.setHours(23);
        dateEnd.setMinutes(59);
        dateEnd.setSeconds(59);
        ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(dateStart.getTime(), dateEnd.getTime()), filePath + excelDayFileName, context);
        //      ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(0,1000000000), filePath+ excelDayFileName, context);

    }


    public static void exportMetaExcel(Context context, long start, long end) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] title_day = {"工号", "部门", "姓名", "柜门号", "存入时间", "存入状态", "取出时间", "取出状态", "授权人", "异常存取原因"};
        String sheetNameDay = "元数据";
        String excelMetaFileName = "/自定义时间元数据表.xls";
        ExcelUtil.initExcel(filePath + excelMetaFileName, sheetNameDay, title_day);
        //   ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(start,end), filePath+ excelMetaFileName, context);
        ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(start, end), filePath + excelMetaFileName, context);
    }

    public static void exportDiyWeekExcel(Context context, int week) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] title_week = {"工号", "部门", "姓名", "柜门号", "正常存入次数", "超时存入次数", "超时存入日期", "未存次数", "未存日期", "正常取出", "应急取出", "应急取出日期", "多次存取次数", "多次存取日期"};
        String sheetNameDiyWeek = "第" + week + "周数据";
        String excelDiyWeekFileName = "/手动导出第" + week + "周数据表.xls";
        ExcelUtil.initExcel(filePath + excelDiyWeekFileName, sheetNameDiyWeek, title_week);
        //   ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(start,end), filePath+ excelMetaFileName, context);
        ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getTargetWeekMonday(week).getTime(), getThisTargetWeekFriday(week).getTime()), filePath + excelDiyWeekFileName, context);
    }
    public static void exportDiyExcel(Context context,long start,long end) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] title_day={"工号","部门","姓名","柜门号","存入时间","存入状态","取出时间","取出状态","应急取","备注"};
        String sheetNameDay = "自定义时间导出";
        String excelDayFileName = "/万道自定义时间报表.xls";
        ExcelUtil.initExcel(filePath + excelDayFileName, sheetNameDay, title_day);
        ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(start,end), filePath+ excelDayFileName, context);

    }


    public static void exportWeekExcel(Context context,String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
    //    String[] title_week={"工号","部门","姓名","柜门号","存入时间","存入状态","取出时间","取出状态","应急取","备注"};
        String sheetNameWeek = "周报";
        String excelWeekFileName = "/周报"+calendar.get(Calendar.WEEK_OF_YEAR)+"周.xls";

        String[] title_week={"工号","部门","姓名","柜门号","正常存入次数","超时存入次数","超时存入日期","未存次数","未存日期","正常取出","应急取出","应急取出日期","多次存取次数","多次存取日期"};
//        String sheetNameWeek = "万道日志周报";
//        String excelWeekFileName = "/万道周报.xls";
        ExcelUtil.initExcel(filePath + excelWeekFileName, sheetNameWeek, title_week);

        ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(),getThisWeekFriday().getTime()), filePath+ excelWeekFileName, context);
       // ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(0,new Date().getTime()), filePath+ excelWeekFileName, context);
    }

    public static void exportMonthExcel(Context context,String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        String format = simpleDateFormat.format(new Date());
        String[] title_month={"工号","部门","姓名","柜门号","本月汇总","正常存入次数","超时存入次数","超时存入日期","未存次数","未存日期","正常取出","应急取出","应急取出日期","多次存取次数","多次存取日期"};
        String sheetNameMonth="月报";
        String excelMonthFileName = "/月报"+format+".xls";
        ExcelUtil.initExcel(filePath + excelMonthFileName, sheetNameMonth, title_month);
        ExcelUtil.writeMonthObjListToExcel(DbUtils.monthLog(getThisMonthFirst().getTime(),getThisMonthSecond().getTime()), filePath+ excelMonthFileName, context);
    }

    public static Date getThisWeekMonday() {
        //   SimpleDateFormat 格式=new SimpleDateFormat("y年M月d日 E H时m分s秒", Locale.CHINA);
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //当前时间，貌似多余，其实是为了所有可能的系统一致
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        System.out.println("当前时间:"+格式.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date  date =calendar.getTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        //System.out.println("周一时间:"+格式.format(calendar.getTime()));
        Log.d("TIME+2333333333655555",date.getTime()+"  ");
        return date;
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
//        System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }


    public static Date getTargetWeekMonday(int week) {
        //   SimpleDateFormat 格式=new SimpleDateFormat("y年M月d日 E H时m分s秒", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date date = calendar.getTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        //System.out.println("周一时间:"+格式.format(calendar.getTime()));
        Log.d("TIME+2333333333655555", date.getTime() + "  ");
        return date;
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
//        System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }

    public static Date getThisTargetWeekFriday(int week) {

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        Date date = calendar.getTime();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        Log.d("TIME+2333333333655555", date.getTime() + "  ");

        return date;
        //  System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }


    public static Date getThisWeekFriday() {
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date  date =calendar.getTime();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        Log.d("TIME+2333333333655555",date.getTime()+"  ");

        return date;
        //  System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }
    public static Date getThisMonthFirst() {
        //   SimpleDateFormat 格式=new SimpleDateFormat("y年M月d日 E H时m分s秒", Locale.CHINA);
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date  date =calendar.getTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        //System.out.println("周一时间:"+格式.format(calendar.getTime()));
        Log.d("TIME+2333333333655555",date.getTime()+"  ");
        return date;
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
//        System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }

    public static Date getThisMonthSecond() {
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date  date =calendar.getTime();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        Log.d("TIME+2333333333655555",date.getTime()+"  ");
        return date;
        //  System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }
}

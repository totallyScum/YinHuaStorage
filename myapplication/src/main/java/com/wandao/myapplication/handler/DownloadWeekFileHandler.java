package com.wandao.myapplication.handler;

import android.os.Environment;

import com.wandao.myapplication.activity.MyApplication;
import com.wandao.myapplication.utils.ExcelUtil;
import com.yanzhenjie.andserver.RequestHandler;

import org.apache.httpcore.HttpEntity;
import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.ContentType;
import org.apache.httpcore.entity.FileEntity;
import org.apache.httpcore.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import static com.yanzhenjie.andserver.util.FileUtils.getMimeType;

public class DownloadWeekFileHandler implements RequestHandler {
    private String filePath = Environment.getExternalStorageDirectory() + "/智能柜网络报表";
    @Override
    public void handle(HttpRequest request, HttpResponse httpResponse, HttpContext context) throws HttpException, IOException {
        File file = createFile();
        ExcelUtil.exportWeekExcel(MyApplication.getInstance(),filePath);
        HttpEntity httpEntity = new FileEntity(file, ContentType.create(getMimeType(file.getAbsolutePath()), Charset.defaultCharset()));
        httpResponse.setHeader("Content-Disposition", "attachment;filename=weekly report.xls");
        httpResponse.setStatusCode(200);
        httpResponse.setEntity(httpEntity);
    }


    private File createFile() {
        File file = null;
        OutputStream outputStream = null;
        try {



            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(new Date());
            //    String[] title_week={"工号","部门","姓名","柜门号","存入时间","存入状态","取出时间","取出状态","应急取","备注"};
            String excelWeekFileName = "/周报"+calendar.get(Calendar.WEEK_OF_YEAR)+"周.xls";





//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(new Date());
     //       String excelWeekFileName = "/周报"+calendar.get(Calendar.WEEK_OF_YEAR)+"周.xls";
          //  String excelWeekFileName = "/万道日报"+calendar.get(Calendar.WEEK_OF_YEAR)+"周.xls";
            String excelWeekFilePath =filePath+ excelWeekFileName;
            file=new File(excelWeekFilePath);
//            ExcelUtil.writeDayObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(), getThisWeekFriday().getTime()), filePath + excelDayFileName, MyApplication.getInstance().getmContext());
            //     file = File.createTempFile("File", ".txt", MainApplication.get().getCacheDir());
            //    outputStream = new FileOutputStream(file);
            //       outputStream.write("leavesC，这是一段测试文本".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}

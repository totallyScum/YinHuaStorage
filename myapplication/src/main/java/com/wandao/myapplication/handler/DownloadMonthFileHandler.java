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
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.yanzhenjie.andserver.util.FileUtils.getMimeType;

public class DownloadMonthFileHandler implements RequestHandler {
    private String filePath = Environment.getExternalStorageDirectory() + "/智能柜网络报表";
    @Override
    public void handle(HttpRequest request, HttpResponse httpResponse, HttpContext context) throws HttpException, IOException {
        File file = createFile();
        ExcelUtil.exportMonthExcel(MyApplication.getInstance(),filePath);
        HttpEntity httpEntity = new FileEntity(file, ContentType.create(getMimeType(file.getAbsolutePath()), Charset.defaultCharset()));
        httpResponse.setHeader("Content-Disposition", "attachment;filename=monthly report.xls");
        httpResponse.setStatusCode(200);
        httpResponse.setEntity(httpEntity);
    }


    private File createFile() {
        File file = null;
        OutputStream outputStream = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
            String format = simpleDateFormat.format(new Date());
            String excelMonthFileName = "/月报"+format+".xls";
            String excelMonthFilePath =filePath+ excelMonthFileName;
            file=new File(excelMonthFilePath);

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

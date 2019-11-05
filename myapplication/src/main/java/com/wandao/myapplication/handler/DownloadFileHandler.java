package com.wandao.myapplication.handler;

import android.os.Environment;

import com.wandao.myapplication.activity.MyApplication;
import com.wandao.myapplication.utils.ExcelUtil;
import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.RequestMethod;
import com.yanzhenjie.andserver.annotation.RequestMapping;

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

/**
 * 作者：leavesC
 * 时间：2018/4/5 16:30
 * 描述：https://github.com/leavesC/AndroidServer
 * https://www.jianshu.com/u/9df45b87cfdf
 */
public class DownloadFileHandler implements RequestHandler {
    private String filePath = Environment.getExternalStorageDirectory() + "/智能柜网络报表";
    @RequestMapping(method = {RequestMethod.GET})
    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        File file = createFile();
        ExcelUtil.exportDayExcel(MyApplication.getInstance(),filePath);
        HttpEntity httpEntity = new FileEntity(file, ContentType.create(getMimeType(file.getAbsolutePath()), Charset.defaultCharset()));
        httpResponse.setHeader("Content-Disposition", "attachment;filename=daily report.xls");
        httpResponse.setStatusCode(200);
        httpResponse.setEntity(httpEntity);

//        httpResponse.setLocale("/ServletDemo/Welcome.html");
//        httpResponse.setEntity();


 //       httpResponse.setHeader("Refresh","5; URL=http://www.baidu.com");
    }

    private File createFile() {
        File file = null;
        OutputStream outputStream = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd号 E");
            String format = simpleDateFormat.format(new Date());
            String excelDayFileName = "/日报"+format+".xls";
            String excelDayFilePath =filePath+ excelDayFileName;
            file=new File(excelDayFilePath);
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

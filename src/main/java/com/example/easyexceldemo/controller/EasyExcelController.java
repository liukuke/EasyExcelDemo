package com.example.easyexceldemo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.easyexceldemo.dao.StudentDao;
import com.example.easyexceldemo.entity.Student;
import com.example.easyexceldemo.listener.StudentReadListener;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Controller
public class EasyExcelController {

    @Resource
    StudentDao studentDao;

    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("学生信息", "utf-8");
        // 文件下载方式(附件下载还是在当前浏览器打开)
        response.setHeader("Content-disposition", "attachment;filename=" +
                fileName + ".xlsx");
        // 构建写入到excel文件的数据(此处可以在数据库获取数据)
        List<Student> students = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setSid(i+"ls");
            student.setName("张三"+i);
            student.setSex("男");
            students.add(student);
        }

        List<Student> students1 = studentDao.selectList(null);

        // 设置头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 设置字体
        WriteFont writeFont = new WriteFont();
        // 设置字体大小
        writeFont.setFontHeightInPoints((short)11);
        // 设置字体加粗
        writeFont.setBold(false);
        headWriteCellStyle.setWriteFont(writeFont);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle,headWriteCellStyle);

        // 写入数据到excel
        EasyExcel.write(response.getOutputStream(), Student.class)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("用户信息")
                .doWrite(students1);

    }

    @RequestMapping(path = {"/uploadExcel"})
    @ResponseBody
    public String uploadExcel(MultipartFile file) throws IOException {
        //统计一个方法耗时
        //在实现方法之前记录一个毫秒数
        long begin = System.currentTimeMillis();
        //
        EasyExcel.read(file.getInputStream(),Student.class,new StudentReadListener(studentDao)).sheet().doRead();
        //在实现方法之后记录一个毫秒数
        long end=System.currentTimeMillis();
        System.out.println("耗时"+(end-begin)/1000+"秒");

        return "success:消耗时间"+(end-begin)/1000+"秒";
    }
}

package com.example.easyexceldemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.example.easyexceldemo.dao.StudentDao;
import com.example.easyexceldemo.entity.Student;
import com.example.easyexceldemo.listener.StudentReadListener;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest

class EasyExcelDemoApplicationTests {

    @Resource
    private StudentDao studentDao;

    @Test
    void testReader() {
        ExcelReaderBuilder readerBuilder = EasyExcel.read("C:\\Users\\Administrator\\Desktop\\学生表.xlsx", Student.class,new StudentReadListener());

        // 封装工作表，sheet（数字） 从0开始，sheet()默认为0
        ExcelReaderSheetBuilder sheet = readerBuilder.sheet();
        // 读取第数字个sheet
        sheet.doRead();

        // 读取所有sheet
//        readerBuilder.doReadAll();
        //写入数据库
        List<Student> students = StudentReadListener.getStudents();
        for (Student student : students) {
            studentDao.insert(student);
        }

    }

    @Test
    public void testWrite() {
        List<Student> students = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setSid(i+"ls");
            student.setName("张三"+i);
            student.setSex("男");
            students.add(student);
        }
        // excel文件对象
        ExcelWriterBuilder write = EasyExcel.write("C:\\Users\\Administrator\\Desktop\\学生表2.xlsx", Student.class);
        // sheet（sheet名称）工作表对象. 将这个工作表写到该表中
        write.sheet("数据").doWrite(students);
        System.out.println("写完");
    }

}

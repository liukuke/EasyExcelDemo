package com.example.easyexceldemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.easyexceldemo.dao.StudentDao;
import com.example.easyexceldemo.entity.Student;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 核心监听器类
 */
public class StudentReadListener extends AnalysisEventListener<Student> {
    /**
     * 学生集合
     */
    private static List<Student> students = new ArrayList<>();

    private StudentDao studentDao;

    public StudentReadListener(){

    }

    public StudentReadListener(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    /**
     * 每读一行内容，会调用该invoke方法一次
     * @param student 读取到的数据封装的对象
     * @param analysisContext
     */
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
            students.add(student);
            // 大批量插入数据库最好使用foreach 动态sql，
            //如果批量增加数据量较多时 建议采用batch模式，
            // foreach一次性插入数据量建议10-100条，for循环是最不建议的方式，需要频繁的建立关闭数据库连接，比较耗时。
            if (students.size() >= 100){
                studentDao.insertBatch(students);
                students.clear();
            }
    }

    /**
     * 全部读完之后，会调用该方法
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("全部解析完成");
    }

    /**
     * 获取全部学生的集合
     * @return
     */
    public static List<Student> getStudents(){
        return students;
    }
}

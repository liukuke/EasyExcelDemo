package com.example.easyexceldemo.dao;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.easyexceldemo.entity.Student;

/**
 * @author Administrator
 */
public interface StudentDao extends BaseMapper<Student> {
    int insertBatch(@Param("studentCollection") Collection<Student> studentCollection);
}

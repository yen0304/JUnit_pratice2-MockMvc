package com.example.demo.dao;

import com.example.demo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentDaoImplTest {

    //注入bean
    @Autowired
    private StudentDao studentDao;

    @Test
    public void getById(){
        Student student = studentDao.getById(1);
        assertNotNull(student);
        assertEquals("Amy",student.getName());
    }

    @Transactional
    @Test
    public void deleteById(){
        studentDao.deleteById(1);
        Student student =studentDao.getById(1);
        assertNull(student);
    }
}
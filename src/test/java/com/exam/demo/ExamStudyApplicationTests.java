package com.exam.demo;

import com.exam.demo.entity.Testpaper;
import com.exam.demo.mapper.TestPaperMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ExamStudyApplicationTests {

    @Autowired
    TestPaperMapper testPaperMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void test1() {
        List<Testpaper> testpapers = testPaperMapper.selectList(null);
        for (Testpaper testpaper : testpapers) {
            System.out.println(testpaper);
        }
    }

}

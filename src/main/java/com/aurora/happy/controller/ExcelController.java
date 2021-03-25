package com.aurora.happy.controller;

import com.aurora.happy.annotation.ExcelAttribute;
import com.aurora.happy.utils.PoiExcelUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
public class ExcelController {

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 模拟从数据库查询数据
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "周深（web导出）", 28, "贵州", new SimpleDateFormat("yyyy-MM-dd").parse("1992-9-29"), 161.0, true));
        studentList.add(new Student(2L, "李健（web导出）", 46, "哈尔滨", new SimpleDateFormat("yyyy-MM-dd").parse("1974-9-23"), 174.5, true));
        studentList.add(new Student(3L, "周星驰（web导出）", 58, "香港", new SimpleDateFormat("yyyy-MM-dd").parse("1962-6-22"), 174.0, false));

        // 导出数据
        PoiExcelUtils<Student> poiExcelUtils = new PoiExcelUtils<>(Student.class);
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("templeate/student.xlsx")).getPath();
        FileInputStream excelTemplateInputStream = new FileInputStream(new File(path));
        poiExcelUtils.exportExcelWithTemplate(studentList, "学生信息表.xlsx", excelTemplateInputStream, 2, 0, response);
        log.info("导出成功！");
    }

    @PostMapping("/importExcel")
    public Map<String, Object> importExcel(MultipartFile file) throws Exception {
        PoiExcelUtils<Student> poiExcelUtils = new PoiExcelUtils<>(Student.class);
        List<Student> studentList = poiExcelUtils.importExcel(file.getInputStream(), 2, 0);

        saveToDB(studentList);
        log.info("导入{}成功！", file.getOriginalFilename());

        // 这里用Map偷懒了，实际项目中可以封装Result实体类返回
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", studentList);
        result.put("msg", "success");
        return result;
    }

    private void saveToDB(List<Student> studentList) {
        if (CollectionUtils.isEmpty(studentList)) {
            return;
        }
        // 直接打印，模拟插入数据库
        studentList.forEach(System.out::println);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Student {
        private Long id;
        @ExcelAttribute(sort = 0, value = "姓名")
        private String name;
        @ExcelAttribute(sort = 1, value = "年龄")
        private Integer age;
        @ExcelAttribute(sort = 2, value = "住址")
        private String address;
        @ExcelAttribute(sort = 3, value = "生日")
        private Date birthday;
        @ExcelAttribute(sort = 4, value = "身高")
        private Double height;
        @ExcelAttribute(sort = 5, value = "是否来自大陆")
        private Boolean isMainlandChina;
    }

}
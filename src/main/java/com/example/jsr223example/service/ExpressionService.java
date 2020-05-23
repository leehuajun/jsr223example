package com.example.jsr223example.service;

import com.example.jsr223example.ConnectionObject;
import com.example.jsr223example.entity.ExpressionEntity;
import com.example.jsr223example.repository.ExpressionRepository;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressionService {

    @Autowired
    private ExpressionRepository expressionRepository;

//    private ConnectionObject connectionObject = ConnectionObject.builder()
//                .url("jdbc:mysql://localhost:3306/demo?useUnicode=true&serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false")
//                .username("root")
//                .password("lzw719")
//                .driver("com.mysql.cj.jdbc.Driver")
//                .build();

    public String runExpressionsByPaperId(Integer paperId,String data) throws Exception {
        List<ExpressionEntity> expressionEntities = expressionRepository.findByPaperIdOrderBySeqAsc(paperId);
        String result = data;

        for(ExpressionEntity exp: expressionEntities){
            GroovyClassLoader loader = new GroovyClassLoader();
            Class aClass = loader.parseClass(exp.getExpression());
            GroovyObject groovyObject = (GroovyObject) aClass.newInstance();
            result = groovyObject.invokeMethod("run", new Object[]{result}).toString();
        }

        return result;
    }
}

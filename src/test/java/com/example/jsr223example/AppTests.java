package com.example.jsr223example;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@SpringBootTest
class AppTests {

  private ConnectionObject connectionObject;

  @BeforeEach
  void init() {
    connectionObject = ConnectionObject.builder()
        .url("jdbc:mysql://localhost:3306/demo?useUnicode=true&serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false")
        .username("root")
        .password("lzw719")
        .driver("com.mysql.cj.jdbc.Driver")
        .build();
  }

  @Test
  void contextLoads() {
  }

  @Test
  void testGroovyJSR223() {
    try {
      ScriptEngineManager factory = new ScriptEngineManager();
      ScriptEngine engine = factory.getEngineByName("groovy");
      String HelloLanguage = "def hello(language,test) {return \"Hello $language,$test\"}";
      engine.eval(HelloLanguage);
      Invocable inv = (Invocable) engine;
      Object[] params = {new String("Groovy"), new String("Abc")};
      Object result = inv.invokeFunction("hello", params);
      //assert result.equals("Hello Groovy");
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testTime() {
    for (int i = 0; i < 50; i++) {
      int i1 = i % 3;
      if (i1 == 0) {
        testGroovyJSR223_GroovyClassLoader();
      } else if (i1 == 1) {
        testGroovyJSR223_ScriptEngine();
      } else {
        testGroovyJSR223_GroovyShell();
      }
    }
  }

  @Test
  void testGroovyJSR223_GroovyClassLoader() {
    String str = "import groovy.json.JsonOutput\n" +
        "\n" +
//                "class DB{\n" +
        "    def checkDatabase(connObj){\n" +
        "        def sql = groovy.sql.Sql.newInstance(connObj.getUrl(),connObj.getUsername(),connObj.getPassword(),connObj.getDriver())\n" +
        "        //println sql.connection.catalog\n" +
        "        def rows = sql.rows(\"select * from t_group\")\n" +
//                "        println(rows)\n" +
        "        def json = JsonOutput.toJson(rows)\n" +
//                "        println(json)\n" +
//                "        sql.eachRow(\"select * from group_entity\") {\n" +
//                "            tp ->\n" +
//                "                println([tp.id, tp.name])\n" +
//                "        }\n" +
        "        sql.close()\n" +
        "        return json\n" +
//                "    }\n" +
        "}";

    try {
      long start = System.currentTimeMillis();
      GroovyClassLoader loader = new GroovyClassLoader();
      Class aClass = loader.parseClass(str);
      GroovyObject groovyObject = (GroovyObject) aClass.newInstance();
      Object result = groovyObject.invokeMethod("checkDatabase", new Object[]{connectionObject});
      long end = System.currentTimeMillis();
      System.out.printf("GroovyClassLoader\t->共耗费时间(毫秒): %d\n", end - start);
      System.out.printf("返回JSON:%s\n", result.toString());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGroovyJSR223_ScriptEngine() {
    String str = "import groovy.json.JsonOutput\n" +
//                "class DB{\n" +
        "    def checkDatabase(connObj){\n" +
        "        def sql = groovy.sql.Sql.newInstance(connObj.getUrl(),connObj.getUsername(),connObj.getPassword(),connObj.getDriver())\n" +
        "        //println sql.connection.catalog\n" +
        "        def rows = sql.rows(\"select * from t_group\")\n" +
//                "        println(rows)\n" +
        "        def json = JsonOutput.toJson(rows)\n" +
//                "        println(json)\n" +
//                "        sql.eachRow(\"select * from group_entity\") {\n" +
//                "            tp ->\n" +
//                "                println([tp.id, tp.name])\n" +
//                "        }\n" +
        "        sql.close()\n" +
        "        return json\n" +
//                "    }\n" +
        "}";

    try {
      long start = System.currentTimeMillis();
      ScriptEngineManager factory = new ScriptEngineManager();
      ScriptEngine engine = factory.getEngineByName("groovy");
      engine.eval(str);
      Invocable inv = (Invocable) engine;
      Object result = inv.invokeFunction("checkDatabase", new Object[]{connectionObject});
      long end = System.currentTimeMillis();
      System.out.printf("ScriptEngine\t->共耗费时间(毫秒): %d\n", end - start);
      System.out.printf("返回JSON:%s\n", result.toString());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGroovyJSR223_GroovyShell() {
    //                "class DB{\n" +
    //                "        println(rows)\n" +
    //                "        def json = JsonOutput.toJson(rows)\n" +
    //                "        println(json)\n" +
    //                "        sql.eachRow(\"select * from group_entity\") {\n" +
    //                "            tp ->\n" +
    //                "                println([tp.id, tp.name])\n" +
    //                "        }\n" +
    //                "    }\n" +
    String str = new StringBuilder()
        .append("import groovy.json.JsonOutput \n")
        .append("    def checkDatabase(connObj){\n")
        .append("        def sql = groovy.sql.Sql.newInstance(connObj.getUrl(),connObj.getUsername(),connObj.getPassword(),connObj.getDriver())\n")
        .append("        //println sql.connection.catalog\n")
        .append("        def rows = sql.rows(\"select * from t_group\")\n")
        .append("        def json = JsonOutput.toJson(rows)\n")
        .append("        sql.close()\n")
        .append("        return json\n")
        .append("}")
        .toString();

    try {
      long start = System.currentTimeMillis();
      Object result = new GroovyShell().parse(str).invokeMethod("checkDatabase", new Object[]{connectionObject});
      long end = System.currentTimeMillis();
      System.out.printf("GroovyShell\t->共耗费时间(毫秒): %d\n", end - start);
      System.out.printf("返回JSON:%s\n", result.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

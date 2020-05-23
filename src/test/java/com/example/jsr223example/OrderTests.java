package com.example.jsr223example;

import com.example.jsr223example.dto.BookDto;
import com.example.jsr223example.dto.OrderDto;
import com.example.jsr223example.entity.ExpressionEntity;
import com.example.jsr223example.repository.ExpressionRepository;
import com.example.jsr223example.service.ExpressionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OrderTests {
    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testOrder() throws Exception {
        List<BookDto> bookDtoList = Arrays.asList(
                BookDto.builder().name("Java入门").isbn("111-1111-111").price(40.0).build(),
                BookDto.builder().name("Redis从入门到精通").isbn("222-2222-222").price(65.0).build(),
                BookDto.builder().name("SpringBoot实战").isbn("333-3333-333").price(38.0).build(),
                BookDto.builder().name("MySQL入门经典").isbn("444-4444-444").price(25.0).build()

        );
        OrderDto orderDto = OrderDto.builder().code("2020052201").bookList(bookDtoList).build();
        String data = objectMapper.writeValueAsString(orderDto);

        System.out.println(data);

        String result = expressionService.runExpressionsByPaperId(1, data);
        OrderDto order = objectMapper.readValue(result, OrderDto.class);
        System.out.println(order);
        System.out.println(order.getTotal());
    }

    @Test
    void addExpress(){
        ExpressionEntity expressionEntity = ExpressionEntity.builder()
                .paperId(1)
                .seq(1)
                .expression("import groovy.json.JsonSlurper\n" +
                        "\n" +
                        "def run(data) {\n" +
                        "  def jsonSluper = new JsonSlurper()\n" +
                        "  def obj = jsonSluper.parseText(data)\n" +
                        "  obj.total = 0\n" +
                        "  Object lst = obj.bookList\n" +
                        "  for (Object book in lst) {\n" +
                        "    obj.total = (obj.total + book.price)\n" +
                        "  }\n" +
                        "  println(obj)\n" +
                        "}")
                .build();
        expressionRepository.save(expressionEntity);
    }
}

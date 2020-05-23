package com.example.jsr223example.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto implements Serializable {

    private static final long serialVersionUID = -658384056845383135L;
    private String code;

    @Builder.Default
    private List<BookDto> bookList = new ArrayList<>();

    @Builder.Default
    private Double total = 0.0;

    @Builder.Default
    private Double freight = 0.0;

    @Builder.Default
    private Double sum = 0.0;
}

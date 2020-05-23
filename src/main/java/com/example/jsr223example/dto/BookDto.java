package com.example.jsr223example.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto implements Serializable {
    private static final long serialVersionUID = -3518272799112492544L;

    private String name;
    private String isbn;
    private Double price;

}

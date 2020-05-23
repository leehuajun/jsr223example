package com.example.jsr223example.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_expression")
public class ExpressionEntity implements Serializable {
    private static final long serialVersionUID = 1923631293535521125L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_")
    private Integer id;

    /**
     * 底稿的ID
     */
    @Column(name = "paper_id_")
    private Integer paperId;

    /**
     * 表达式的运行顺序号，越小越先运行
     */
    @Builder.Default
    @Column(name = "seq_")
    private Integer seq = 0;

    /**
     * 表达式/动态脚本代码
     */
    @Lob
    @Column(name = "expression_")
    private String expression;

}

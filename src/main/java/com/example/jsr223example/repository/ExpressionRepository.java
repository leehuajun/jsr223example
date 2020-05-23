package com.example.jsr223example.repository;

import com.example.jsr223example.entity.ExpressionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ExpressionRepository extends JpaRepository<ExpressionEntity,Integer>, JpaSpecificationExecutor<ExpressionEntity> {

    List<ExpressionEntity> findByPaperIdOrderBySeqAsc(Integer paperId);

}

package com.ssafy.tranvel.repository;


import com.ssafy.tranvel.entity.AttractionList;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<AttractionList, Long> {

}

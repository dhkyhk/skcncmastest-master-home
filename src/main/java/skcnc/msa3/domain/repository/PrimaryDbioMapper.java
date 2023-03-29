package skcnc.msa3.domain.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class PrimaryDbioMapper extends AbstractMapper {
    public PrimaryDbioMapper(@Qualifier("primarySqlSessionTemplate") SqlSession sqlSession) {
        super(sqlSession);
    }
}

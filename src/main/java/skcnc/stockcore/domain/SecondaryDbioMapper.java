package skcnc.msa3.domain.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class SecondaryDbioMapper extends AbstractMapper {
    public SecondaryDbioMapper(@Qualifier("secondarySqlSessionTemplate") SqlSession sqlSession) {
        super(sqlSession);
    }
}

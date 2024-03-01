package skcnc.framework.database;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class DbioMapperbatisSecond extends AbstractMapper {
    public DbioMapperbatisSecond(@Qualifier("secondSessionTemplate") SqlSession sqlSession) {
	    super(sqlSession);
	}
}
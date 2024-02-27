package skcnc.framework.database;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class DbioMapperbatis extends AbstractMapper {
    public DbioMapperbatis(@Qualifier("sessionTemplate") SqlSession sqlSession) {
        super(sqlSession);
    }
}

package skcnc.stockcore.domain;

import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;


/**
 * @fileName    : AbstractMapper.java
 * @description : 전역 공통 Mapper
 */
public abstract class AbstractMapper {

    private SqlSession sqlSession;

    public AbstractMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int insert(String queryId) {
        return this.sqlSession.insert(queryId);
    }

    public int insert(String queryId, Object parameterObject) {
        return this.sqlSession.insert(queryId, parameterObject);
    }

    public int update(String queryId) {
        return this.sqlSession.update(queryId);
    }

    public int update(String queryId, Object parameterObject) {
        return this.sqlSession.update(queryId, parameterObject);
    }

    public int delete(String queryId) {
        return this.sqlSession.delete(queryId);
    }

    public int delete(String queryId, Object parameterObject) {
        return this.sqlSession.delete(queryId, parameterObject);
    }

    public <T> T select(String queryId) {
        return this.sqlSession.selectOne(queryId);
    }

    public <T> T select(String queryId, Object parameterObject) {
        return this.sqlSession.selectOne(queryId, parameterObject);
    }

    public <E> List<E> selectList(String queryId) {
        return this.sqlSession.selectList(queryId);
    }

    public <E> List<E> selectList(String queryId, Object parameterObject) {
        if(parameterObject instanceof RowBounds){
            RowBounds rowBounds = (RowBounds)parameterObject;
            return this.sqlSession.selectList(queryId, parameterObject, new RowBounds(rowBounds.getOffset(), rowBounds.getLimit()));
        }else{
            return this.sqlSession.selectList(queryId, parameterObject);
        }

    }

    public <E> Cursor<E> selectCursor(String queryId) {
        return this.sqlSession.selectCursor(queryId);
    }
    public <E> Cursor<E> selectCursor(String queryId, Object parameterObject) {
        return this.sqlSession.selectCursor(queryId, parameterObject);
    }

}
package www.ezrpro.com.db.opt;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  14:32:59
* 
*/

public class DbTools {

    public static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatisConfig.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }

    public static <T> T  getMapper(SqlSession session,Class<T> var1){
        return session.getMapper(var1);
    }

    public static void closeSqlSession(SqlSession sqlSession){
        sqlSession.close();
    }
    
}

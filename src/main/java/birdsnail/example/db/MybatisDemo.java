package birdsnail.example.db;

import birdsnail.example.base.User;
import birdsnail.example.db.mapper.UserMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * Mybatis demo
 */
public class MybatisDemo {


    /**
     * 获取sqlSession
     */
    public SqlSession getSqlSession() {
        DataSource dataSource = new PooledDataSource("org.h2.Driver",
                // 初始化脚本的路径要指定classpath
                "jdbc:h2:mem:~/test;INIT=RUNSCRIPT FROM 'classpath:h2/create.sql'\\;RUNSCRIPT FROM 'classpath:h2/data.sql';MODE=MYSQL",
                "sa",
                "");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("dev", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        // resource中的mapper.xml包结构要跟mapper包结构保持一致，不然加载不到xml文件
        configuration.addMapper(UserMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) {
        /*
        手动方式获取mapper
         */
        MybatisDemo mybatisDemo = new MybatisDemo();
        SqlSession sqlSession = mybatisDemo.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User value = userMapper.selectByName("yhd");
        System.out.println("sql result-->" + value);

        value = userMapper.selectByName("不上班");
        System.out.println("sql result-->" + value);

        User user = new User();
        user.setName("不上班");
        user.setAge(222);
        user.setPhone("1569856325");
        userMapper.insert(user);

        value = userMapper.selectByName("不上班");
        System.out.println("sql result-->" + value);

    }


}

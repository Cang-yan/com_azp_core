package com.azp.core.test.starter;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.HashSet;
import com.azp.core.AppStarter;


public class AppTestStarter {

    private static final Logger logger = LoggerFactory.getLogger(AppTestStarter.class);

    private static final int DB_PORT = 45073;

    private static final String DB_SCHEMA = "kyragc";

    private static final String JDBC_CONNECTION = "jdbc:mysql://dev.database.horsecoder.com:" + DB_PORT + "/" + DB_SCHEMA + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";

    private static final String STORAGE_LOCATION = "/tmp/maria/test";

    private static final String ARTIFACT_ID = "core";

    public static void main(String[] args) {
        // 启动DB
        startDatabase();

        // 本地调试环境参数配置
        HashSet<String> argSet = new HashSet<>(Arrays.asList(args));
        argSet.add("--spring.profiles.active=dev");
        argSet.add("--spring.datasource.url=" + JDBC_CONNECTION);

        // 本地联调状态配置
        boolean integrated = Boolean.parseBoolean(System.getProperty("integrated", "false"));
        if (!integrated) argSet.add("--eureka.client.enabled=false");

        // 启动AppStarter
        AppStarter.main(argSet.toArray(new String[0]));
    }

    private static void startDatabase() {
        try {
            // DB Config
            DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
            int port = allocatePort(DB_PORT);
            configBuilder.setPort(port);
            configBuilder.setDataDir(STORAGE_LOCATION + port);
            logger.info("Database will start at port:" + port);

            // DB Start
            DB db = DB.newEmbeddedDB(configBuilder.build());
            db.start();
            db.createDB(DB_SCHEMA);
        } catch (Exception ignored) {
            logger.error("DB start error");
        }

        new Thread(() -> {
            try {
                initSql();
            } catch (Exception ignored) {
                logger.info("Sql init error");
            }
        }).start();
    }


    /**
     * 自动初始化数据环境
     * @throws Exception
     */
    private static void initSql() throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_CONNECTION, "root", "");
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setErrorLogWriter(null);
        runner.setLogWriter(null);
        try {
            runner.runScript(Resources.getResourceAsReader(ARTIFACT_ID + ".sql"));
            runner.runScript(Resources.getResourceAsReader(ARTIFACT_ID + "_test.sql"));
        } catch (Exception e) {
            connection.close();
            throw e;
        }
    }


    /**
     * 自动分配可用端口
     *
     * @param port 期望端口
     * @return 实际端口
     */
    private static int allocatePort(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            int localPort = serverSocket.getLocalPort();
            serverSocket.close();
            return localPort;
        } catch (Exception e) {
            return allocatePort(port + 1);
        }
    }

}

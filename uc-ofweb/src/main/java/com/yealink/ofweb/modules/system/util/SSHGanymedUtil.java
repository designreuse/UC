package com.yealink.ofweb.modules.system.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * ganymed ssh工具类
 * author:pengzhiyuan
 * Created on:2016/8/29.
 */
public class SSHGanymedUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SSHGanymedUtil.class);

    // 目标主机地址
    private String host;
    // 端口
    private int port;
    // 用户名
    private String userName;
    //密码
    private String password;
    // 连接
    private Connection connection;

    public SSHGanymedUtil(String _host, int _port, String _userName,
                          String _password) {
        this.host = _host;
        this.port = _port;
        this.userName = _userName;
        this.password = _password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * 登录服务器
     * @return
     */
    public boolean login() {
        LOG.info("login linux host="+host);

        // 建立连接
        connection = new Connection(host, port);
        try {
            // 连接上
            connection.connect();
            // 进行校验
            boolean isAuthenticated = connection.authenticateWithPassword(
                    userName, password);

            LOG.info("isAuthenticated = " + isAuthenticated);
            if (isAuthenticated) {
                return true;
            }
        } catch (Exception e) {
            LOG.error("login fail!UserOrPasswordError,", e);
        }
        return false;
    }

    /**
     * 执行shell命令
     * @param command
     * @return 执行结果
     */
    public String execCommand(final String command) throws Exception {
        LOG.info("start exec Command! cmd="+command);
        // 连接的通道
        Session sess = null;
        String result = "";
        try {
            // 创建session
            sess = connection.openSession();
//            // 开启远程的客户端
//            sess.requestPTY("vt100", 80, 24, 640, 480, null);
//            // 开启后睡眠4秒
//            Thread.sleep(4000);
            // 开启终端
            // 执行命令
            sess.execCommand(command);

            String errorResult = "";

            result = processOutputStream(sess.getStdout());

            Integer exitStatus = sess.getExitStatus();
            if (exitStatus != null && exitStatus != 0) {
                errorResult = processOutputStream(sess.getStderr());
            }

            LOG.info("ExitCode: " + exitStatus);
            LOG.debug("error result = " + errorResult);
            LOG.debug("result = " + result);

            // exitcode不为空且不是0 认为是失败  或者  错误流信息不为空
            if ((sess.getExitStatus() != null && sess.getExitStatus() != 0) ||
                    !PropertiesUtils.getString(errorResult).equals("")){
                throw new Exception("run error cmd="+command);
            }
        } catch (Throwable e) {
            LOG.error("run cmd error!", e);
            throw e;
        } finally {
            // 关闭通道
            if (sess != null) {
                sess.close();
            }
        }
        return result;
    }

    /**
     * 处理执行结果返回信息
     * @param in
     * @return
     */
    private String processOutputStream(InputStream in) throws IOException {
        final StringBuilder sb = new StringBuilder(256);
        // 起始时间,避免连通性陷入死循环
        long start = System.currentTimeMillis();

        InputStream stdout = new StreamGobbler(in);
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
//        char[] arr = new char[512];
        int read;
        int i = 0;
//        while (true) {
//            // 将结果流中的数据读入字符数组
//            read = br.read(arr, 0, arr.length);
//            // 推延5秒就退出[针对连通性测试会陷入死循环]
//            if (read < 0 || (System.currentTimeMillis() - start) > 5000)
//                break;
//
//            // 将结果拼装进StringBuilder
//            sb.append(new String(arr, 0, read));
//            i++;
//        }
        try {
            String line=null;
            while((line=br.readLine()) != null) {
                // 超过5秒就退出
                if ((System.currentTimeMillis() - start) > 5000) {
                    break;
                }
                sb.append(line+"\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 关闭ssh连接
     */
    public void closeConnection() {
        LOG.info("close ssh connection.");
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        SSHGanymedUtil util = new SSHGanymedUtil("10.3.17.148", 22, "root", "ucsoftware");

        if (util.login()) {
            String result = null;
            try {
                result = util.execCommand("lsof -i:5222 |awk '{print $2}' | tail -n 1");
            } catch (Exception e) {
            }
            LOG.debug("result="+result);
            util.closeConnection();
        }

    }
}

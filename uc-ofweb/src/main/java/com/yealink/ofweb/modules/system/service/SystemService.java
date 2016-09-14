package com.yealink.ofweb.modules.system.service;

import com.yealink.ofweb.modules.common.Constants;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.msg.dao.MsgManagerDao;
import com.yealink.ofweb.modules.system.entity.SystemEntity;
import com.yealink.ofweb.modules.system.util.SSHGanymedUtil;
import com.yealink.ofweb.modules.util.DesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统服务
 * author:pengzhiyuan
 * Created on:2016/8/29.
 */
@Service
public class SystemService {

    @Autowired
    private MsgManagerDao msgManagerDao;

    /**
     * 获取linux用户名
     * @return
     */
    private String getLinuxUsername() {
        Map<String, Object> map = msgManagerDao.queryPropertyById(Constants.PROPERTY_LINUX_USERNAME);
        if (map != null) {
            String userName = PropertiesUtils.getString(map.get("propValue"));
            return userName;
        }
        return "";
    }

    /**
     * 获取linux密码
     * @return
     */
    private String getLinuxPassword() {
        Map<String, Object> map = msgManagerDao.queryPropertyById(Constants.PROPERTY_LINUX_PASSWORD);
        if (map != null) {
            String enPassword = PropertiesUtils.getString(map.get("propValue"));
            if (!enPassword.equals("")) {
                try {
                    return PropertiesUtils.getString(DesUtils.decryptBase64(enPassword));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 执行linux命令获取 系统服务相关状态
     *
     * @return
     *  成功的话返回对应目录，表示服务状态是启动 文件服务key=fileServer IM服务key=openfire
     *  如果对应key返回null则表示其服务状态是关闭的
     */
    public Map<String, String> execGetSystemServerInfo(String host, int port) {
        String userName = getLinuxUsername();
        String password = getLinuxPassword();

        // 用户名获取密码为空
        if (userName.equals("") || password.equals("")) {
            return null;
        }

        Map<String, String> resultMap = null;
        SSHGanymedUtil util = new SSHGanymedUtil(host, port, userName, password);

        // 登录
        if (util.login()) {
            String result = null;
            resultMap = new HashMap<String, String>();

            //简单查询 是否有文件服务进程
            String fsCmd = "ps -ef|grep DFileServer_Home|grep -v grep";
            try {
                result = util.execCommand(fsCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!PropertiesUtils.getString(result).equals("")) {
                // 成功
                resultMap.put(Constants.SERVICE_STATUS_FS,"true");
            }

            //简单查询 是否有IM服务
            String imCmd = "lsof -i:5222 |awk '{print $2}' | tail -n 1";
            try {
                result = util.execCommand(imCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!PropertiesUtils.getString(result).equals("")) {
                // 成功
                resultMap.put(Constants.SERVICE_STATUS_IM,"true");
            }
            // 关闭连接
            util.closeConnection();
        }
        return resultMap;
    }

    /**
     * 服务操作
     * @param systemEntity - 服务信息
     * @param opt - 操作类型 1-启动 2-重启 3-停止
     */
    public void operateService(SystemEntity systemEntity, String opt) throws Exception {
        String userName = getLinuxUsername();
        String password = getLinuxPassword();

        // 用户名获取密码为空
        if (userName.equals("") || password.equals("")) {
            throw new Exception("userName or password is null!");
        }

        String flag = systemEntity.getFlag();
        String dir = systemEntity.getDir();
        String host = systemEntity.getHost();
        int port = systemEntity.getPort();

        SSHGanymedUtil util = new SSHGanymedUtil(host, port, userName, password);

        // 登录
        if (!util.login()) {
            throw new Exception("login linux server fail!");
        }

        try {
            String cmd = "";
            String result = null;
            // 如果是文件服务
            if (Constants.SERVICE_NAME_FS.equals(flag)) {
                if ("1".equals(opt)) {
                    // 启动
                    try {
                        cmd = "cd "+dir+"/bin;rm fs_pid;chmod +x *.sh;source /etc/profile;./fs_run.sh";
                        util.execCommand(cmd);
                    } catch (Exception e) {
                        throw e;
                    }
                } else if ("2".equals(opt)) {
                    // 重启
                    try {
                        // 关闭
                        cmd = "cd "+dir+"/bin;chmod +x *.sh;./fs_stop.sh";
                        util.execCommand(cmd);
                        // 启动
                        cmd = "cd "+dir+"/bin;rm fs_pid;chmod +x *.sh;source /etc/profile;./fs_run.sh";
                        util.execCommand(cmd);
                    } catch (Exception e) {
                        throw e;
                    }

                } else if ("3".equals(opt)) {
                    // 停止
                    try {
                        cmd = "cd "+dir+"/bin;chmod +x *.sh;./fs_stop.sh";
                        util.execCommand(cmd);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
            // 如果是IM服务
            else if (Constants.SERVICE_NAME_IM.equals(flag)) {
                if ("1".equals(opt)) {
                    // 启动
                    try {
                        cmd = "cd "+dir+"/bin;chmod +x *.sh;source /etc/profile;./openfire.sh";
                        util.execCommand(cmd);
                    } catch (Exception e) {
                        throw e;
                    }

                } else if ("2".equals(opt)) {
                    // 重启
                    // 停止
                    cmd = "lsof -i:5222 |awk '{print $2}' | tail -n 1";
                    try {
                        result = util.execCommand(cmd);
                    } catch (Exception e) {
                        throw e;
                    }
                    if (result != null) {
                        cmd = "kill -9 "+result;
                        try {
                            util.execCommand(cmd);
                        } catch (Exception e) {
                            throw e;
                        }
                    } else {
                        throw new Exception("service has stoped!");
                    }

                    // 启动
                    try {
                        cmd = "cd "+dir+"/bin;chmod +x *.sh;source /etc/profile;./openfire.sh start";
                        util.execCommand(cmd);
                    } catch (Exception e) {
                        throw e;
                    }

                } else if ("3".equals(opt)) {
                    // 停止
                    cmd = "lsof -i:5222 |awk '{print $2}' | tail -n 1";
                    try {
                        result = util.execCommand(cmd);
                    } catch (Exception e) {
                        throw e;
                    }
                    if (result != null) {
                        cmd = "kill -9 "+result;
                        try {
                            util.execCommand(cmd);
                        } catch (Exception e) {
                            throw e;
                        }
                    } else {
                        throw new Exception("service has stoped!");
                    }
                }
            } else {
                throw new Exception("service not found!");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            util.closeConnection();
        }
    }
}

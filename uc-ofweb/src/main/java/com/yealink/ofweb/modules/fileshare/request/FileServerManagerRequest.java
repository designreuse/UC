package com.yealink.ofweb.modules.fileshare.request;

/**
 * 文件服务器管理请求
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
public class FileServerManagerRequest {
    /**
     * 服务器JID
     */
    private String jid;
    /**
     * 主机地址
     */
    private String host;
    /**
     * 端口
     */
    private String port;
    /**
     * 会话连接id
     */
    private String connId;
    /**
     * 连接digest
     */
    private String digest;
    /**
     * http访问baseurl
     */
    private String baseUrl;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }
}

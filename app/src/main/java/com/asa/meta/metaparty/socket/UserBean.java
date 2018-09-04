package com.asa.meta.metaparty.socket;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class UserBean {
    public String ip;
    public int type;
    public int port;
    public String hostAddress;
    public String content;

    public UserBean setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }

    public UserBean setPort(int port) {
        this.port = port;
        return this;
    }

    public UserBean setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public UserBean setType(int type) {
        this.type = type;
        return this;
    }

    public UserBean setContent(String content) {
        this.content = content;
        return this;
    }

    public String build() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(ip)) {
                jsonObject.put("ip", ip);
            }

            if (!TextUtils.isEmpty(content)) {
                jsonObject.put("content", content);
            }

            if (type != 0) {
                jsonObject.put("type", type);
            }

        } catch (JSONException e) {

        }

        return jsonObject.toString();
    }
}

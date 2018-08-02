package com.pym.uiproject.app.message;

/**
 * Peng YanMing on 2018\7\31 0031
 */
public class Goods {
    public  String content;
    public  String hashId;
    public  int unixtime;
    public String updatetime;

    public String getContent() {
        return content;
    }

    public String getHashId() {
        return hashId;
    }

    public int getUnixtime() {
        return unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "content='" + content + '\'' +
                ", hashId='" + hashId + '\'' +
                ", unixtime=" + unixtime +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}

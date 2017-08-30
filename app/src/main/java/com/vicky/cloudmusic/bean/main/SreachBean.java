package com.vicky.cloudmusic.bean.main;

import com.vicky.cloudmusic.Constant;

import java.io.Serializable;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class SreachBean implements Serializable {

    private Constant.CloudType  cloudType;
    private String name;
    private String id;


    public Constant.CloudType getCloudType() {
        return cloudType;
    }

    public void setCloudType(Constant.CloudType cloudType) {
        this.cloudType = cloudType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

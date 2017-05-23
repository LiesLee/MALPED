package com.lies.malped.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rrsh on 16/10/21.
 *   省份含城市列表
 */

public class ProvinceAnd_City implements Serializable{


    /**
     * city_list : [{"id":"110100","level_type":"2","name":"北京市","pinyin":"Beijing","short_name":"北京"}]
     * id : 110000
     * level_type : 1
     * name : 北京
     * pinyin : Beijing
     * short_name : 北京
     */

    private String id;
    private String level_type;
    private String name;
    private String pinyin;
    private String short_name;
    /**
     * id : 110100
     * level_type : 2
     * name : 北京市
     * pinyin : Beijing
     * short_name : 北京
     */

    private List<CityListBean> city_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel_type() {
        return level_type;
    }

    public void setLevel_type(String level_type) {
        this.level_type = level_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<CityListBean> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<CityListBean> city_list) {
        this.city_list = city_list;
    }

    public static class CityListBean implements Serializable{
        private String id;
        private String level_type;
        private String name;
        private String pinyin;
        private String short_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel_type() {
            return level_type;
        }

        public void setLevel_type(String level_type) {
            this.level_type = level_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }
    }
}

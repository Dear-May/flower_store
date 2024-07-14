package com.example.demo.Entity;

import org.springframework.web.multipart.MultipartFile;

public class GoodEntity {
    private int id;
    private String goods_name;
    private String goods_describe;
    private String good_price;
    private String goods_category;
    private String goods_state;
    private String goods_imgFile;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_describe() {
        return goods_describe;
    }

    public void setGoods_describe(String goods_describe) {
        this.goods_describe = goods_describe;
    }

    public String getGood_price() {
        return good_price;
    }

    public void setGood_price(String good_price) {
        this.good_price = good_price;
    }

    public String getGoods_category() {
        return goods_category;
    }

    public void setGoods_category(String goods_category) {
        this.goods_category = goods_category;
    }

    public String getGoods_state() {
        return goods_state;
    }

    public void setGoods_state(String goods_state) {
        this.goods_state = goods_state;
    }

    public String getGoods_imgFile() {
        return goods_imgFile;
    }

    public void setGoods_imgFile(String goods_imgFile) {
        this.goods_imgFile = goods_imgFile;
    }

    @Override
    public String toString() {
        return "GoodEntity{" +
                "id=" + id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_describe='" + goods_describe + '\'' +
                ", good_price='" + good_price + '\'' +
                ", goods_category='" + goods_category + '\'' +
                ", goods_state='" + goods_state + '\'' +
                ", goods_imgFile=" + goods_imgFile +
                '}';
    }
}

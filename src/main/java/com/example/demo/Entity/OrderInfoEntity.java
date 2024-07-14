package com.example.demo.Entity;

public class OrderInfoEntity {
    private String order_id;
    private int order_goodId;
    private int order_goodnum;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getOrder_goodId() {
        return order_goodId;
    }

    public void setOrder_goodId(int order_goodId) {
        this.order_goodId = order_goodId;
    }

    public int getOrder_goodnum() {
        return order_goodnum;
    }

    public void setOrder_goodnum(int order_goodnum) {
        this.order_goodnum = order_goodnum;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + order_id + "," +
                "\"goodId\": " + order_goodId + "," +
                "\"goodnum\": " + order_goodnum +
                "}";
    }
}

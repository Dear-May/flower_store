package com.example.demo.Entity;

public class ShoppingCartEntity {
    private int id;
    private int user_id;
    private int cart_goodid;
    private int order_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCart_goodid() {
        return cart_goodid;
    }

    public void setCart_goodid(int cart_goodid) {
        this.cart_goodid = cart_goodid;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "ShoppingCartEntity{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", cart_goodid=" + cart_goodid +
                ", order_id=" + order_id +
                '}';
    }
}

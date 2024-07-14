package com.example.demo.Entity;

public class OrderEntity {
    private int id;
    private int userId;
    private String orderTime;
    private String orderStatus;
    private double orderPrice;

    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public int getUserId() { return userId;}

    public void setUserId(int userId) { this.userId = userId; }

    public String getOrderTime() { return orderTime; }

    public void setOrderTime(String orderTime) { this.orderTime = orderTime; }

    public String getOrderStatus() { return orderStatus; }

    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public double getOrderPrice() { return orderPrice; }

    public void setOrderPrice(double orderPrice) { this.orderPrice = orderPrice; }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", userId='" + userId +'\''+
                ", orderTime='" + orderTime + '\'' +
                ", orderStatus=''" + orderStatus + '\'' +
                ", orderPrice=" + orderPrice +
                '}';
    }
}

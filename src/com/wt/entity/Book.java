package com.wt.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Book implements Serializable{

	private Integer book_id;
	private String book_name;
	private Integer quantity;
	private double price;
	
	// ��Ӷ��������״̬
	// 0 Ϊ��������1Ϊ���䡢2Ϊ���͡�3Ϊǩ��
	// ��ʼ��Ĭ��ֵΪ0����Ϊ��������״̬
	private Integer book_state;
	
	// ֮ǰ�Ĵ�����Զ��ӳ��
	//Set<Order> orders = new HashSet<>();

	private Order order;
	
	public Book(Integer book_id, String book_name, Integer quantity,
			double price, Order order) {
		super();
		this.book_id = book_id;
		this.book_name = book_name;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
	}

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Integer getBook_id() {
		return book_id;
	}

	public void setBook_id(Integer book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getBook_state() {
		return book_state;
	}

	public void setBook_state(Integer book_state) {
		this.book_state = book_state;
	}
	
//	public Set<Order> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(Set<Order> orders) {
//		this.orders = orders;
//	}
	
}

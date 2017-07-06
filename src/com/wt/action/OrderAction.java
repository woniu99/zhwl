package com.wt.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.wt.entity.Book;
import com.wt.entity.Order;
import com.wt.entity.Warehouse;
import com.wt.service.OrderService;

public class OrderAction extends ActionSupport implements RequestAware,
ModelDriven<Book>, Preparable{

	private static final long serialVersionUID = 1L;
	
	private OrderService orderService;
	
	private InputStream inputStream;
	
	private Integer id;
	
	private Integer bookStatus;
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	
	ActionContext context = ActionContext.getContext();
	
	HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
	
	HttpSession session = request.getSession();
	
	// ����Ϊ��ȡ����
	String userName = (String) session.getAttribute("username");
	
	String bookname = request.getParameter("bookname");
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	// ����Ϊ��ѯ�û��Ĺ����¼
	public void query() {
		
		// ����Ϊ��ѯ�û��Ķ����б�
		List<Order> orders = orderService.findOrder(userName);
		List<Book> books = new ArrayList<>();
		for (Order order : orders) {
			books.add(order.getBook());
		}

		// ����Ϊͼ���б�
		for (Book book : books) {
			System.out.println("book.getBook_name() --- " + book.getBook_name());
			System.out.println("book.getPrice() --- " + book.getPrice());
		}
		
		
		if(books != null){
			Map<String, Object> mapSession = ActionContext.getContext().getSession();
			mapSession.put("books", books);
		}
	}
	
	// ����Ϊ��ѯ�û��Ĺ����¼
	public void logisticsQuery() {
		
		// ����Ϊ��ѯ�û��Ķ����б�
		List<Order> orders = orderService.findOrder(userName);
		List<Book> books = new ArrayList<>();
		for (Order order : orders) {
			books.add(order.getBook());
		}

		// ����Ϊͼ���б�
//		for (Book book : books) {
//			System.out.println("book.getBook_name() --- " + book.getBook_name());
//			System.out.println("book.getPrice() --- " + book.getPrice());
//		}
		
		// 0 Ϊ��������1Ϊ���䡢2Ϊ���͡�3Ϊǩ��
 		
 		// 0 Ϊ������
 		List<Book> pendingBooks = new ArrayList<>();
 	
 		for (Book book : books) {
			if(book.getBook_state() == 0){
				pendingBooks.add(book);
			}
		}
 		
 		// 1Ϊ����
 		List<Book> transportBooks = new ArrayList<>();
 	 	
 		for (Book book : books) {
			if(book.getBook_state() == 1){
				transportBooks.add(book);
			}
		}
 		
 		// 2Ϊ����
 		List<Book> sendBooks = new ArrayList<>();
 	 	
 		for (Book book : books) {
			if(book.getBook_state() == 2){
				sendBooks.add(book);
			}
		}
 		
 		// 3Ϊǩ��
 		List<Book> signBooks = new ArrayList<>();
 	 	
 		for (Book book : books) {
			if(book.getBook_state() == 3){
				signBooks.add(book);
			}
		}
		
		if(books != null){
			Map<String, Object> mapSession = ActionContext.getContext().getSession();
			mapSession.put("books", books);
			mapSession.put("pendingBooks", pendingBooks);
			mapSession.put("transportBooks", transportBooks);
			mapSession.put("sendBooks", sendBooks);
			mapSession.put("signBooks", signBooks);
		}
		
	}
	
	public String save(){
		
		System.out.println("saveOrder");

		String StrQuantity = request.getParameter("quantity");
		int quantity = Integer.parseInt(StrQuantity);
		
		String StrPrice = request.getParameter("price");
		int unit_price = Integer.parseInt(StrPrice);
		
		int price = quantity * unit_price;
		
		// ��ȡ�ֿ��е�ͼ��
		Warehouse w_book = orderService.findWarehouseBook(bookname);
		
		int w_quantity = w_book.getQuantity();
		
		String w_q = Integer.toString(w_quantity);
		
		w_quantity = w_quantity - quantity;
		
		if(w_quantity < 0){
			session.setAttribute("notEnough", "notEnough");
			session.setAttribute("w_q", w_q);
			session.setAttribute("bookname", bookname);
			System.out.println("��治��");
		}
		else{
			session.removeAttribute("notEnough");
			session.removeAttribute(w_q);
			session.removeAttribute(bookname);
			
			// �½�����
			Order order = new Order();
			
			Book book = new Book();
			
			book.setBook_name(bookname);
			book.setQuantity(quantity);
			book.setPrice(price);
			book.setBook_state(0);
			
			order.setUser_name(userName);
			
			orderService.saveOrUpdateBook(book);
			order.setBook(book);
			orderService.saveOrUpdateOrder(order);
			
			// ���²ֿ��е�ͼ����Ŀ
			w_book.setQuantity(w_quantity);
			orderService.saveOrUpdateWarehouseBook(w_book);
		}
		
		query();
		
		return "save";
	}
	
	public String shoppingInfo(){
		
		query();
		
		return "shoppingInfo";
	}
	
	public String logisticsInfo(){
		
		logisticsQuery();
		
		return "logisticsInfo";
	}
	
	//����������Ϣ
	public String update() {
		try {
			orderService.update(id, bookStatus);
			inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				inputStream = new ByteArrayInputStream("0".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}

		return "ajax-success";
	}

	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Book getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}

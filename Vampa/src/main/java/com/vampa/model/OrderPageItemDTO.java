package com.vampa.model;

import java.util.List;

public class OrderPageItemDTO {
	
	/* 뷰로부터 전달받을 값 */
	private int bookId;
	
	private int bookCount;
	
	/* bookId를 통해 DB에서 꺼내올 값 */
	private String bookName;
	
	private int bookPrice;
	
	private double bookDiscount;
	
	/* 장바구니에서 했던, 만들어 낼 값 */
	//할인을 적용한 상품 한 개의 판매가격
    private int salePrice;
    
    //총 가격 = 판매가격 * 수량
    private int totalPrice;
    
    /* point정보(37~~) */
    //상품 한개의 받을 수 있는 포인트
    private int point;
    
    //장바구니 페이지에서 사용자가 구매하고자 하는 수량까지 곱하여 받을 수 있는 포인트
    private int totalPoint;
    
    /* 상품 이미지 */
	private List<AttachImageVO> imageList;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public double getBookDiscount() {
		return bookDiscount;
	}

	public void setBookDiscount(double bookDiscount) {
		this.bookDiscount = bookDiscount;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
    
	/* salePrice와 totalPrice의 변수 값을 초기화 해주는 메서드 */
	public void initSaleTotal() {
		//할인을 적용한 상품 한 개의 판매가격
		this.salePrice = (int)(this.bookPrice * (1-this.bookDiscount));
		//총 가격 = 판매가격 * 수량
		this.totalPrice = this.salePrice * this.bookCount;
		/* point정보 */
		this.point = (int)(Math.floor(this.salePrice*0.05));
		this.totalPoint = this.point * this.bookCount;
	}

	public List<AttachImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<AttachImageVO> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		return "OrderPageItemDTO [bookId=" + bookId + ", bookCount=" + bookCount + ", bookName=" + bookName
				+ ", bookPrice=" + bookPrice + ", bookDiscount=" + bookDiscount + ", salePrice=" + salePrice
				+ ", totalPrice=" + totalPrice + ", point=" + point + ", totalPoint=" + totalPoint + ", imageList="
				+ imageList + "]";
	}
}

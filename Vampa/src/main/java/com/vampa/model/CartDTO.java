package com.vampa.model;

public class CartDTO {
	/*처음 4개 : vam_cart 테이블의 속성*/
    private int cartId;
    
    private String memberId;
    
    private int bookId;
    
    private int bookCount;
    
    //vam_book의 속성(vam_cart와 join하여 3개 변수값을 장바구니 페이지에 뿌려주기)
    private String bookName;
    
    private int bookPrice;
    
    private double bookDiscount;
    
    // 추가
    //할인을 적용한 상품 한 개의 판매가격
    private int salePrice;
    
    //총 가격 = 판매가격 * 수량
    private int totalPrice;
    
    /* 게터&세터 (단,salePrice와 totalPrice의 setter는 생성안함) */
    public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

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

	public int getTotalPrice() {
		return totalPrice;
	}

	/* salePrice와 totalPrice의 변수 값을 초기화 해주는 메서드 */
	public void initSaleTotal() {
		//할인을 적용한 상품 한 개의 판매가격
		this.salePrice = (int)(this.bookPrice * (1-this.bookDiscount));
		//총 가격 = 판매가격 * 수량
		this.totalPrice = this.salePrice * this.bookCount;
	}
	
	@Override
	public String toString() {
		return "CartDTO [cartId=" + cartId + ", memberId=" + memberId + ", bookId=" + bookId + ", bookCount="
				+ bookCount + ", bookName=" + bookName + ", bookPrice=" + bookPrice + ", bookDiscount=" + bookDiscount
				+ ", salePrice=" + salePrice + ", totalPrice=" + totalPrice + "]";
	}
}

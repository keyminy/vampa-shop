package com.vampa.model;

import lombok.Data;

public class Criteria {
	   /* 현재 페이지 번호 */
    private int pageNum;
    
    /* 페이지 표시 개수 */
    private int amount;
    
    /* 검색 타입 */
    private String type;
    
    /* 검색 키워드 */
    private String keyword;
    
    /* Criteria 생성자 */
    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }
    
    /* Criteria 기본 생성자 */
    public Criteria(){
        this(1,10);
    }
    
    /* 검색 타입 데이터 배열 변환 */
    //https://kimvampa.tistory.com/182?category=771727
    //검색 조건(type)데이터를 배열로 변화한 데이터로 반환해줌.
    //Mybatis쿼리에서 #{typeArr} 파라매터를 호출하기 위해
    //getTypeArr() 게터를 호출하기때문에 typeArr 변수를 선언하지 않아도 해당 변수가 존재하는 것처럼 처리됨.
    public String[] getTypeArr() {
        return type == null? new String[] {}:type.split("");
    }

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", type=" + type + ", keyword=" + keyword + "]";
	}
    
    
}

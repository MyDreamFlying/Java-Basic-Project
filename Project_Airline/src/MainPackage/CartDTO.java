package MainPackage;

public class CartDTO extends DBConnection {

	String cart_no; // 장바구니 번호
	// -> 날짜 + 인덱스번호
	
	String mem_id; //  회원 아이디(Member테이블 참조)
	String cart_qty; // 장바구니에 담긴 상품 갯수
	String cart_sum; // 장바구니에 담긴 금액 총합
	public String getCart_no() {
		return cart_no;
	}
	public void setCart_no(String cart_no) {
		this.cart_no = cart_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getCart_qty() {
		return cart_qty;
	}
	public void setCart_qty(String cart_qty) {
		this.cart_qty = cart_qty;
	}
	public String getCart_sum() {
		return cart_sum;
	}
	public void setCart_sum(String cart_sum) {
		this.cart_sum = cart_sum;
	}
	
	
	
	
}

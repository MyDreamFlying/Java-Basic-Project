package MainPackage;

public class PaymentDTO {

	
	// 외부에서 생성자를 호출하지 못하게 막음(싱글톤으로 운영할 예정)
	private PaymentDTO() {}
	
	// 앞으로 외부에서 사용할 유일한 PaymentDTO 객체(싱글톤)
	private static PaymentDTO payment = new PaymentDTO(); 
	
	
	
	String pay_num; //결제번호
	String pay_mid; //회원아이디
	int pay_amt; //결제금액
	int pay_tno; //티켓번호
	String pay_date; //결제일자
	boolean hasPayed; // 결제유무

	
	// PaymentDTO의 객체를 리턴 (다른 클래스에서 이 클래스로 객체를 받아 쓸 예정)
	public static PaymentDTO getPaymentDTO_Instance() {
		
		return payment;
		
	}
	
	public String getPay_num() {
		return pay_num;
	}

	public void setPay_num(String pay_num) {
		this.pay_num = pay_num;
	}

	public String getPay_mid() {
		return pay_mid;
	}

	public void setPay_mid(String pay_mid) {
		this.pay_mid = pay_mid;
	}

	public int getPay_amt() {
		return pay_amt;
	}

	public void setPay_amt(int pay_amt) {
		this.pay_amt = pay_amt;
	}

	public int getPay_tno() {
		return pay_tno;
	}

	public void setPay_tno(int pay_tno) {
		this.pay_tno = pay_tno;
	}

	public String getPay_date() {
		return pay_date;
	}

	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}

	public boolean hasPayed() {
		return hasPayed;
	}

	public void setHasPayed(boolean hasPayed) {
		this.hasPayed = hasPayed;
	}


	
	
	
}

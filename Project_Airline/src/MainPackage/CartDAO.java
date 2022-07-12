package MainPackage;

import java.text.SimpleDateFormat;

public class CartDAO extends DBConnection{

	
	// DB연결 
	public void accessDB() {
		DBConnection.getConnection();
	}
	
	
	
	public static String getTime() {
		
		// 현재 날짜와 시간을 문자열로 형변환해서 반환
		 // 현재 날짜 구하기        
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		 //SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년 MM월 dd일");         
		 String now = sdf.format(System.currentTimeMillis());        
		 //String nowTime2 = sdf2.format(System.currentTimeMillis());
	
		return now;
	}
	
	
	// 상품(티켓)을 장바구니에 담음
	public void addTicket() {
		
	}
	
	
	
	
}

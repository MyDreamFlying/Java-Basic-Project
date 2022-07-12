package MainPackage;

import java.util.ArrayList;

//실질적인 작업이 이루어지는 클래스 (DB연동, CRUD 등...)
public class TicketDAO extends DBConnection{

	
	ArrayList<String> ticList = new ArrayList<String>(); // 티켓 목록을 저장할 변수
	
	// 데이터베이스 연결
	public void accessDB() {
		DBConnection.getConnection();
	}
	
	
	// 항공편 조회
	public void showList() {
		
	}
	
	// 구매한 모든 티켓 보여줌
	public void showTicketList() {
		/*
		 * 티켓 목록을 보여주고 그 중에서 하나를 선택해서
		 * 디테일하게 보여줌
		 * 
		 * 
		 * 
		 */
	}
	
	// 구매한 하나의 티켓을 보여줌
	public void showDetailTicket() {
		// 디켓의 상세정보를 표기
	}
	
	
	
}

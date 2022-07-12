package MainPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * PAYMENT 테이블
 * 
 * PAY_NUM
 * PAY_AMT
 * PAY_DATE
 * PAY_EXIST
 * CART_NO
 * 
 * 
 * 
 */







//실질적인 작업이 이루어지는 클래스 (DB연동, CRUD 등...)
public class PaymentDAO extends DBConnection {

	// 외부에서 생성자를 호출하지 못하게 막음(싱글톤으로 운영할 예정)
	private PaymentDAO() {
	}

	// 앞으로 외부에서 사용할 유일한 PaymentDAO 객체(싱글톤)
	private static PaymentDAO payment = new PaymentDAO();

	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;




	// PaymentDAO의 객체를 리턴 (다른 클래스에서 이 클래스로 객체를 받아 쓸 예정)
	public static PaymentDAO getPaymentDAO_Instance() {
		
		return payment;

	}

// utils methods by here
//----------------------------------------------------------------------	
	
	// 결제 기능
	public static void pay() {

	
		
		/*
		 * MemberDAO클래스에서 현재 접속하고 있는 회원의 ID를 가져옴
		 * 
		 * 
		 */
		
		
		
		try {
			
			String cart_no = CartDAO.getTime(); // 년월일시분초 -> cart_num
			String id = MemberDAO.user_id; // 현재 접속하고 있는 유저의 ID를 반환
			int n = FlightDAO.getCartList().size(); // 수량			
			int price = 500000;
			
			String query = "INSERT INTO CART(CART_NO, MEM_ID, CART_QTY, CART_SUM) "
					+ "VALUES('" + cart_no + "'," +  "'" + id + "' ," + n + ", " + price + ")"; 
			
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			
			query = "SELECT MEM_DEPM FROM MEMBER WHERE MEM_ID = '" + id + "' ";
			
			rs = stmt.executeQuery(query);
			int mem_depm = 0;
			while(rs.next()) {
				mem_depm = rs.getInt("MEM_DEPM");
			}
			
			query = "UPDATE MEMBER SET MEM_DEPM = " + (mem_depm - price) + " WHERE MEM_ID = '" + id + "'";
			
			stmt.executeQuery(query);
			
			System.out.println("결제가 완료되었습니다.");
			
			
			for(int i = 0; i < FlightDAO.cart.size(); i++) {
				FlightDAO.cart.remove(i);
			}
			
			
		}catch(SQLException sqle) {
			System.out.println("SQL EXCEPTION : " + sqle);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	// 결제유무 반환
	public boolean payCheck() {
		boolean isPayed = false;

		return isPayed;
	}

	// 결제일자 반환
	public String payDate() {

		return "";
	}

}

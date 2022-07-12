package MainPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// 항공 관련 클래스
public class FlightDAO {

	
	static ArrayList<Integer> cart = new ArrayList();
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	

	public FlightDAO() {
		conn = DBConnection.getConnection();
	}
	
	
	
	//현재 항공 인덱스를 담은 리스트를 반환(CartDAO에서 사용예정)
	public static ArrayList<Integer> getCartList(){
		return cart;
	}
	
	
	// 항공조회
	public void showList() {

		 String query = "SELECT RANK() OVER(ORDER BY IDX) \"NO.\" "
		 		+ ", FLIGHT_NO, AIR_CODE,"
		 		+ "APT_NM, FLIGHT_DEPA, FLIGHT_DEPT,"
		 		+ "FLIGHT_DEP FROM AIRPORT A, FLIGHT F "
		 		+ "WHERE A.APT_AVL = F.APT_AVL ";
		 		
	
		 
		
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			
			System.out.println("      ┌────항공편───────항공사 코드────도착지───────출발정보──────출발 공항────┐");
			while(rs.next()) {
				
				int i = rs.getInt("NO.");
				String flight_no = rs.getString("FLIGHT_NO");
				String air_code = rs.getString("AIR_CODE");
				String apt_nm = rs.getString("APT_NM");
				String flight_depa = rs.getString("FLIGHT_DEPA");
				String flight_dept = rs.getString("FLIGHT_DEPT");
				String flight_dep = rs.getString("FLIGHT_DEP");
				
				System.out.printf("%2d \t %20s \t %8s \t %20s \t %20s \t %13s",i,flight_no,air_code,apt_nm, flight_depa + " " + flight_dept, "인천공항 \n");
				//System.out.println(i + "    │" + flight_no + " \t \t " + air_code + " \t \t \t" + apt_nm + " \t \t " + flight_depa + " " + flight_dept + " \t \t " + "인천공항" );
				
			
				
//				System.out.println("항공편 : " + air_code);
//				System.out.println("항공사 코드 : " + apt_nm);
//				System.out.println("도착지  : " + flight_depa);
//				System.out.println("출발일 : " + flight_dept);
//				System.out.println("출발 공항 : " + flight_dep);
				
				
			}
			
		}catch(SQLException sqle) {
			System.out.println("SQL ERROR : " + sqle);
		}catch(Exception e) {
			System.out.println("UNKNOWN ERROR : " + e);
		}
		
		
		
	}
	
	
	
	
	// 항공편 리스트에 담기
	public static void add() {
		/*
		 * 항공조회를 한 후 원하는 인덱스 번호를 add(인덱스) 형식으로 호출하면
		 * List에 담은 후 데이터베이스에 저장
		 * 
		 * 
		 */
		try {
			System.out.print("번호 입력 >> ");
			int n = Integer.parseInt(bf.readLine());
			cart.add(n);
		}catch(Exception e) {
			System.out.println("UNKNOWN ERROR : " + e);
		}
		
		
		
		
		
	}
	
	public static void showAll() {
		
		for(Integer prod : cart) {
			
			String query = "SELECT FLIGHT_NO, "
					+ "AIR_CODE, "
					+ "APT_NM, "
					+ "FLIGHT_DEPA, "
					+ "FLIGHT_DEPT  "
					+ "FROM AIRPORT A, FLIGHT F "
					+ "WHERE A.APT_AVL = F.APT_AVL "
					+ "AND F.IDX = " + prod;
					
			if(FlightDAO.cart.size() > 0) {
				try {
					
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					
					System.out.println();
					//System.out.println(MemberDAO.user_name + "님이 바구니에 담으신 항공편 ㅎㅎ");
					System.out.println("┌────항공편───────항공사 코드────도착지───────출발정보──────출발 공항────┐");
					while(rs.next()) {
						
						String flight_no = rs.getString("FLIGHT_NO");
						String air_code = rs.getString("AIR_CODE");
						String apt_nm = rs.getString("APT_NM");
						String flight_depa = rs.getString("FLIGHT_DEPA");
						String flight_dept = rs.getString("FLIGHT_DEPT");
						//String flight_dep = rs.getString("FLIGHT_DEP");
						
						System.out.printf("%20s \t %8s \t %20s \t %20s \t %13s",flight_no,air_code,apt_nm, flight_depa + " " + flight_dept, "인천공항 \n");
						
						System.out.println();
						
					}
					
				}catch(SQLException sqle) {
					System.out.println("SQL ERROR : " + sqle);
				}catch(Exception e) {
					System.out.println("UNKNOWN ERROR : " + e);
				}
				
			}else {
				System.out.println("장바구니가 비어있습니다.");
			}
			
			
			
		}
		
		
	}
	
	
	
	

}

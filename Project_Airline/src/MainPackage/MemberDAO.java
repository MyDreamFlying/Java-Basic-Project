package MainPackage;

// Referenced Link : https://flatsun.tistory.com/385

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//실질적인 작업이 이루어지는 클래스 (DB연동, CRUD 등...)
public class MemberDAO extends DBConnection{
	
	static String user_name = "";
	static String user_id = "";
	static boolean adminLog = false;  // 관리자가 로그인 했는제 확인
	/*
	 * 
	 * -기능(Functions)-
	 * 1. 회원가입(SignIn)
	 * 2. 로그인(LogIn) , 로그아웃(Logout)
	 * 3. 회원탈퇴(delAccount)
	 * 4. 예매내역 확인(checkReserve)
	 * 5. 예매내역 수정(updateReserve)
	 * 6. 내정보수정(updateInfo)
	 * 7. 항공권선택(pickFlight)
	 * 8. 항공권결제(payFlight)
	 * 
	 */
	
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static boolean isLogined = false; // <- login status
	static MemberDAO worker = new MemberDAO();
	MemberDTO memDTO;

	
	// 먼저 DB에 연동.
	public static void accessDB() {
		conn = DBConnection.getConnection();
	}
	
	// 현재 접속하고 있는 회원의 아이디를 반환
	public static String getMem_id() {
		return user_id;
	}
	
	// 로그인 작성완료
	public static boolean logIn(String id, String pw) {
		// This method returns true for login success
		
		
		
		String query = "SELECT * FROM MEMBER WHERE MEM_ID = " + " '" + id 
									+ "' " + " AND " + "MEM_PW = " + " '" + pw + "'";
		
		
		try {
			
			stmt = conn.createStatement();
			//stmt.executeQuery(query);
			rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				// 사용자가 입력한 아이디와 비번이 데이터베이스에서 일치하는게 존재
				user_name = rs.getString("MEM_NM");
				user_id = rs.getString("MEM_ID");
				isLogined = true;
				
			}else {
				System.out.println("아이디/비밀번호가 일치하지 않거나 가입된 회원이 아닙니다.");
				
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return isLogined;
	}
	
	
	// 회원가입
	public static void signIn() {
		/*
		 * 사용자 회원가입 메소드
		 * 사용자가 입력해야 할 정보
		 * 1. 회원아이디 (중복검사 실시)
		 * 2. 비밀번호
		 * 3. 비밀번호 확인 (틀리면 처음으로 돌아감) -> 보류
		 * 4. 주민번호 (앞, 뒤 구분해서 내부적으론 자를거임)
		 * 5. 이름
		 * 
		 * 
		 * 
		 * 회원 테이블 : MEMBER
		 * 
		 * MEM_ID (NOT NULL)
		 * MEM_PW (NOT NULL)
		 * MEM_RGON1 (NOT NULL)
		 * MEM_RGON2 (NOT NULL)
		 * MEM_NM (NOT NULL)
		 * MEM_TEL (NOT NULL)
		 * MEM_ADD (NULL AVAIBLE)
		 * MEM_PP (NULL AVAIABLE)
		 * MEM_DEPM (NULL AVAIBLE)
		 * MEM_ENAME (NOT NULL)
		 * 
		 * 
		 * 
		 */
		try {
			
			stmt = conn.createStatement();
			
			
			//System.out.println("메뉴로 돌아가기 : m , 뒤로가기 : < ");
			while(true) { // while starts
				System.out.print("사용할 아이디 입력 : "); // 아이디 입력
				String mem_id = bf.readLine();
				
				// 테이블은 아직 만들어지지 않았으므로 추후 만들예정, 지금은 다른 DB에서 테스트중.
				rs = stmt.executeQuery("SELECT * FROM MEMBER WHERE MEM_ID = " + "\'" + mem_id + "\'");
				
				//입력된 아이디로 조회가 된다면(rs에 값이 들어가있다면)
				if(rs.next()) {
					System.out.println("해당 아이디는 이미 존재하는 아이디입니다.");
					continue;
				}else {					
					
/*
 * MEM_ID (NOT NULL)
		 * MEM_PW (NOT NULL)
		 * MEM_RGON1 (NOT NULL)
		 * MEM_RGON2 (NOT NULL)
		 * MEM_NM (NOT NULL)
		 * MEM_TEL (NOT NULL)
		 * MEM_ADD (NULL AVAIBLE)
		 * MEM_PP (NULL AVAIABLE)
		 * MEM_DEPM (NULL AVAIBLE)
		 * MEM_ENAME (NOT NULL)
 * 
 * 
 */
					
					System.out.print("비밀번호 입력 : "); // 비밀번호 입력 (MEM_PW)
					String mem_pw = bf.readLine();
					System.out.print("주민번호(앞자리) : "); // 주민번호 앞자리 입력 (MEM_RGON1)
					String mem_regon1 = bf.readLine();
					System.out.print("주민번호(뒷자리) : "); // 주민번호 뒷자리 입력(MEM_RGON2)
					String mem_regon2 = bf.readLine();
					System.out.print("이름 입력 : "); // 이름입력 (MEM_NM)
					String mem_nm = bf.readLine();
					
					
					System.out.print("전화번호 입력 : "); // 전화번호 입력 (MEM_TEL)
					String mem_tel = bf.readLine();
					
					String telCheckQuery = "SELECT * FROM MEMBER WHERE MEM_TEL = '" + mem_tel + "'";
					
					stmt = conn.createStatement();
					rs = stmt.executeQuery(telCheckQuery);
					if(rs.next()) {
						System.out.println("WARNING : 해당 전화번호로 이미 가입된 아이디가 있습니다.");
						continue;
					}
					
					
					
					
					System.out.print("주소 : "); // 주소입력 (MEM_ADD)
					String mem_add =  bf.readLine();
					System.out.print("여권번호 : "); // MEM_PP
					String mem_pp = bf.readLine();
					System.out.print("보유금액 : "); // MEM_DEPM
					String mem_depm = bf.readLine();
					System.out.print("영문성명 : "); // MEM_ENAME
					String mem_ename = bf.readLine(); 
					
					
					
					/*
					 * Insert문으로 데이터베이스에 집어넣은 후 while문 break
					 * 
					 */
					
					
					String sign_query = "INSERT INTO MEMBER(MEM_ID, MEM_PW,"
							+ "MEM_RGON1, MEM_RGON2, MEM_NM, MEM_TEL, MEM_ADD,"
							+ "MEM_PP, MEM_DEPM, MEM_ENAME) "
							+ " VALUES(" + "'" + mem_id + "' , " +
							"'" + mem_pw + "' , " +
							"'" + mem_regon1 + "' , " +
							"'" + mem_regon2 + "' , " + 
							"'" + mem_nm + "' , " + 
							"'" + mem_tel + "' , " + 
							"'" + mem_add + "' , " + 
							"'" + mem_pp + "' , " + 
							"'" + mem_depm + "' ," + 
							"'" + mem_ename + "')";
							
					
					System.out.println("┌∑── " + mem_nm + "님 회원가입을 축하드립니다 ──∃┐");
					System.out.println("└────────────────────┘");
					
					
					
					stmt.executeQuery(sign_query);
					break;
					 
					
				}
				
				
				
				
				
							
				
			} // while ends
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	// 회원탈퇴
	public static void deleteAccount() { //회원탈퇴 시작
		
		String query = "DELETE FROM MEMBER WHERE MEM_ID = " + "'" + user_id + "'";
	
		
		
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			user_name = "";	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	} // 회원탈퇴 끝
	
	//회원정보 수정
	public static void update() { // 회원정보수정 시작
		
		/*
		 * 회원 스스로가 수정할 수 있는 항목
		 * 1. 비밀번호
		 * 2. 이름
		 * 3. 영어이름
		 * 4. 전화번호
		 * 5. 주소
		 * 6. 여권번호
		 */
		
		// 회원의 정보를 먼저 출력해줌. (자신의 정보를 보면서 무엇을 수정하고 싶은지 선택할 수 있게)
		try {
			
			String query = "SELECT * FROM MEMBER WHERE MEM_ID = " + "'" + user_id + "'";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				
				String mem_id = rs.getString("MEM_ID");
				String mem_pw = rs.getString("MEM_PW");
				String mem_rgon1 = rs.getString("MEM_RGON1");
				String mem_rgon2 = rs.getString("MEM_RGON2");
				String mem_nm = rs.getString("MEM_NM");
				String mem_ename = rs.getString("MEM_ENAME");
				String mem_tel = rs.getString("MEM_TEL");
				String mem_add = rs.getString("MEM_ADD");
				String mem_pp = rs.getString("MEM_PP");
				String mem_depm = rs.getString("MEM_DEPM");
				
				
	System.out.println("변경하고 싶으신 항목 옆에 적힌 명령어를 입력하여 선택하시면 됩니다.");
				
				System.out.println("           ♡┌─────────" + mem_nm + "님의 회원정보 ─────────┐♡");
				System.out.println("              │─────────" + "↑ㅎω σ"   + "                    ────────  │");
				System.out.println("              │─────────" + "아이디               :\t"  + mem_id);
				System.out.println("              │─────  [pw]─" + "비밀번호             :\t"  + mem_pw);
				System.out.println("              │─────────" + "주민번호(앞자리) :\t"  + mem_rgon1);
				System.out.println("              │─────────" + "주민번호(뒷자리) :\t"  + mem_rgon2);
				System.out.println("              │─────[enm]─" + "영문성명             :\t"  + mem_ename);
				System.out.println("              │─────  [tel]─" + "전화번호             :\t"  + mem_tel);
				System.out.println("              │─────[add]─" + "주소                    :\t"  + mem_add);
				System.out.println("              │─────   [pp]─" + "여권번호             :\t"  + mem_pp);
				System.out.println("              │─────────" + "보유금액             :\t"  + mem_depm);
				System.out.println("              └─────────────────────────────");
				
				
			}
			
			System.out.print(user_name + " >> ");
			String input = bf.readLine();
			
			

			
			if(input.equalsIgnoreCase("pw")) { // 비밀번호 변경 선택
				
				// 단순히 updateTo메소드를 사용하기 위한 임시 객체를 만듬
				//new MemberDAO().updateTo("비밀번호", "MEM_PW");
				worker.updateTo("비밀번호", "MEM_PW");
				
				
			}else if(input.equalsIgnoreCase("enm")) { // 영문성명 변경 선택
				
				worker.updateTo("영문성명", "MEM_ENAME");
				
			}else if(input.equalsIgnoreCase("tel")) { // 전화번호 변경 선택
				
				worker.updateTo("전화번호", "MEM_TEL");
				
			}else if(input.equalsIgnoreCase("add")) {// 주소 변경 선택
				
				worker.updateTo("주소", "MEM_ADD");
				
			}else if(input.equalsIgnoreCase("pp")) { // 여권번호 변경 선택
				
				worker.updateTo("여권번호", "MEM_PP");
				
			}else if(input.equals("<")) {
				Views.atLogin();
				System.out.print(user_name + " >> ");
				MenuBar.menuView();
			}
			else {
		
				
				System.out.println("                           ─── ");
				System.out.println("                         │        │");
				System.out.println("                         │        │");
				System.out.println("                         │        │");
				System.out.println("                         │        │");
				System.out.println("┌───   ───            ─── ───┐");
				System.out.println("│          │         │       │        │           │");
				System.out.println("┌─────────────────┐");
				System.out.println("│올바르지 않은 입력형식입니다. (- -)     │");
				System.out.println("└─────────────────┘");
				
				System.out.print(user_name + " >> ");
				MenuBar.menuView();
				
				
			}
			
			
		}catch(Exception e) {
			
		}
		
		//최신커밋
	}// 회원정보수정 끝
	
	
	public void updateTo(String what_to_change, String column ) {
		// 들어갈 매개변수 : 변경할 항목, 그 항목의 데이터베이스 컬럼명
		
		
		String qry = "";
		
		try {
			
			System.out.println("변경할" + what_to_change  + " 입력!");
			System.out.print(user_name + " >> ");
			String tmp = bf.readLine();
			
			qry = "UPDATE MEMBER SET " +  column + " = " + "'" + tmp + "'" + " WHERE MEM_ID = "
					+ "'" + user_id + "'";
			
			stmt = conn.createStatement();
			
			stmt.executeQuery(qry);
			System.out.println();
			System.out.println("┌─────────────────┐");
			System.out.println("│"  + what_to_change + " 변경 완료      ");
			System.out.println("└─────────────────┘");
			
			Views.atLogin();
			System.out.print(user_name + " >> ");
			MenuBar.menuView();
			
		}catch(SQLException sqle) {
			System.out.println("SQL ERROR : " + sqle);
		}catch(Exception e) {
			System.out.println("Unknown Error : " + e);
		}
		
		
	}
	
	
	
	// 아이디/비밀번호 찾기
	public static void find() {
		// 이름이랑 전화번호를 입력받아 아이디/비밀번호 찾기
		String query = "";
		
		try {
			
			System.out.println("[id] ->  아이디 찾기");
			System.out.println("[pw] -> 비밀번호 찾기");
			System.out.print(user_name + " >> ");
			String input = bf.readLine();
			
			if(input.equalsIgnoreCase("id")) {
//				 아이디 찾기 할떄는 이름이랑 전화번호를 받고
				System.out.print("┌─┠ 이름 입력 : ");
				String name = bf.readLine();
				System.out.print("└─┠ 전화번호 : ");
				String tel = bf.readLine();
				
				query = "SELECT MEM_ID FROM MEMBER WHERE MEM_NM = " + "'" + name + "' AND "
						+ " MEM_TEL = '" + tel + "'";
		
				
				String qry = "SELECT MEM_ID FROM MEMBER WHERE MEM_TEL = '"  + tel + "'";
				
				//01052364512
				stmt = conn.createStatement();
				rs = stmt.executeQuery(qry);
				
				while(rs.next()) {
					System.out.println(name + "의 아이디는 " + rs.getString("MEM_ID") + "입니다. ");
				}
				
				
				while(rs.next()) {
					System.out.println(rs.getString("MEM_ID"));
				}
				
				
				MenuBar.menuView();
				
			}else if(input.equalsIgnoreCase("pw")) {
				// 비밀번호를 찾을때는 아이디, 이름, 전화번호 받기
				try {
					System.out.print("┌─┟ 아이디 입력 : ");
					String id = bf.readLine();
					System.out.print("│─┠ 이름 입력 : ");
					String name = bf.readLine();
					System.out.print("└─┠ 전화번호 입력 : ");
					String tel = bf.readLine();
					
					String qry = "SELECT MEM_PW FROM MEMBER WHERE MEM_ID = '" + id + "' AND " + 
										"MEM_NM = '" + name + "' AND MEM_TEL = '" + tel + "'";
					
					stmt = conn.createStatement();
					rs = stmt.executeQuery(qry);
					if(rs.next()) {
						try {
							System.out.print("└─ ∬ 새로운 비밀번호 입력 : ");
							String pw = bf.readLine();
							
							qry = "UPDATE MEMBER SET MEM_PW = '" + pw + "' WHERE MEM_ID = '" + id + "'";
							stmt.executeQuery(qry);
							System.out.println("└─ UPDATE MESSAGE : 비밀번호가 정상적으로 변경되었습니다.");
							
							MenuBar.startView();
							MenuBar.menuView();
							
						}catch(Exception e) {
							System.out.println("UNKNOWN ERROR : " + e);
						}
					}else {
						System.out.println("┌─ 등록된 회원이 아닙니다. ─┐");
						Views.atLogin();
						MenuBar.menuView();
					}
							
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}else {
				System.out.println("WARNING : 입력이 옳바르지 않습니다.");
				MenuBar.menuView();
			}
		}catch(SQLException sqle) {
			System.out.println("SQL ERROR : " + sqle);
		}catch(Exception e) {
			System.out.println("Unknown Error : " + e);
		}
		
	}
	
	
	
	public static void searchFlights() {
		
		FlightDAO fdao = new FlightDAO();
		fdao.showList();
		
	}
	
	
	public void allClear() {
		try {
			if(rs != null) {
				rs.close();
			}
			
			if(stmt != null) {
				stmt.close();
			}
			
			if(conn != null) {
				conn.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

package MainPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 실질적인 작업이 이루어지는 클래스 (DB연동, CRUD 등...)

public class AirlineDAO extends DBConnection {
	static Connection conn;
	// 데이터베이스 연결
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static int rs1 = 0;
	static Statement stmt = null;
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static AirlineDTO airDTO;

	public static void accessDB() throws ClassNotFoundException, SQLException {
		conn = DBConnection.getConnection();
	}

	public static void insert() throws IOException, SQLException, ClassNotFoundException {// 항공사 추가
		airDTO = new AirlineDTO();
		conn = DBConnection.getConnection();
		System.out.println("항공사정보 추가");
		System.out.print("항공사 고유번호: ");
		airDTO.setAir_code(bf.readLine());// 항공사 고유번호

		System.out.print("항공사 명: ");
		airDTO.setAir_name(bf.readLine());// 항공사 명

		String sql = "INSERT INTO AIRLINE (AIR_CODE, AIR_NAME) " + "VALUES(?, ?)";

		String code = airDTO.getAir_code();

		String name = airDTO.getAir_name();

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM AIRLINE WHERE AIR_CODE = " + "\'" + code + "\'");

		if (rs.next()) {
			System.out.println("중복된 항공사코드가 존재함");
			System.out.println("다시");
			System.out.println("------------------------");
			insert();
		} else {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);
			pstmt.setString(2, name);
			rs1 = pstmt.executeUpdate();

			if (rs1 != 0) {
				System.out.println("추가완료");
				System.out.println("------------------------");
				AdminView.menuView();
			}
		}

	}// 항공사 추가끝

	public static void tableView() throws SQLException, IOException, ClassNotFoundException {

		conn = DBConnection.getConnection();
		String sql = "SELECT * FROM AIRLINE";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		System.out.println("항공사코드 " + "항공사명");
		while (rs.next()) {
			String code = rs.getString("AIR_CODE");
			String name = rs.getString("AIR_NAME");
			System.out.println(code + "  " + name);
		}
		System.out.println();
		System.out.println("---------------------");
		AdminView.showAirline();// 항공사 정보관리로 이동

	}

	public static void update() throws SQLException, IOException, ClassNotFoundException {
		accessDB();
		String sql = "";

		System.out.println("항공사 고유번호는 수정불가");
		System.out.println("1. 항공사명 수정");
		System.out.println("2. 항공사코드 삭제");
		String input = bf.readLine();
		if (input.equalsIgnoreCase("1")) {
			System.out.print("어느항공사의 명을 수정할까요? 고유번호를 입력하시오 : ");
			String code = bf.readLine();
			System.out.println("항공사 명을 무엇으로 바꿀까요?");
			String name = bf.readLine();
			sql = "UPDATE AIRLINE " + "SET AIR_NAME = ? " + "WHERE AIR_CODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, code);
			rs1 = pstmt.executeUpdate();
			if (rs1 == 2) {
				System.out.println("수정완료");
				AdminView.showAirline();
			}

		} else if (input.equalsIgnoreCase("2")) {
			System.out.print("정말 삭제하시겠습니까?: yes or no");
			String rep = bf.readLine();
			if (rep.equalsIgnoreCase("yes")) {
				System.out.println("삭제를 진행하겠습니다.");
				System.out.print("삭제할 항공코드를 입력하세요: ");
				String code = bf.readLine();
				String sql1 = "SELECT AIR_CODE FROM AIRLINE WHERE AIR_CODE = '"+code+"'";
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery(sql1);
				
				if(rs.next()) {
					sql = "DELETE FROM AIRLINE WHERE AIR_CODE = ?";
					rs1 = 0;
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, code);
					
					
					rs1 = pstmt.executeUpdate();
					
					if (rs1 == 1) {
						System.out.println("삭제완료");
						System.out.println("-------------------------");
						AdminView.showAirline();
					}

				}else {
					System.out.println("해당 항공사코드가 없습니다. ");
					System.out.println("------------------------------------");
					

				}
				
				
			}else if(rep.equalsIgnoreCase("no")) {
				AdminView.showAirline();
			}else{
				System.out.println("잘못 입력하였습니다. 다시");
				System.out.println("--------------------------------");
				update();
			}

		}

	}

}

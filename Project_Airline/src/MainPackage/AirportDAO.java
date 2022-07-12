package MainPackage;

//실질적인 작업이 이루어지는 클래스 (DB연동, CRUD 등...)
public class AirportDAO extends DBConnection{

	// 데이터베이스 연결
	public void accessDB() {
		DBConnection.getConnection();
	}
	
}

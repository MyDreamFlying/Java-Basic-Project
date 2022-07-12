package MainPackage;

public class Views {

	public static void atLogin() {
		System.out.println();
		System.out.println("╂ Logged in Command words ╂");
		System.out.println("┌──────────────┐");
		System.out.println("│logout(로그아웃)\t\t\t     ");
		System.out.println("│update(회원수정/정보조회)     ");	
		System.out.println("│delete(회원탈퇴)\t\t\t     ");
		System.out.println("│flight(항공조회/예약)\t     ");
		System.out.println("│showbook(예약내역)\t\t     ");
		System.out.println("│pay(결제)\t\t     ");
		System.out.println("└──────────────┘");
		System.out.println();
		
	}
	
	public static void atLogout() {
		System.out.println();
		System.out.println(" Lobby Command words");
		System.out.println("┌──────────────┐");
		System.out.println("│login(로그인) \t\t\t      │");
		System.out.println("│logout(로그아웃)\t\t\t      │");
		System.out.println("│signin(회원가입)\t\t\t      │");
		System.out.println("│find(아이디/비밀번호 찾기)      │");
		System.out.println("└──────────────┘");
		System.out.println();
		System.out.print(MemberDAO.user_name + " >> ");
	}
	
	
	
	public void atAfterBook() {
		System.out.println();
		System.out.println("╂ Logged in Command words ╂");
		System.out.println("┌──────────────┐");
		System.out.println("│logout(로그아웃)\t\t\t      │");
		System.out.println("│update(회원수정/정보조회)      │");	
		System.out.println("│delete(회원탈퇴)\t\t\t      │");
		System.out.println("│flight(항공조회/예약)\t     │");
		System.out.println("│showbook(예약내역)\t\t     │");
		System.out.println("│pay(결제)\t\t     │");
		System.out.println("└──────────────┘");
		System.out.println();
	}
	
	public void atFind() {
		
	}
	
	
	
	
}

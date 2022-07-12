package MainPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// 사용자에게 보여질 화면들을 정의 해놓은 클래스
public class MenuBar {

	static boolean atFlight = false; // 사용자가 현재 flight 화면을 보고 있는지 판단
	static boolean isLogined = false; // 회원의 로그인 상태 (로그인 : true, 로그아웃 : false)
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); // Scanner의 기능
	
	public static String Input() {
		
		String input = "";
		
		try {
			input = bf.readLine();
		}catch(Exception e) {
			e.printStackTrace();
		}

		return input;
		
	}
	
	
	public static void startView() {
		
		System.out.println();
		System.out.println(" Lobby Command words");
		System.out.println("┌──────────────┐");
		System.out.println("│login(로그인) \t\t\t      │");
		System.out.println("│signin(회원가입)\t\t\t      │");
		System.out.println("│find(아이디/비밀번호 찾기)      │");
		System.out.println("└──────────────┘");
		System.out.println();
		System.out.print(MemberDAO.user_name + " >> ");
	}
	
	
	// 사용자가 처음 로그인 했을때와 앞으로 보여질 화면
	public static void menuView() {
		
		/*
		 * 
		 * 로그인, 회원가입, 항공조회, 항공예약,(이스터에그 : 코인캐기) ,회원정보수정
		 * 회원탈퇴
		 * 
		 * 
		 */
		
	
		
		try {
			
			
			String input = bf.readLine();

			
//-------------------------------------------------------------------------------			
			
			// 로그인
			if(input.equalsIgnoreCase("login")) { // 로그인 기능 
				if(!isLogined) {
					MemberDAO.accessDB(); // 데이터베이스 연동 (MemberDAO 클래스의 accessDB() 메소드 호출
					
					System.out.print("┌─ ∑ ID : ");
					String id = bf.readLine();
					System.out.print("└─ ∑ PW : ");
					String pw = bf.readLine();
					
					isLogined = MemberDAO.logIn(id, pw); // 로그인 하는 함수, MemberDAO 클래스의 login()메소드를 호출
					//System.out.println("isLogined : " + isLogined);
					
					
					if(isLogined) {
						
						// 로그인 되었을 때 보여질 화면
						Views.atLogin();
						System.out.println("┌─────────────────────────┐");
						System.out.println("│ " + MemberDAO.user_name + "님 방문을 환영합니다. ");
						System.out.println("└─────────────────────────┘");
						System.out.print(MemberDAO.user_name + " >> ");
						menuView();

						
					}else {
						System.out.println("로그인 실패");
						startView();
						menuView();
					}
				}else {
					System.out.println("WARNING : 이미 로그인 되있습니다.");
					startView();
					menuView();
				}
				
				
			} // 로그인 기능 끝
			
//-------------------------------------------------------------------------------			
			
			// 회원가입
			else if(input.equalsIgnoreCase("signin")){ // 회원가입 기능 시작
				MemberDAO.accessDB();
				if(isLogined) {
					System.out.println();
					System.out.println("WARNING : 회원가입을 하시려면 먼저 로그아웃을 하셔야 합니다.");
					Views.atLogin();
					System.out.print(MemberDAO.user_name + " >> ");
					menuView();
				}else {
					MemberDAO.signIn();
					Views.atLogin();
					System.out.print(MemberDAO.user_name + " >> ");
					menuView();
				}
				
				
			} // 회원가입 기능 끝

//-------------------------------------------------------------------------------
			
			// 로그아웃
			else if(input.equalsIgnoreCase("logout")) { // 로그아웃 기능 시작
				
				if(isLogined) { // 로그인이 되어있는 상태
					
					MemberDAO.user_name = "";
					System.out.println();
					System.out.println("SUCCESS : 정상적으로 로그아웃 하셨습니다.");
					isLogined = false;
					Views.atLogout();
					menuView();
					
				}else { // 로그인이 되어있지 않은 상태
					System.out.println();
					System.out.println("WARNING : 현재 로그인 되어있지 않습니다. 다시 확인해주세요.");
					startView();
					menuView();
				}
				
			}// 로그아웃 기능 끝
//-------------------------------------------------------------------------------
			
			
			//회원탈퇴
			else if(input.equalsIgnoreCase("delete")) { // 회원탈퇴 시작
				if(isLogined) {
					String tmp = "";
					System.out.print("정말로 회원탈퇴를 하실겁니까?(YES/NO) >> ");
					tmp = bf.readLine();
					if(tmp.equalsIgnoreCase("YES")) {
						System.out.println("┌──↓↓다음과 같은 문구를 작성해주세요 ↓↓──┐");
						System.out.println("│→ '" + MemberDAO.user_id + " is free'");
						
						System.out.print(MemberDAO.user_name + " >> ");
						tmp = bf.readLine();
						if(tmp.equals(MemberDAO.user_id + " is free")) {
							MemberDAO.deleteAccount();
							isLogined = false;
							System.out.println("ㆁω ㆁ → 정상적으로 탈퇴 하셨습니다.. 다음에 또 오시길..");
							Views.atLogout();
							menuView();
							
						}else if(tmp.equalsIgnoreCase("NO")) {
							Views.atLogin();
							System.out.print(MemberDAO.user_name + " >> ");
							menuView();
						}
						else {
							System.out.println("WARNING : 잘못 입력하셨습니다.");
							Views.atLogin();
							System.out.print(MemberDAO.user_name + " >> ");
							menuView();
						}
						//System.out.println(user_id );
					}else {
						Views.atLogin();
						menuView();
						System.out.println("조금 더 생각하고 와주세요.. ㅜㅜ");
					}
					
					
					
				}else {
					System.out.println("WARNING : 먼저 로그인을 해주세요.");
					startView();
					menuView();
				}
			}// 회원탈퇴 끝
			
			
//-------------------------------------------------------------------------------
			
			// 회원정보 수정
			else if(input.equalsIgnoreCase("update")) { // 회원정보 수정 시작
				if(isLogined) {
					
					MemberDAO.update();
					Views.atLogin();
					menuView();
					
				}else {
					System.out.println("WARNING : 로그인을 먼저 하셔야 합니다.");
					startView();
					menuView();
				}
			} // 회원정보 수정 끝
			

//-------------------------------------------------------------------------------			
			
			// 아아디/비밀번호 찾기
			else if(input.equalsIgnoreCase("find")) { // 아이디/비밀번호 찾기 시작
				if(!isLogined) {
					MemberDAO.accessDB();
					MemberDAO.find();
					startView();
					menuView();
				}else {
					System.out.println("현재 로그인 되있으십니다.");
					Views.atLogin();
					menuView();
				}
				
			}// 아이디/비밀번호 찾기 끝
			
//-------------------------------------------------------------------------------
			
			// 항공조회, 예약
			else if(input.equalsIgnoreCase("flight")) { //항공조회/예약 시작
	
				atFlight = true;
				
				MemberDAO.searchFlights();
				
				System.out.println("└────────────────────────────────────────────────────┘");
				System.out.println("바구니에 담는 방법");
				System.out.println("1.'add' 입력 후 엔터");
				System.out.println("2. 항공편 번호 입력 후 엔터");
				System.out.println("3. 바구니에 담기를 마치고 싶으면 'done' 입력");
				
				System.out.print(MemberDAO.user_name + " >> ");
				menuView();
				
			}// 항공조회/예약 끝
//-------------------------------------------------------------------------------
			
			
			// 뒤로가기
			else if(input.equalsIgnoreCase("<")) { // 뒤로가기 시작
				if(isLogined) {
					Views.atLogin();
					System.out.print(MemberDAO.user_name + " >> ");
					menuView();					
				}else {
					System.out.println("로그인이나 먼저 해");
					Views.atLogout();
					
					menuView();
				}
				
			}// 뒤로가기 끝
			
//-------------------------------------------------------------------------------			
			
			
			// 항공편 리스트에 담기
			// 입력한 데이터가 정수형 숫자이면( 문자열을 정수형으로 바꾸고 1로 나누었을 때 나머지가 0이면 정수)
			else if(input.equalsIgnoreCase("add")) { // 항공편 리스트에 담기 시작
				
				atFlight = true;
				
				FlightDAO.add();
				
				System.out.print(MemberDAO.user_name + " >> ");
				menuView();
				
				
			}// 항고편 리스트에 담기 끝
			
			
//-------------------------------------------------------------------------------
			
			else if(input.equalsIgnoreCase("showbook")) {
				
				FlightDAO.showAll();
				System.out.print(MemberDAO.user_name + " >> ");
				menuView();
				
			}
			
			
			else if(input.equalsIgnoreCase("done")) {
				// 예약번호 입력을 다 치고 난 후 done을 눌러 종료
				
				Views.atLogin();
				System.out.println("선택하신 항목들이 바구니에 모두 담겼습니다.");
				System.out.println();
				System.out.print(MemberDAO.user_name + " >> ");
				menuView();
				
			}

			
			
			
			
//-------------------------------------------------------------------------------			
			
			// 시작화면으로 돌아가기
			else if(input.equalsIgnoreCase("home")) {
				if(isLogined) {
					Views.atLogin();
					System.out.print(MemberDAO.user_name + " >> ");
					menuView();					
				}else {
					System.out.println("로그인 먼저~");
					Views.atLogout();
					menuView();
				}
			}
			
			else if(input.equalsIgnoreCase("pay")) {
				
				PaymentDAO.pay();
				Views.atLogin();
				System.out.print(MemberDAO.user_name + " >> ");
				menuView();		
				
			}

			
			
			else if(input.equalsIgnoreCase("exit")) {
				new MemberDAO().allClear();
				System.out.println("프로그램이 정상적으로 종료되었습니다. 다음에 또 오세요.");
			}
			
//-------------------------------------------------------------------------------				
			
			
			else if(input.equals("1004")) {
				new AdminView().menuView();
			}
			
			
			else {
				System.out.println("올바른 명령어가 아닙니다. 다시 입력해주세요!");
				System.out.print(MemberDAO.user_name + " >> ");
				menuView();
			}

//-------------------------------------------------------------------------------			
			
			
			
			
//-------------------------------------------------------------------------------
			
		
			
			

		// try	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}

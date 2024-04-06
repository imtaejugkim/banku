package bank;

import database.DatabaseManager;
import database.UserDao;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;
    private final AccountService accountService;
    private final String loggedInUserId;


    public MainMenu(String userId) {
        this.scanner = new Scanner(System.in);
        UserDao userDao = new UserDao(new DatabaseManager());
        this.accountService = new AccountService(userDao);
        this.loggedInUserId = userId;
    }

    public void show() {
        while (true) {
            System.out.println("============================================");
            System.out.println("          건국 은행에 오신 것을 환영합니다.");
            System.out.println("============================================");
            System.out.println("[1] 계좌 개설");
            System.out.println("[2] 예·적금");
            System.out.println("[3] 입금");
            System.out.println("[4] 송금");
            System.out.println("[5] 출금");
            System.out.println("[6] 계좌 및 예·적금 조회");
            System.out.println("[0] 종료");
            System.out.println("============================================");
            System.out.print("선택하실 메뉴 번호를 입력하세요 (0-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    accountService.openAccount(loggedInUserId);
                    break;
                case 2:
                    // 예·적금 로직
                    break;
                case 3:
                    // 입금 로직
                    break;
                case 4:
                    // 송금 로직
                    break;
                case 5:
                    // 출금 로직
                    break;
                case 6:
                    // 계좌 및 예·적금 조회 로직
                    break;
                case 0:
                    System.out.println("서비스를 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                    break;
            }
        }
    }
}

package transaction;

import database.AccountDao;
import database.DatabaseManager;
import database.DateDao;
import database.UserDao;

import java.io.IOException;
import java.util.Scanner;

public class DepositService {
    private final Scanner scanner;
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final DateDao dateDao;

    public DepositService(UserDao userDao, AccountDao accountDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.scanner = new Scanner(System.in);
        this.dateDao = new DateDao(new DatabaseManager());
        initializeServices();
    }

    private void initializeServices() {
        DatabaseManager dbManager = new DatabaseManager();
        UserDao userDao = new UserDao(dbManager);
    }

    public void showDeposit(String loggedInUserId){
        try{
            System.out.println("\n\n[입금 서비스]");
            System.out.println("============================================");
            System.out.println("('q'를 입력할 시 이전 화면으로 돌아갑니다.)");
            System.out.println("============================================");
            System.out.println("입금할 금액을 입력하세요");

            String money = "";
            String currentDate = dateDao.getDate();

            while (true) {
                System.out.print("입금할 금액: ₩ ");
                money = scanner.nextLine();

                if (money.equals("q")) {
                    return;
                }

                if (!money.matches("\\d+")) {
                    System.out.println("올바른 양식이 아닙니다.");
                    continue;
                }

                try{
                    Long.parseLong(money);
                }catch (NumberFormatException e){
                    System.out.println("최대 입금 가능 범위는 9,223,372,036,854,775,807원 입니다. 다시 입력해주세요.");
                    continue;
                }

                if (Long.parseLong(money)<=0){
                    System.out.println("최소 1원 이상 입력해주세요.");
                    continue;
                }

                long amount = Long.parseLong(money);

                String account = userDao.findUserToAccount(loggedInUserId);
                int returnNum = accountDao.executeTransaction(account, amount, "deposit", currentDate);

                if(returnNum == 0){
                    System.out.println();
                    System.out.println("입금이 완료되었습니다!");
                    System.out.println("현재 잔액: ₩ " + accountDao.showSavings(account));
                    break;
                } else {
                    System.out.println("통장의 최대 금액은 9,223,372,036,854,775,807원까지 가능합니다. 다시 시도해주세요.");
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("입금 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}

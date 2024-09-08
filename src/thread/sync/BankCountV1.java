package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankCountV1 implements BankAccount {

    private int balance;

    public BankCountV1(int initaialBlance) {
        this.balance = initaialBlance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
        // 잔고가 출금액 보다 적으면, 진행하면 안됨
        if (balance < amount) {
            log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
            return false;
        }

        // 잔고가 출금액 보다 많으면, 진행
        log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
        sleep(1000);
        balance -= amount;
        log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);

        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}

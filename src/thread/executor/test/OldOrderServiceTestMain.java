package thread.executor.test;

public class OldOrderServiceTestMain {

    public static void main(String[] args) {
        String orderNo = "order#1234";
        OldOrderService oldOrderService = new OldOrderService();
        oldOrderService.order(orderNo);
    }
}

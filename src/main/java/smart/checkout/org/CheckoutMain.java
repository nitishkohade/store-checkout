package smart.checkout.org;

import java.util.concurrent.FutureTask;

public class CheckoutMain {

	public static void main(String[] args) {
		ProductList.setProducts();
		try {
			ProductList.setProductList("category_A.json");
			ProductList.setProductList("category_B.json");
			ProductList.setProductList("category_C.json");
			
			// put barcode with count, number of items and start the thread
			CustomerOrder order1 = new CustomerOrder();
			order1.setCustomer("Customer1");
			order1.putProduct("doveshampoodsda6rdd678asd7a8di", 4);
			order1.putProduct("chocolatecookiesadadaada67a8di", 5);
			order1.putProduct("copperbottleadadaads8sd7a845gddi", 6);
			FutureTask<Integer> futureTask1 = new FutureTask(order1);
			Thread thread1 = new Thread(futureTask1);
			
			
			// put barcode with count, number of items and start the thread
			CustomerOrder order2 = new CustomerOrder();
			order2.setCustomer("Customer2");
			order2.putProduct("windowcurtainadadasdsdada6rd7a8di", 6);
			order2.putProduct("toiletbrushrdd6ddddadi78sd7a8di", 2);
			order2.putProduct("dummywatchada6rdd6ddd78asdas8d8di", 9);
			FutureTask<Integer> futureTask2 = new FutureTask(order2);
			Thread thread2 = new Thread(futureTask2);
			
			thread1.start();
			thread2.start();
			
			int futureResult1 = futureTask1.get();
			int futureResult2 = futureTask2.get();
			
			if (futureResult1 == 0) {
				System.err.println("Please check your product barcode for order1");
			} else {
				System.out.println("Successfully purchased order1 for customer1");
			}
			if (futureResult2 == 0) {
				System.err.println("Please check your product barcode for order2");
			} else {
				System.out.println("Successfully purchased order2 for customer2");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("There is an error, please check your file name and ");
		}
	}
}

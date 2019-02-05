package smart.checkout.org;

public class CheckoutMain {

	public static void main(String[] args) {
		ProductList.setProducts();
		try {
			ProductList.setProductList("category_A1.json");
			ProductList.setProductList("category_B.json");
			ProductList.setProductList("category_C.json");
			
			// put barcode with count, number of items and start the thread
			CustomerOrder order1 = new CustomerOrder();
			order1.setCustomer("Customer1");
			order1.putProduct("doveshampoodsda6rdd678asd7a8di", 4);
			order1.putProduct("chocolatecookiesadadaada67a8di", 5);
			order1.putProduct("copperbottleadadaads8sd7a845gddi", 6);
			
			
			// put barcode with count, number of items and start the thread
			CustomerOrder order2 = new CustomerOrder();
			order2.setCustomer("Customer2");
			order2.putProduct("windowcurtainadadasdsdada6rd7a8di", 6);
			order2.putProduct("toiletbrushrdd6ddddadi78sd7a8di", 2);
			order2.putProduct("dummywatchada6rdd6ddd78asdas8d8di", 9);
			
			order1.start();
			order2.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("There is an error, please check your file name");
		}
	}
}

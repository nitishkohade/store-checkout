package smart.checkout.org;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class CustomerOrder implements Callable<Integer> {
	
	private Map<String, Integer> orderedProducts;
	private String customer;
	
	public CustomerOrder() {
		orderedProducts = new HashMap<>();
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void putProduct(String code, int count) {
		orderedProducts.put(code, count);
	}

	public Integer call() {
		Checkout checkout = new Checkout();
		List<String> notPresnt = checkout.checkIfBarCodesExists(orderedProducts);
		if (notPresnt.size() == 0) {
			Boolean bool = checkout.purchaseProducts(orderedProducts);
			List<Product> notAvailableProducts = null;
			if (bool) {
				List<FinalProduct> finalProduct = checkout.getTaxableProduct(orderedProducts);
				double total = checkout.getTotalCost(finalProduct);
				checkout.generateInvoice(finalProduct, customer, total);
			} else {
				notAvailableProducts = checkout.productsNotAvailable(orderedProducts);
				notAvailableProducts
				.parallelStream()
				.forEach(p->{
					System.out.println(customer+": " + p.getProduct_name());
				});
			}
		} else {
			return 0;
		}
		return 1;
	}
}

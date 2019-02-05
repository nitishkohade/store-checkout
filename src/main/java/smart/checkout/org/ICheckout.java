package smart.checkout.org;

import java.util.List;
import java.util.Map;

public interface ICheckout {

	void generateInvoice(List<FinalProduct> products, String customer, double totalCost);
	double getTotalCost(List<FinalProduct> products);
	List<FinalProduct> getTaxableProduct(Map<String, Integer> products);
	List<Product> productsNotAvailable(Map<String, Integer> barCodes);
	boolean purchaseProducts(Map<String, Integer> barCodes);
	
}

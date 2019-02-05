package smart.checkout.org;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestProductList {

	Checkout checkout = null;
	@Before
	public void setUp() throws Exception {		
		checkout = new Checkout();
		ProductList.setProducts();
		ProductList.setProductList("category_A.json");	
	}
	
	@Test
	public void testUpdateProductCount() {
		Map<String, Integer> barCodes = new HashMap<>();
		barCodes.put("doveshampoodsda6rdd678asd7a8di", 31);
		barCodes.put("chocolatecookiesadadaada67a8di", 31);
		barCodes.put("copperbottleadadaads8sd7a845gddi", 31);
		boolean bool = ProductList.updateProductCount(barCodes);
		assertEquals(true, bool);
	}
	@Test
	public void testUpdateProductCountIfCountExceeds() {
		Map<String, Integer> barCodes = new HashMap<>();
		barCodes.put("doveshampoodsda6rdd678asd7a8di", 31);
		barCodes.put("chocolatecookiesadadaada67a8di", 71);
		barCodes.put("copperbottleadadaads8sd7a845gddi", 31);
		boolean bool = ProductList.updateProductCount(barCodes);
		assertEquals(false, bool);
	}
}

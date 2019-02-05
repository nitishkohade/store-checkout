package smart.checkout.org;

import static org.junit.Assert.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.itextpdf.text.Document;

public class TestCheckout {

	Checkout checkout = null;
	FinalProduct finalProduct = null;
	JSONParser parser = Mockito.mock(JSONParser.class);
	FileReader reader = Mockito.mock(FileReader.class);
	JSONObject jsonObject = Mockito.mock(JSONObject.class);
	Document document = Mockito.mock(Document.class);
	
	@Before
	public void setUp() throws Exception {
		checkout = new Checkout();
		finalProduct = new FinalProduct();
		finalProduct.setTotalTaxablePrice(10);
		ProductList.setProducts();
		ProductList.setProductList("category_A.json");
	}
	
//	@Test
//	public void testSetProductListForEmptyFile() {
//		checkout.setProductList("");		
//		assertEquals("empty file returning zero", 0, ProductList.getProducts().size());
//	}
	
	@Test
	public void testSetProductListForFile() {
		assertEquals("File having size 5", 5, ProductList.getProducts().size());
	}
	
	@Test
	public void testGetTotalCostIfEmpty() {
		List<FinalProduct> products = new ArrayList<>();
		
		double d = checkout.getTotalCost(products);
		assertEquals(0.0, d, 0);
	}
	
	@Test
	public void testGetTotalCostIfNotEmpty() {
		List<FinalProduct> products = new ArrayList<>();
		products.add(finalProduct);
		double d = checkout.getTotalCost(products);
		assertEquals(10.0, d, 0);
	}
	
	@Test
	public void testGetTaxValueForCategoryA() {
		int d = checkout.getTaxValue("A");
		assertEquals("get tax value for A category", 10, d);
	}
	@Test
	public void testGetTaxValueForCategoryB() {
		int d = checkout.getTaxValue("B");
		assertEquals("get tax value for B category", 20, d);
	}
	@Test
	public void testGetTaxValueForCategoryC() {
		int d = checkout.getTaxValue("C");
		assertEquals("get tax value for C category", 0, d);
	}
	@Test
	public void testGetDefaultTaxValue() {
		int d = checkout.getTaxValue("");
		assertEquals("get tax value for default category", 0, d);
	}
	
	@Test
	public void testProductsNotAvailableIfNoBarCodes() {
		Map<String, Integer> barCodes = new HashMap<>();
		List<Product> list = checkout.productsNotAvailable(barCodes);		
		assertEquals("returning zero if no product selected", 0, list.size());
	}
	@Test
	public void testIfBarCodesCountExceeds() {
		Map<String, Integer> barCodes = new HashMap<>();
		barCodes.put("doveshampoodsda6rdd678asd7a8di", 51);
		List<Product> list = checkout.productsNotAvailable(barCodes);		
		assertEquals("If selected product having count more than the limit", 1, list.size());
	}
	@Test
	public void testIfBarCodesAreLimited() {
		Map<String, Integer> barCodes = new HashMap<>();
		barCodes.put("doveshampoodsda6rdd678asd7a8di", 31);
		List<Product> list = checkout.productsNotAvailable(barCodes);		
		assertEquals("returning zero if no product purchased", 0, list.size());
	}
	@Test
	public void testGetTaxableProduct() {
		Map<String, Integer> barCodes = new HashMap<>();
		barCodes.put("doveshampoodsda6rdd678asd7a8di", 31);
		barCodes.put("chocolatecookiesadadaada67a8di", 31);
		barCodes.put("copperbottleadadaads8sd7a845gddi", 31);
		List<FinalProduct> list = checkout.getTaxableProduct(barCodes);		
		assertEquals("There are 3 taxable product", 3, list.size());
	}
	@Test
	public void testGenerateInvoiceBlank() {
		Map<String, Integer> barCodes = new HashMap<>();
		List<FinalProduct> list = new ArrayList<>();
		list.add(finalProduct);
		checkout.generateInvoice(list, "testFile", 10);		
	}
	
}

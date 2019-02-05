package smart.checkout.org;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProductList {

	private static Map<String, Product> products;

	public static void setProducts() {
		products = new ConcurrentHashMap<String, Product>();
	}
	
	public static void putProducts(Product p) {
		products.put(p.getProduct_bar_code(), p);
	}
	public static Map<String, Product> getProducts() {
		return products;
	}
	public static boolean updateProductCount(Map<String, Integer> codes) {
		final Map<String, Integer> barCodes = codes
				.entrySet()
				.stream()
			    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		
		final boolean[] bool = new boolean[1];
		bool[0] = true;
		
		Iterator<String> itr = barCodes.keySet().iterator();
		
		while(itr.hasNext()) {
			String code = itr.next();
			long count = barCodes.get(code);
			if(products.get(code).getCount()>=count) {
				products.computeIfPresent(code, (key, value) -> {
					if(value.getCount()>=count) {
						value.setCount(value.getCount() - count);
					}else {
						barCodes.put(code, 0);
						bool[0] = false;
					}
					return value;
				});
			} else {
				bool[0] = false;
				barCodes.put(code, 0);
			}
		}
		//revert the count if failed
		itr = barCodes.keySet().iterator();
		while(!bool[0] && itr.hasNext()) {
			String code = itr.next();
			long count = barCodes.get(code);
			if(count > 0) {
				products.computeIfPresent(code, (key, value) -> {
					value.setCount(value.getCount() + count);
					return value;
				});
			} 
		}
		return bool[0];
	}
	// fetch all JSON data from json file into a map
	public static void setProductList(String fileName) throws Exception{
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(fileName));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray msg = (JSONArray) jsonObject.get("products");
			Iterator<JSONObject> iterator = msg.iterator();
			while (iterator.hasNext()) {
				Product product = new Product();
				jsonObject = (JSONObject)iterator.next();
				String name = (String) jsonObject.get("product_name");
				product.setProduct_name(name);
				String id = (String) jsonObject.get("product_id");
				product.setProduct_id(id);
				String category = (String) jsonObject.get("product_category");
				product.setProduct_category(category);
				String barcode = (String) jsonObject.get("product_bar_code");
				product.setProduct_bar_code(barcode);
				double price = (Double) jsonObject.get("product_price");
				product.setProduct_price(price);
				long count = (Long) jsonObject.get("product_count");
				product.setCount(count);

				ProductList.putProducts(product);
			}

		} catch (FileNotFoundException e) {
			throw new Exception("Check your file name");
		} catch (IOException | ParseException e) {
			// for logger
			e.printStackTrace();
		}
	}
}

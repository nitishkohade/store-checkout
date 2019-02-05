package smart.checkout.org;

import java.util.HashMap;
import java.util.Map;

public class Product {

	private String product_name;
	private String product_id;
	private String product_category;
	private String product_bar_code;
	private double product_price;
	private long count;
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public String getProduct_bar_code() {
		return product_bar_code;
	}
	public void setProduct_bar_code(String product_bar_code) {
		this.product_bar_code = product_bar_code;
	}
	public double getProduct_price() {
		return product_price;
	}
	public void setProduct_price(double product_price) {
		this.product_price = product_price;
	}
}

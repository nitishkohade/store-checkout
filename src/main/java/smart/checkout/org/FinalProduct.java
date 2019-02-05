package smart.checkout.org;

public class FinalProduct extends Product {

	private int tax;
	private double totalTaxablePrice;

	public double getTotalTaxablePrice() {
		return totalTaxablePrice;
	}

	public void setTotalTaxablePrice(double totalTaxablePrice) {
		this.totalTaxablePrice = totalTaxablePrice;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}
	
}

package smart.checkout.org;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

public class Checkout implements ICheckout{
	private boolean isPurchased;
	
	// Return true or false based on the successful entries in the table
	public boolean purchaseProducts(Map<String, Integer> barCodes) {
		isPurchased = ProductList.updateProductCount(barCodes);
		return isPurchased;
	}
	// Fetches what are all products not available if number of items exceeds
	public List<Product> productsNotAvailable(Map<String, Integer> barCodes) {
		List<Product> products = new ArrayList<>();
		// filtered stream of bar code which are not available as per the required quantity
		Stream<String> filteredStream= barCodes
				.keySet()
				.stream()
				.filter((code)->{
					Product product = (Product) ProductList.getProducts().get(code);
					if(product.getCount()<barCodes.get(code)) {
						return true;
					}
					return false;
				});

		// Getting not available products
		filteredStream.parallel().forEach(code -> {
			products.add(ProductList.getProducts().get(code));
		});

		return products;
	}

	public static int getTaxValue(String category) {
		switch(category) {
		case "A":
			return 10;
		case "B":
			return 20;
		case "C":
			return 0;
		default:
			return 0;
		}
	}
	// get computed list of products along with tax
	public List<FinalProduct> getTaxableProduct(Map<String, Integer> products){
		List<FinalProduct> items = new ArrayList<>();
		products.keySet()
		.stream()
		.forEach(code -> {
			FinalProduct p = new FinalProduct();
			Product product = ProductList.getProducts().get(code);
			int tax = getTaxValue(product.getProduct_category());
			p.setCount(products.get(code));
			p.setProduct_name(product.getProduct_name());
			p.setProduct_price(product.getProduct_price());
			p.setProduct_id(product.getProduct_id());
			p.setProduct_category(product.getProduct_category());
			p.setProduct_bar_code(product.getProduct_bar_code());
			p.setTax(tax);
			p.setTotalTaxablePrice(products.get(code)*(product.getProduct_price() + (product.getProduct_price()*tax/100)));
			items.add(p);			
		});
		return items;
	}
	// To get total cost of all the items if purchased
	public double getTotalCost(List<FinalProduct> products) {
		final double[] total = new double[1];
		total[0] = 0;
		products
		.stream()
		.forEach(product -> {
			total[0] = total[0] + product.getTotalTaxablePrice();
		});		
		return total[0];
	}
	// Generates invoice on PDF
	public void generateInvoice(List<FinalProduct> products, String customer, double totalCost) {

		Font blueFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
		Font redFont = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(0, 255, 0, 0));
		Font yellowFont = FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 0, 255, 0));
		Document document = new Document();
		try
		{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(customer+".pdf"));
			document.open();

			//Create chapter and sections
			Paragraph chapterTitle = new Paragraph("Your Billing Info:", yellowFont);
			Chapter chapter1 = new Chapter(chapterTitle, 1);
			chapter1.setNumberDepth(0);

			products
			.stream()
			.forEach(product -> {
				Paragraph sectionTitle = new Paragraph("Product Name: "+product.getProduct_name(), redFont);
				Section section1 = chapter1.addSection(sectionTitle);

				Paragraph sectionContent = new Paragraph("Total Items: "+product.getCount(), blueFont);
				Paragraph sectionContent1 = new Paragraph("Product Price: "+product.getProduct_price(), blueFont);
				Paragraph sectionContent2 = new Paragraph("tax Amount: "+product.getTax(), blueFont);
				Paragraph sectionContent3 = new Paragraph("Total Taxable Price: "+product.getTotalTaxablePrice(), blueFont);
				section1.add(sectionContent);
				section1.add(sectionContent1);
				section1.add(sectionContent2);
				section1.add(sectionContent3);
			});	
			
			Paragraph sectionTitle = new Paragraph("", redFont);
			Section section1 = chapter1.addSection(sectionTitle);
			Paragraph sectionContent = new Paragraph("Total Price: "+totalCost, blueFont);
			section1.add(sectionContent);
			document.add(chapter1);

			document.close();
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public List<String> checkIfBarCodesExists(Map<String, Integer> orderedProducts) {
		List<String> notPresent = new ArrayList<>();		
		orderedProducts.keySet()
		.stream()
		.forEach(code -> {
			if(!ProductList.getProducts().containsKey(code)) {
				notPresent.add(code);
			}
		});		
		return notPresent;
	}	
}

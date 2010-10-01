package com.manning.sbia.ch06.service;

public class ProductServiceImpl {

	private Product createProduct(String id, String name, String description, float price) {
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		return product;
	}

	public List<Product> getProducts() {
		List<Product> products = new ArrayList<Product>();
		products.add(createProduct("PR....210", "BlackBerry 8100 Pearl", "", 124.60));
		products.add(createProduct("PR....211", "Sony Ericsson W810i", "", 139.45));
		products.add(createProduct("PR....212", "Samsung MM-A900M Ace", "", 97.80));
		products.add(createProduct("PR....213", "Toshiba M285-E 14", "", 166.20));
		products.add(createProduct("PR....214", "Nokia 2610 Phone", "", 145.50));
		products.add(createProduct("PR....215", "CN Clogs Beach/Garden Clog", "", 190.70));
		products.add(createProduct("PR....216", "AT&T 8525 PDA", "", 289.20));
		products.add(createProduct("PR....217", "Canon Digital Rebel XT 8MP Digital SLR Camera", "", 13.70));
		return products;
	}
}

package application;

public class Company {

	private String name;
	private String email;
	private String phone;
	private String fax;
	private String logo;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String slogan;

	public Company(String name, String email, String phone, String fax, String logo, String street, String city,
			String state, String zip, String slogan) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.fax = fax;
		this.logo = logo;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.slogan = slogan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

}

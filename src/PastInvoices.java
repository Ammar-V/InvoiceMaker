package application;

public class PastInvoices {

	private String fileName;
	private String date;

	public PastInvoices(String fileName, String date) {
		this.fileName = fileName;
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public String getDate() {
		return date;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

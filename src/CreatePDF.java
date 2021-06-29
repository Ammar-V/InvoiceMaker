package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.ObservableList;

public class CreatePDF {

	public static int penalty = 0;
	public static String inputInvoiceNum = "";
	Company company = EditInfo.company;
	String logo = EditInfo.companyLogo;
	Company broker;
	Company shipper;
	Company receiver;

	public void create(String contact, String selectedShipper, String selectedReceiver, LocalDate today, LocalDate due,
			ObservableList<InvoiceItem> data, String pdfName, String bolNumber, String carrierPro, String brokerPro,
			String shippedVia, int payTerms) {

		Document document = new Document();

		String todayDate = today.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		String dueDate = due.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

		// Get which company the invoice is being sent to
		for (Company comp : AddContact.contacts) {
			if (comp.getName().equals(contact)) {
				broker = comp;
				break;
			}
		}

		// Get the shipper
		for (Company comp : AddShipper.shippers) {
			if (comp.getName().equals(selectedShipper)) {
				shipper = comp;
				break;
			}
		}

		// Get the shipper
		for (Company comp : AddReceiver.receivers) {
			if (comp.getName().equals(selectedReceiver)) {
				receiver = comp;
				break;
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(pdfName);
			PdfWriter.getInstance(document, out);

			// Add invoice to database
			String invoiceName = new File(pdfName).getName();

			FileOutputStream copyOut = new FileOutputStream("invoices/" + invoiceName);
			PdfWriter.getInstance(document, copyOut);

			DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String now = format.format(LocalDateTime.now());
			ViewInvoices.invoices.add(new PastInvoices(invoiceName, now));

			document.open();

			PdfPTable sloganTable = new PdfPTable(3);
			sloganTable.setWidthPercentage(100);
			sloganTable.setWidths(new int[] { 1, 1, 1 });

			Paragraph slogan = new Paragraph(company.getSlogan(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9));
			slogan.setAlignment(Paragraph.ALIGN_CENTER);
			PdfPCell sloganCell = new PdfPCell(slogan);
			sloganCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			sloganCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			sloganCell.setBorder(PdfPCell.NO_BORDER);
			sloganCell.setPadding(-10);
			sloganTable.addCell(sloganCell);

			PdfPCell empty = new PdfPCell();
			empty.setBorder(PdfPCell.NO_BORDER);
			sloganTable.addCell(empty);

			PdfPCell invoiceCell = new PdfPCell(
					new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22)));
			invoiceCell.setPaddingTop(-20);
			invoiceCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			invoiceCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			invoiceCell.setBorder(PdfPCell.NO_BORDER);
			sloganTable.addCell(invoiceCell);

			document.add(sloganTable);

			PdfPTable headerTable = new PdfPTable(3);
			headerTable.setWidthPercentage(100);
			headerTable.setWidths(new int[] { 1, 1, 1 });

			// Add Logo to PDF
			String logoPath = new File(logo).getAbsolutePath();
			Image img = Image.getInstance("file:////" + logoPath);
			// Scale the image if needed
			if (img.getScaledHeight() > 128) {
				img.scaleToFit(150, 128);
			}

			PdfPCell imgCell = new PdfPCell(img);
			imgCell.setPadding(0);
			imgCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			imgCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			imgCell.setBorder(PdfPCell.NO_BORDER);
			headerTable.addCell(imgCell);

			// Information about who the Invoice is from
			String fromText = "\n" + company.getStreet() + "\n" + company.getCity() + ", " + company.getState() + ", "
					+ company.getZip() + "\n" + company.getPhone() + ", " + company.getFax() + "\n" + company.getEmail()
					+ "\n";

			Paragraph fromParagraph = new Paragraph(fromText, FontFactory.getFont(FontFactory.HELVETICA, 10));
			fromParagraph.setIndentationLeft(10);
			Paragraph from = new Paragraph(company.getName(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
			from.add(fromParagraph);

			PdfPCell fromCell = new PdfPCell(from);
			fromCell.setPadding(0);
			fromCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			fromCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			fromCell.setBorder(PdfPCell.NO_BORDER);
			headerTable.addCell(fromCell);

			headerTable.addCell(empty);

			document.add(headerTable);

			// Information about who the Invoice is going to

			Paragraph to = new Paragraph("Bill To:    ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
			PdfPCell billTo = new PdfPCell(to);
			billTo.setPaddingRight(10);
			billTo.setPaddingTop(0);
			billTo.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			billTo.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			billTo.setBorder(PdfPCell.NO_BORDER);

			String toText = broker.getName() + "\n" + broker.getStreet() + "\n" + broker.getCity() + ", "
					+ broker.getState() + ", " + broker.getZip() + "\n" + broker.getPhone();
			if (!broker.getFax().equals(""))
				toText += ", " + broker.getFax();
			if (!broker.getEmail().equals(""))
				toText += "\n" + broker.getEmail();
			Paragraph toParagraph = new Paragraph(toText, FontFactory.getFont(FontFactory.HELVETICA, 10));

			// Invoice number info
			PdfPTable invoiceInfo = new PdfPTable(2);
			invoiceInfo.addCell(
					getCell(new Paragraph("Invoice No:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10))));
			invoiceInfo.addCell(getCell(
					new Paragraph(getInvoiceNum(inputInvoiceNum), FontFactory.getFont(FontFactory.HELVETICA, 10))));

			invoiceInfo.addCell(getCell(new Paragraph("Date:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10))));
			invoiceInfo.addCell(getCell(new Paragraph(todayDate, FontFactory.getFont(FontFactory.HELVETICA, 10))));

			invoiceInfo
					.addCell(getCell(new Paragraph("Due Date:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10))));
			invoiceInfo.addCell(getCell(new Paragraph(dueDate, FontFactory.getFont(FontFactory.HELVETICA, 10))));

			PdfPCell invoiceInfoCell = new PdfPCell(invoiceInfo);
			invoiceInfoCell.setBorder(PdfPCell.NO_BORDER);
			invoiceInfoCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			invoiceInfoCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

			// Actual table with TO and INVOICE NUMBER
			PdfPTable infoTable = new PdfPTable(3);
			infoTable.setWidthPercentage(100);
			infoTable.setSpacingBefore(15);
			infoTable.addCell(billTo);
			infoTable.addCell(getCell(toParagraph));
			infoTable.addCell(invoiceInfoCell);
			infoTable.setWidths(new int[] { 1, 1, 1 });

			document.add(infoTable);

			/*
			 * 
			 * LOGISTICS
			 * 
			 */

			PdfPTable logTable = new PdfPTable(5);
			logTable.setSpacingBefore(15);
			logTable.setWidthPercentage(100);
			logTable.setWidths(new int[] { 1, 1, 1, 1, 1 });
			addTableHeader(logTable,
					new String[] { "BOL Number", "Carrier Pronumber", "Broker Pronumber", "Shipped Via", "Pay Terms" });

			String[] logItems = new String[] { bolNumber, carrierPro, brokerPro, shippedVia,
					Integer.toString(payTerms) };

			for (String item : logItems) {
				Paragraph str = new Paragraph(item, FontFactory.getFont(FontFactory.HELVETICA, 10));
				PdfPCell logCell = new PdfPCell(str);
				logCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				logCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				logTable.addCell(logCell);
			}
			document.add(logTable);

			/*
			 * 
			 * SHIPPER AND RECEIVER
			 * 
			 */

			PdfPTable srTable = new PdfPTable(2);
			srTable.setWidthPercentage(100);
			srTable.setWidths(new int[] { 1, 1 });
			srTable.setSpacingBefore(15);

			String shipperText = shipper.getName() + "\n   " + shipper.getStreet() + "\n   " + shipper.getCity() + ", "
					+ shipper.getState() + ", " + shipper.getZip() + "\n   " + shipper.getPhone();
			if (!shipper.getFax().equals(""))
				shipperText += ", " + shipper.getFax();
			if (!shipper.getEmail().equals(""))
				shipperText += "\n   " + shipper.getEmail();
			Paragraph shipperPar = new Paragraph(shipperText, FontFactory.getFont(FontFactory.HELVETICA, 10));
			Paragraph shipperBold = new Paragraph("Shipper:\n   ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
			shipperBold.add(shipperPar);
			srTable.addCell(getCell(shipperBold));

			String receiverText = receiver.getName() + "\n   " + receiver.getStreet() + "\n   " + receiver.getCity()
					+ ", " + receiver.getState() + ", " + receiver.getZip() + "\n   " + receiver.getPhone();
			if (!receiver.getFax().equals(""))
				receiverText += ", " + receiver.getFax();
			if (!receiver.getEmail().equals(""))
				receiverText += "\n   " + receiver.getEmail();
			Paragraph receiverPar = new Paragraph(receiverText, FontFactory.getFont(FontFactory.HELVETICA, 10));
			Paragraph receiverBold = new Paragraph("Receiver:\n   ",
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
			receiverBold.add(receiverPar);
			srTable.addCell(getCell(receiverBold));

			document.add(srTable);

			// Invoice Items table

			PdfPTable itemTable = new PdfPTable(2);
			itemTable.setWidthPercentage(100);
			itemTable.setSpacingBefore(15);
			itemTable.setWidths(new int[] { 4, 1 });
			addTableHeader(itemTable, new String[] { "Description", "Price ($)" });

			// This variable will help keep track of alternating color;
			boolean color = true;

			double totalPrice = 0;

			// Add Items
			for (InvoiceItem item : data) {
				// Add description
				String description = item.getDescription().replaceAll("\n", "");
				PdfPCell desCell = new PdfPCell(
						new Paragraph(description, FontFactory.getFont(FontFactory.HELVETICA, 10)));
				desCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				desCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				desCell.setBorder(PdfPCell.NO_BORDER);

				// Add price
				double price = item.getPrice();
				totalPrice += price;
				PdfPCell priCell = new PdfPCell(
						new Paragraph("$" + Double.toString(price), FontFactory.getFont(FontFactory.HELVETICA, 10)));
				priCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				priCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				priCell.setBorder(PdfPCell.NO_BORDER);

				if (color) {
					desCell.setBackgroundColor(new BaseColor(217, 223, 225));
					priCell.setBackgroundColor(new BaseColor(217, 223, 225));
					color = false;
				} else {
					color = true;
				}

				itemTable.addCell(desCell);
				itemTable.addCell(priCell);
			}

			// A break in the table
			PdfPCell breakCell = new PdfPCell(new Paragraph(" "));
			breakCell.setBorder(PdfPCell.NO_BORDER);
			itemTable.addCell(breakCell);
			itemTable.addCell(breakCell);

			// Total
			PdfPCell totalDes = new PdfPCell(
					new Paragraph("Total Price", FontFactory.getFont(FontFactory.HELVETICA, 10)));
			totalDes.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			totalDes.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

			PdfPCell priceDes = new PdfPCell(
					new Paragraph("$" + Double.toString(totalPrice), FontFactory.getFont(FontFactory.HELVETICA, 10)));
			priceDes.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			priceDes.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

			if (color) {
				totalDes.setBackgroundColor(new BaseColor(217, 223, 225));
				priceDes.setBackgroundColor(new BaseColor(217, 223, 225));
				color = false;
			} else {
				color = true;
			}
			itemTable.addCell(totalDes);
			itemTable.addCell(priceDes);

			// Add another break in the cell
			itemTable.addCell(breakCell);
			itemTable.addCell(breakCell);

			// Add Penalty
			if (penalty > 0) {
				PdfPCell penaltyDes = new PdfPCell(new Paragraph(
						"Your current charges are due on " + dueDate + ". A " + Integer.toString(penalty)
								+ "% late payment will be applied after the due date.",
						FontFactory.getFont(FontFactory.HELVETICA, 9)));
				penaltyDes.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				penaltyDes.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				penaltyDes.setBorderWidth((float) 1.5);
				itemTable.addCell(penaltyDes);

				double penaltyTotal = totalPrice + (totalPrice * (penalty / 100.0));

				PdfPCell penaltyPrice = new PdfPCell(new Paragraph("$" + Double.toString(penaltyTotal),
						FontFactory.getFont(FontFactory.HELVETICA, 10)));
				penaltyPrice.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				penaltyPrice.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				penaltyPrice.setBorderWidth((float) 1.5);
				itemTable.addCell(penaltyPrice);
			}
			document.add(itemTable);

			// Last bit of info
			Paragraph lastInfo = new Paragraph(
					"Make all check payable to " + company.getName() + ". \nTHANK YOU FOR YOUR BUSINESS!",
					FontFactory.getFont(FontFactory.HELVETICA, 10));
			lastInfo.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(lastInfo);

			document.close();
			out.close();
			copyOut.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PdfPCell getCell(Paragraph text) {
		PdfPCell cell = new PdfPCell(text);
		cell.setPadding(0);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	private void addTableHeader(PdfPTable table, String[] titles) {
		for (String columnTitle : titles) {
			PdfPCell header = new PdfPCell();
			header.setBorderWidth(2);
			header.setPhrase(new Paragraph(columnTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
			if (columnTitle.equals("Description")) {
				header.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			} else {
				header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			}
			header.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			table.addCell(header);
		}
	}

	// Generate Invoice Number
	public String getInvoiceNum(String inputNum) {
		if (!inputNum.equals("")) {
			// Clear the inputed value so it is not used when automatically generating a
			// number.
			inputInvoiceNum = "";
			return inputNum;
		}
		Random r = new Random();
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		while (true) {
			String invoice = "";
			for (int j = 0; j < 6; j++) {
				invoice += chars.charAt(r.nextInt(chars.length()));
			}

			// Check it hasn't been used before
			try {
				File file = new File("invoiceNums.txt");
				if (!file.exists())
					file.createNewFile();
				Scanner scan = new Scanner(file);
				ArrayList<String> previousNums = new ArrayList<>();
				while (scan.hasNext()) {
					previousNums.add(scan.next());
				}
				scan.close();
				if (!previousNums.contains(invoice)) {
					FileWriter write = new FileWriter(file, true);
					write.append(invoice);
					write.close();
					return invoice;
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

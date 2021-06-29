package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ManageData {

	public void upload(Company company) {

		// Remove previous logo
		try {
			File previousImage = new File(EditInfo.previousLogo);
			Path path = previousImage.toPath();
			Files.delete(path);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			objectMapper.writeValue(new File("business.json"), company);
			System.out.println("Saveeed....");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Save the image
		BufferedImage bImage = SwingFXUtils.fromFXImage(EditInfo.logo, null);

		// Get image file type
		String[] filetype = company.getLogo().split("\\.");

		try {
			ImageIO.write(bImage, filetype[filetype.length - 1], new File(company.getLogo()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void load() {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Load current company info from JSON
			File file = new File("business.json");
			// convert json to string
			String json = new String(Files.readAllBytes(file.toPath()));
			System.out.println(json);
			// convert json object into Company object
			JsonNode j = objectMapper.readTree(json);
			Company company = new Company(j.get("name").asText(), j.get("email").asText(), j.get("phone").asText(),
					j.get("fax").asText(), j.get("logo").asText(), j.get("street").asText(), j.get("city").asText(),
					j.get("state").asText(), j.get("zip").asText(), j.get("slogan").asText());
			EditInfo.company = company;
			/*
			 * Set the current image name for the save button to access when creating a new
			 * Company object. When the program is loaded, the Edit Info tab is populated.
			 * If someone just wants to change, for example, just the slogan, they will get
			 * an error because they did not select a logo file name. However, the logo is
			 * present. Therefore, company.getLogo() is assigned to companyLogo. Now when
			 * pressing save, the program will know that a logo is already present and
			 * saved.
			 */
			EditInfo.companyLogo = company.getLogo();

			// Load image
			FileInputStream input = new FileInputStream(company.getLogo());
			Image image = new Image(input);

			// Clos the input stream to allow this logo to be deleted in the future
			input.close();
			EditInfo.logo = image;

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadContacts() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			File folder = new File("contacts");

			// Get all the files in the contacts folder
			File[] files = folder.listFiles();

			for (File file : files) {
				String json = new String(Files.readAllBytes(file.toPath()));

				JsonNode j = objectMapper.readTree(json);
				Company company = new Company(j.get("name").asText(), j.get("email").asText(), j.get("phone").asText(),
						j.get("fax").asText(), null, j.get("street").asText(), j.get("city").asText(),
						j.get("state").asText(), j.get("zip").asText(), null);
				AddContact.contacts.add(company);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveContact() {

		try {

			File folder = new File("contacts");
			folder.mkdir();

			ObjectMapper objectMapper = new ObjectMapper();

			Company company = AddContact.contacts.get(AddContact.contacts.size() - 1);

			objectMapper.writeValue(new File("contacts/" + company.getName() + ".json"), company);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteContact(Company company) {
		try {

			File file = new File("contacts/" + company.getName() + ".json");

			Files.delete(file.toPath());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadShippers() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			File folder = new File("shippers");

			// Get all the files in the shippers folder
			File[] files = folder.listFiles();

			for (File file : files) {
				String json = new String(Files.readAllBytes(file.toPath()));

				JsonNode j = objectMapper.readTree(json);
				Company company = new Company(j.get("name").asText(), j.get("email").asText(), j.get("phone").asText(),
						j.get("fax").asText(), null, j.get("street").asText(), j.get("city").asText(),
						j.get("state").asText(), j.get("zip").asText(), null);
				AddShipper.shippers.add(company);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveShipper() {

		try {

			File folder = new File("shippers");
			// Create folder if this is the first time a shipper is added
			folder.mkdir();

			ObjectMapper objectMapper = new ObjectMapper();

			Company company = AddShipper.shippers.get(AddShipper.shippers.size() - 1);

			objectMapper.writeValue(new File("shippers/" + company.getName() + ".json"), company);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteShipper(Company company) {
		try {

			File file = new File("shippers/" + company.getName() + ".json");

			Files.delete(file.toPath());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadReceivers() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			File folder = new File("receivers");

			// Get all the files in the shippers folder
			File[] files = folder.listFiles();

			for (File file : files) {
				String json = new String(Files.readAllBytes(file.toPath()));

				JsonNode j = objectMapper.readTree(json);
				Company company = new Company(j.get("name").asText(), j.get("email").asText(), j.get("phone").asText(),
						j.get("fax").asText(), null, j.get("street").asText(), j.get("city").asText(),
						j.get("state").asText(), j.get("zip").asText(), null);
				AddReceiver.receivers.add(company);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveReceiver() {

		try {

			File folder = new File("receivers");
			// Create folder if this is the first time a receiver is added
			folder.mkdir();

			ObjectMapper objectMapper = new ObjectMapper();

			Company company = AddReceiver.receivers.get(AddReceiver.receivers.size() - 1);

			objectMapper.writeValue(new File("receivers/" + company.getName() + ".json"), company);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteReceiver(Company company) {
		try {

			File file = new File("receivers/" + company.getName() + ".json");

			Files.delete(file.toPath());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadInvoices() {

		try {
			File dir = new File("invoices");
			dir.mkdir();

			File[] invoices = dir.listFiles();

			for (File file : invoices) {
				FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				String date = format.format(creationTime.toMillis());

				ViewInvoices.invoices.add(new PastInvoices(file.getName(), date));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

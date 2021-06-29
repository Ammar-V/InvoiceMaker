package application;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CheckImgSize {

	String filetype = "";

	public boolean check() {
		String logoPath = new File(EditInfo.companyLogo).getAbsolutePath();
		try {
			BufferedImage img = ImageIO.read(new File(logoPath));
			if (img.getHeight() > 128) {
				// Percent change when scaling height to 128
				double percent = 128.0 / img.getHeight();
				Image reLogo;
				if (percent * img.getWidth() > 150) {
					double percentWidth = 150.0 / img.getWidth();
					reLogo = img.getScaledInstance(150, (int) (img.getHeight() * percentWidth), Image.SCALE_DEFAULT);
				} else {
					reLogo = img.getScaledInstance((int) (img.getWidth() * percent), 128, Image.SCALE_DEFAULT);
				}
				// The resized image
				BufferedImage resized = new BufferedImage(reLogo.getWidth(null), reLogo.getHeight(null),
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D bGr = resized.createGraphics();
				bGr.drawImage(reLogo, 0, 0, null);
				bGr.dispose();

				// Store the resized image in a temp file.
				String[] filetype = EditInfo.company.getLogo().split("\\.");
				this.filetype = filetype[filetype.length - 1];

				ImageIO.write(resized, this.filetype, new File("resized." + this.filetype));
				return false;
			} else {
				return true;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

}

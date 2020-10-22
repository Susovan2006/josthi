package com.josthi.web.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @author Susovan Gumtya
 *
 */
public class ResizeImage {

	private static final Logger logger = LoggerFactory.getLogger(ResizeImage.class);
	
	public static String resizeProfileImage(String imagePath) throws Exception {
		
		try {
			
			BufferedImage originalImage = ImageIO.read(new File(imagePath));
			int width          = originalImage.getWidth();
			int height         = originalImage.getHeight();
			logger.info("WxH = "+width +" X "+height);
			if(width >= height) {
				int reductionRatio = width / 200;
				logger.info("Land scape :"+reductionRatio);
				
				width = width / reductionRatio;
				height = height / reductionRatio;			
				logger.info("New Resolution : "+width +" X "+height);
				
			}else if(width < height) {
				int reductionRatio = height / 200;
				logger.info("Potrate scape :"+ reductionRatio);
				
				width = width / reductionRatio;
				height = height / reductionRatio;
				
				logger.info("New Resolution : "+width +" X "+height);
			}
			
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

			BufferedImage resizeImageJpg = resizeImage(originalImage, type, height, width);
			ImageIO.write(resizeImageJpg, "jpg", new File(imagePath));
			
			return "Image resized successfully!!";
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
	}
	
	
	
	
	
	
	private static final int IMG_WIDTH = 100;
	private static final int IMG_HEIGHT = 100;

	public static void main(String [] args){

	try{

		BufferedImage originalImage = ImageIO.read(new File("C:\\josthi\\file\\RU200921008\\IMG_5320_2XXX.jpg"));
		int width          = originalImage.getWidth();
		int height         = originalImage.getHeight();
		System.out.println(width +" X "+height);
		if(width >= height) {
			int reductionRatio = width / 200;
			System.out.println("Land scape :"+reductionRatio);
			
			width = width / reductionRatio;
			height = height / reductionRatio;			
			System.out.println("New Resolution : "+width +" X "+height);
			
		}else if(width < height) {
			int reductionRatio = height / 200;
			System.out.println("Potrate scape :"+ reductionRatio);
			
			width = width / reductionRatio;
			height = height / reductionRatio;
			
			System.out.println("New Resolution : "+width +" X "+height);
		}
		
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		BufferedImage resizeImageJpg = resizeImage(originalImage, type, height, width);
		ImageIO.write(resizeImageJpg, "jpg", new File("C:\\josthi\\file\\RU200921008\\IMG_5320_2YYY.jpg"));

		//BufferedImage resizeImagePng = resizeImage(originalImage, type);
		//ImageIO.write(resizeImagePng, "png", new File("c:\\image\\xxxx.jpg"));

		//BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
		//ImageIO.write(resizeImageHintJpg, "jpg", new File("c:\\image\\xxxx.jpg"));

		//BufferedImage resizeImageHintPng = resizeImageWithHint(originalImage, type);
		//ImageIO.write(resizeImageHintPng, "png", new File("c:\\image\\xxxx.jpg"));

	}catch(IOException e){
		System.out.println(e.getMessage());
	}

    }

	/*
	 * private static BufferedImage resizeImage(BufferedImage originalImage, int
	 * type){ BufferedImage resizedImage = new
	 * BufferedImage(IMG_WIDTH, IMG_HEIGHT, type); Graphics2D g =
	 * resizedImage.createGraphics(); g.drawImage(originalImage, 0, 0, IMG_WIDTH,
	 * IMG_HEIGHT, null); g.dispose();
	 * 
	 * return resizedImage; }
	 */
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int height, int width){
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
	
		return resizedImage;
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);
	
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	
		return resizedImage;
    }
}

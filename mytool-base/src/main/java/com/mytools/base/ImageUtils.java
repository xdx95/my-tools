package com.mytools.base;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 图片工具类
 *
 * @author xdx
 */
public class ImageUtils {

	// 修改图片颜色的方法
	public static BufferedImage changeColor(BufferedImage image, Color newColor) {
		int width = image.getWidth();
		int height = image.getHeight();

		// 创建一个新的图像来存储变色后的结果
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// 遍历每个像素并替换颜色
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgba = image.getRGB(x, y);
				Color originalColor = new Color(rgba, true);

				// 混合颜色
				int newRed = (newColor.getRed() * originalColor.getRed()) / 255;
				int newGreen = (newColor.getGreen() * originalColor.getGreen()) / 255;
				int newBlue = (newColor.getBlue() * originalColor.getBlue()) / 255;

				Color newPixelColor = new Color(newRed, newGreen, newBlue, originalColor.getAlpha());
				result.setRGB(x, y, newPixelColor.getRGB());
			}
		}
		return result;
	}

}

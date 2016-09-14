package com.yealink.uc.web.modules.capcha.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/**
 * 内部类<br/>
 * 生成验证码图片相关属性.
 */
 public class CaptchaProperty {

	private static final int DEFAULT_HEIGHT = 32;

	/**
	 * 常量，默认缩放最小系数.
	 */
	private static final float DEFAULT_MIN_SCALE_SIZE = 0.8f;

	/**
	 * 常量，默认旋转弧度值.
	 */
	private static final double DEFAULT_THETA = (45) * 3.14 / 180;

	/**
	 * 常量，默认干扰线条数。
	 */
	private static final int DEFAULT_LINE_COUNT = 100;

	/**
	 * 常量，默认干扰线RGB颜色最大值。
	 */
	private static final int DEFAULT_MAX_LINE_COLOR_RGB = 240;

	/**
	 * 常量，默认干扰线RGB颜色最小值。.
	 */
	private static final int DEFAULT_MIN_LINE_COLOR_RGB = 100;

	/**
	 * 常量，默认字体RGB颜色最大值。
	 */
	private static final int DEFAULT_MAX_FONT_COLOR_RGB = 255;

	/**
	 * 常量，默认字体RGB颜色最小值。
	 */
	private static final int DEFAULT_MIN_FONT_COLOR_RGB = 0;

	/**
	 * 常量，默认背景RGB颜色最大值。
	 */
	private static final int DEFAULT_MIN_BACKGROUND_COLOR_RGB = 200;

	/**
	 * 常量，默认背景RGB颜色最小值。
	 */
	private static final int DEFAULT_MAX_BACKGROUND_COLOR_RGB = 250;

	/**
	 * 常量，默认字体大小。
	 */
	private static final int DEFAULT_FONT_SIEZ = 26;

	/**
	 * 常量，默认验证码字符长度。
	 */
	private static final int ACCEPTED_WORD_LENGTH = 4;
	/**
	 * 常量，RGB颜色中最大的值.
	 */
	private static final int MAX_OF_RGB = 255;
	/**
	 * 验证码字符个数，默认为4个.
	 */
	private int acceptedWordLength;

	/**
	 * 背景干扰线的样式.默认为new BasicStroke(2f, BasicStroke.CAP_BUTT,
	 * BasicStroke.JOIN_BEVEL);
	 */
	private BasicStroke basicStroke;// 设置绘制背景干扰线的样式

	/**
	 * 验证码字体.默认为new Font("楷体", Font.BOLD, this.height / 2);
	 */
	private Font font;

	/**
	 * 验证码字体颜色，默认为红色Color.red
	 */
	private Color fontColor;

	/**
	 * 验证码图片高度为字体高度2倍.
	 */
	private int height;

	/**
	 * 验证码图片宽度为字体宽度*（acceptedWordLength+1）.
	 */
	private int width;

	/**
	 * 干扰线个数，默认为100条.
	 */
	private int lineCount;

	/**
	 * 干扰线颜色，默认为灰色Color..
	 */
	private Color lineColor;

	/**
	 * 图片背景颜色，默认为随机.
	 */
	private Color backgroundColor;

	/**
	 * 背景RGB颜色随机产生r值，g值，b值得最大范围.
	 */
	private int maxBackgroundColorRGB;

	/**
	 * 字体RGB颜色随机产生r值，g值，b值得最大范围.
	 */
	private int maxFontColorRGB;

	/**
	 * 背景RGB颜色随机产生r值，g值，b值得最小范围.
	 */
	private int minBackgroundColorRGB;

	/**
	 * 字体RGB颜色随机产生r值，g值，b值得最小范围.
	 */
	private int minFontColorRGB;
	/**
	 * 干扰线RGB颜色随机产生r值，g值，b值得最小范围.
	 */
	private int minLineColorRGB;
	/**
	 * 干扰线RGB颜色随机产生r值，g值，b值得最大范围.
	 */
	private int maxLineColorRGB;

	/**
	 * 旋转操作的第一个参数，旋转弧度，默认为this.theta = (45) * 3.14 / 180.
	 */
	private double theta;

	/**
	 * 缩放操作的最小缩放系数，默认为0.8f.
	 */
	private float minScaleSize;

	/**
	 * 自定义随机颜色，默认颜色包括红色，黑色，蓝色，绿色，蓝绿色，粉色，黄色，用户可以自己往里面加其他颜色
	 */
	private static final 	Color[] AVAIBLE_COLORS = {new Color(40,40,40), new Color(32,32,32),
		new Color(23,54,96), new Color(119,47,40), new Color(0,123,55), Color.DARK_GRAY};

	/**
	 * 默认构造函数.
	 */
	public CaptchaProperty() {
		this.acceptedWordLength = ACCEPTED_WORD_LENGTH;
		this.height =DEFAULT_HEIGHT;
		this.width = DEFAULT_FONT_SIEZ * (ACCEPTED_WORD_LENGTH + 1);
		this.font = new Font("微软雅黑", Font.BOLD, DEFAULT_FONT_SIEZ);
		this.maxBackgroundColorRGB = DEFAULT_MAX_BACKGROUND_COLOR_RGB;
		this.minBackgroundColorRGB = DEFAULT_MIN_BACKGROUND_COLOR_RGB;
		this.minFontColorRGB = DEFAULT_MIN_FONT_COLOR_RGB;
		this.maxFontColorRGB = DEFAULT_MAX_FONT_COLOR_RGB;
		this.minLineColorRGB = DEFAULT_MIN_LINE_COLOR_RGB;
		this.maxLineColorRGB = DEFAULT_MAX_LINE_COLOR_RGB;
		this.basicStroke = new BasicStroke(2f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL); // 定制线条样式
		this.lineCount = DEFAULT_LINE_COUNT;
		this.theta = DEFAULT_THETA;
		// this.anchorx=this.font.getSize() * i + 8;
		this.minScaleSize = DEFAULT_MIN_SCALE_SIZE;
		this.fontColor = getRandColor();
		this.lineColor = getRandColor(minLineColorRGB, maxLineColorRGB);
		this.backgroundColor = getRandColor(minBackgroundColorRGB,
				maxBackgroundColorRGB);
	}

	/**
	 * Gets the accepted word length.
	 *
	 * @return the accepted word length
	 */
	public int getAcceptedWordLength() {
		return acceptedWordLength;
	}

	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Gets the basic stroke.
	 *
	 * @return the basic stroke
	 */
	public BasicStroke getBasicStroke() {
		return basicStroke;
	}

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Gets the font color.
	 *
	 * @return the font color
	 */
	public Color getFontColor() {
		this.fontColor=getRandColor();
		return fontColor;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the line color.
	 *
	 * @return the line color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Gets the line count.
	 *
	 * @return the line count
	 */
	public int getLineCount() {
		return lineCount;
	}

	/**
	 * Gets the max background color rgb.
	 *
	 * @return the max background color rgb
	 */
	public int getMaxBackgroundColorRGB() {
		return maxBackgroundColorRGB;
	}

	/**
	 * Gets the max font color rgb.
	 *
	 * @return the max font color rgb
	 */
	public int getMaxFontColorRGB() {
		return maxFontColorRGB;
	}

	/**
	 * Gets the max line color rgb.
	 *
	 * @return the max line color rgb
	 */
	public int getMaxLineColorRGB() {
		return maxLineColorRGB;
	}

	/**
	 * Gets the min background color rgb.
	 *
	 * @return the min background color rgb
	 */
	public int getMinBackgroundColorRGB() {
		return minBackgroundColorRGB;
	}

	/**
	 * Gets the min font color rgb.
	 *
	 * @return the min font color rgb
	 */
	public int getMinFontColorRGB() {
		return minFontColorRGB;
	}

	/**
	 * Gets the min line color rgb.
	 *
	 * @return the min line color rgb
	 */
	public int getMinLineColorRGB() {
		return minLineColorRGB;
	}

	/**
	 * Gets the min scale size.
	 *
	 * @return the min scale size
	 */
	public float getMinScaleSize() {
		return minScaleSize;
	}

	/**
	 * 该方法主要作用是获得随机生成的颜色.
	 *
	 * @param minColorRGB
	 *            最小RGB颜色范围
	 * @param maxColorRGB
	 *            最大RGB颜色范围
	 * @return the rand color
	 */
	private Color getRandColor(int minColorRGB, int maxColorRGB) {
		Random random = new Random();

		int r, g, b;// 随机生成RGB颜色中的r,g,b值
		r = minColorRGB + random.nextInt(maxColorRGB - minColorRGB);
		g = minColorRGB + random.nextInt(maxColorRGB - minColorRGB);
		b = minColorRGB + random.nextInt(maxColorRGB - minColorRGB);
		return new Color(r, g, b);
	}
	/**
	 * 随机生成指定颜色中的一个
	 * @return
	 */
	private Color getRandColor(){
		Random random = new Random();
		return AVAIBLE_COLORS[random.nextInt(AVAIBLE_COLORS.length)];
	}
	/**
	 * Gets the theta.
	 *
	 * @return the theta
	 */
	public double getTheta() {
		return theta;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the accepted word length.
	 *
	 * @param acceptedWordLength
	 *            the accepted word length
	 */
	public void setAcceptedWordLength(int acceptedWordLength) {
		this.acceptedWordLength = acceptedWordLength;
		this.width = DEFAULT_FONT_SIEZ * (this.acceptedWordLength + 1);
	}

	/**
	 * Sets the background color.
	 *
	 * @param backgroundColor
	 *            the background color
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Sets the basic stroke.
	 *
	 * @param basicStroke
	 *            the basic stroke
	 */
	public void setBasicStroke(BasicStroke basicStroke) {
		this.basicStroke = basicStroke;
	}

	/**
	 * Sets the font.
	 *
	 * @param font
	 *            the font
	 */
	public void setFont(Font font) {
		if (font.getSize() * this.acceptedWordLength >= width) {
			this.width = font.getSize() * (this.acceptedWordLength + 1);
		}
		if (font.getSize() * 2 >= height) {
			this.height = font.getSize() * 2;
		}
		this.font = font;
	}

	/**
	 * Sets the font color.
	 *
	 * @param fontColor
	 *            the font color
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the height
	 */
	public void setHeight(int height) {
		if (height / 2 < this.font.getSize()) {
			this.font = new Font("微软雅黑", Font.BOLD, height / 2);
		}
		this.height = height;
	}

	/**
	 * Sets the line color.
	 *
	 * @param lineColor
	 *            the line color
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * Sets the line count.
	 *
	 * @param lineCount
	 *            the line count
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	/**
	 * Sets the max background color rgb.
	 *
	 * @param maxBackgroundColorRGB
	 *            the max background color rgb
	 */
	public void setMaxBackgroundColorRGB(int maxBackgroundColorRGB) {
		if (maxBackgroundColorRGB > MAX_OF_RGB) {
			maxBackgroundColorRGB = MAX_OF_RGB;
		}

		this.maxBackgroundColorRGB = maxBackgroundColorRGB;
	}

	/**
	 * Sets the max font color rgb.
	 *
	 * @param maxFontColorRGB
	 *            the max font color rgb
	 */
	public void setMaxFontColorRGB(int maxFontColorRGB) {
		if (maxFontColorRGB > MAX_OF_RGB) {
			maxFontColorRGB = MAX_OF_RGB;
		}
		this.maxFontColorRGB = maxFontColorRGB;
	}

	/**
	 * Sets the max line color rgb.
	 *
	 * @param maxLineColorRGB
	 *            the max line color rgb
	 */
	public void setMaxLineColorRGB(int maxLineColorRGB) {
		if (maxLineColorRGB > MAX_OF_RGB) {
			maxLineColorRGB = MAX_OF_RGB;
		}
		this.maxLineColorRGB = maxLineColorRGB;
	}

	/**
	 * Sets the min background color rgb.
	 *
	 * @param minBackgroundColorRGB
	 *            the min background color rgb
	 */
	public void setMinBackgroundColorRGB(int minBackgroundColorRGB) {
		if (minBackgroundColorRGB > MAX_OF_RGB) {
			minBackgroundColorRGB = MAX_OF_RGB;
		}
		this.minBackgroundColorRGB = minBackgroundColorRGB;
	}

	/**
	 * Sets the min font color rgb.
	 *
	 * @param minFontColorRGB
	 *            the min font color rgb
	 */
	public void setMinFontColorRGB(int minFontColorRGB) {
		if (minFontColorRGB > MAX_OF_RGB) {
			minFontColorRGB = MAX_OF_RGB;
		}
		this.minFontColorRGB = minFontColorRGB;
	}

	/**
	 * Sets the min line color rgb.
	 *
	 * @param minLineColorRGB
	 *            the min line color rgb
	 */
	public void setMinLineColorRGB(int minLineColorRGB) {
		if (minLineColorRGB > MAX_OF_RGB) {
			minLineColorRGB = MAX_OF_RGB;
		}
		this.minLineColorRGB = minLineColorRGB;
	}

	/**
	 * Sets the min scale size.
	 *
	 * @param minScaleSize
	 *            the min scale size
	 */
	public void setMinScaleSize(float minScaleSize) {
		if (minScaleSize > MAX_OF_RGB) {
			minScaleSize = MAX_OF_RGB;
		}
		this.minScaleSize = minScaleSize;
	}

	/**
	 * Sets the theta.
	 *
	 * @param theta
	 *            theta
	 */
	public void setTheta(double theta) {
		this.theta = theta;
	}

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the width
	 */
	public void setWidth(int width) {
		if (width / (this.acceptedWordLength + 1) < this.font.getSize()) {
			this.font = new Font("微软雅黑", Font.BOLD, width
					/ (this.acceptedWordLength + 1));
		}
		this.width = width;
	}
}
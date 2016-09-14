package com.yealink.uc.web.modules.capcha.service;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.yealink.uc.web.modules.capcha.util.CaptchaProperty;

import org.springframework.stereotype.Service;

@Service
public class CaptchaService {
    /**
     * 默认字体间隔大小，单位为px.
     */
    private static final int DEFAULT_SPACE_SIZE = 8;

    private CaptchaProperty property;

    /**
     * 可用生成随机验证码的字符，去掉o l 0 1 4个容易混淆的字符.
     */
    private static final char[] AVAILABLE_CAPTCHA_CHARS = new char[]{
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
        'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4',
        '5', '6', '7', '8', '9'};

    private String captchaChars = "";

    /**
     * 初始化图片验证码的相关参数.
     */
    public CaptchaService() {
        this.property = new CaptchaProperty();
    }

    /**
     * 生成验证码图片的背景.其中默认的背景色是在RGB范围为(200,250)随机生成的颜色，干扰线默认为100条，颜色
     * 在RGB范围为(100,150)之间随机生成。
     *
     * @param g 画笔
     */
    private void createBackground(Graphics g) {
        g.setColor(property.getBackgroundColor());
        g.fillRect(0, 0, property.getWidth(), property.getHeight()); // 绘制背景
        Graphics2D g2d = (Graphics2D) g; // 创建Grapchics2D对象
        g2d.setColor(property.getLineColor());
        Random random = new Random();
        for (int i = 0; i < property.getLineCount(); i++) {
            int x = random.nextInt(property.getWidth() - 1);
            int y = random.nextInt(property.getHeight() - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;
            BasicStroke bs = property.getBasicStroke();
            Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
            g2d.setStroke(bs);
            g2d.draw(line); // 绘制直线
        }
    }

    public BufferedImage getImage() {
        // 创建BufferedImage对象,其作用相当于一图片
        BufferedImage image = new BufferedImage(property.getWidth(),
            property.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        createBackground(g);
        setCaptchaChars();
        handleCaptcha(g, captchaChars);
        g.dispose();
        return image;
    }

    /**
     * 将生成的随机数进行随机缩放并旋转指定角度 ，设置字符的字体颜色等属性。.
     *
     * @param g     画笔
     * @param sRand 随机生成的验证码字符串
     */
    private void handleCaptcha(Graphics g, String sRand) {
        Random random = new Random();
        int acceptedWordLength = property.getAcceptedWordLength();
        for (int i = 0; i < acceptedWordLength; i++) {
            Graphics2D g2dWord = (Graphics2D) g;
            AffineTransform trans = rotateAndScale(random, i);
            g2dWord.setTransform(trans);
            g.setFont(property.getFont());
            g.setColor(property.getFontColor());
            String stmp = String.valueOf(sRand.charAt(i));
            g.drawString(stmp,
                (property.getFont().getSize() - DEFAULT_SPACE_SIZE)
                    * (i + 1) + DEFAULT_SPACE_SIZE,
                property.getHeight() * 2 / 3);
        }
    }

    private AffineTransform rotateAndScale(Random random, int i) {
        AffineTransform trans = new AffineTransform();
        trans.rotate(property.getTheta(),
            (property.getFont().getSize() - DEFAULT_SPACE_SIZE) * (i + 1)
                + DEFAULT_SPACE_SIZE, property.getHeight() / 3);
        /* 缩放文字 */
        float scaleSize = random.nextFloat() + property.getMinScaleSize();
        if (scaleSize > 1f) {
            scaleSize = 1f;
        }
        trans.scale(scaleSize, scaleSize);
        return trans;
    }

    private void setCaptchaChars() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        int availableChars = AVAILABLE_CAPTCHA_CHARS.length;
        for (int i = 0; i < property.getAcceptedWordLength(); i++) {
            stringBuffer.append(AVAILABLE_CAPTCHA_CHARS[random
                .nextInt(availableChars) % availableChars]);
        }
        this.captchaChars = stringBuffer.toString();
    }

    public void setProperty(CaptchaProperty picture) {
        this.property = picture;
    }

    public CaptchaProperty getProperty() {
        return property;
    }

    public String getCaptcha() {
        return captchaChars;
    }


}

package cn.albumenj.switchmonitor.util.qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author liuyueyi
 */
public class ImageUtil {

    /**
     * 在图片中间,插入圆角的logo
     *
     * @param qrCode 原图
     * @param logo   logo地址
     * @throws IOException
     */
    public static void insertLogo(BufferedImage qrCode, String logo, QrCodeOptions.LogoStyle logoStyle) throws IOException {
        int qrCodeWidth = qrCode.getWidth();
        int qrCodeHeight = qrCode.getHeight();

        // 获取logo图片
        BufferedImage bf = getImageByPath(logo);
        int size = bf.getWidth() > qrCodeWidth * 2 / 10 ? qrCodeWidth * 2 / 50 : bf.getWidth() / 5;
        // 边距为二维码图片的1/10
        bf = ImageUtil.makeRoundBorder(bf, logoStyle, size, Color.BLUE);

        // logo的宽高
        int w = bf.getWidth() > qrCodeWidth * 2 / 10 ? qrCodeWidth * 2 / 10 : bf.getWidth();
        int h = bf.getHeight() > qrCodeHeight * 2 / 10 ? qrCodeHeight * 2 / 10 : bf.getHeight();

        // 插入LOGO
        Graphics2D graph = qrCode.createGraphics();

        int x = (qrCodeWidth - w) / 2;
        int y = (qrCodeHeight - h) / 2;

        graph.drawImage(bf, x, y, w, h, null);
        graph.dispose();
        bf.flush();
    }


    /**
     * 根据路径获取图片
     *
     * @param path 本地路径 or 网络地址
     * @return 图片
     * @throws IOException
     */
    public static BufferedImage getImageByPath(String path) throws IOException {
        String http = "http";
        String start = "/";
        // 从网络获取logo
        if (path.startsWith(http)) {
            return ImageIO.read(new URL(path));

        }
        // 绝对地址获取logo
        else if (path.startsWith(start)) {
            return ImageIO.read(new File(path));
        }
        // 从资源目录下获取logo
        else {
            return ImageIO.read(ImageUtil.class.getClassLoader().getResourceAsStream(path));
        }
    }


    /**
     * fixme 边框的计算需要根据最终生成logo图片的大小来定义，这样才不会出现不同的logo原图，导致边框不一致的问题
     * <p>
     * 生成圆角图片 & 圆角边框
     *
     * @param image     原图
     * @param logoStyle 圆角的角度
     * @param size      边框的边距
     * @param color     边框的颜色
     * @return 返回带边框的圆角图
     */
    public static BufferedImage makeRoundBorder(BufferedImage image, QrCodeOptions.LogoStyle logoStyle, int size, Color color) {
        // 将图片变成圆角
        int cornerRadius = 0;
        if (logoStyle == QrCodeOptions.LogoStyle.ROUND) {
            cornerRadius = 30;
            image = makeRoundedCorner(image, cornerRadius);
        }

        int borderSize = size;
        int w = image.getWidth() + borderSize;
        int h = image.getHeight() + borderSize;
        BufferedImage output = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color == null ? Color.WHITE : color);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius,
                cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, size, size, null);
        g2.dispose();

        return output;
    }


    /**
     * 生成圆角图片
     *
     * @param image        原始图片
     * @param cornerRadius 圆角的弧度
     * @return 返回圆角图
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image,
                                                  int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius,
                cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }
}
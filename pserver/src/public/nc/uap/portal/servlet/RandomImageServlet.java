package nc.uap.portal.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * �����֤��ͼƬ����Servlet��
 * 
 * @author dengjt
 * 
 */
public class RandomImageServlet extends HttpServlet {
	private static final long serialVersionUID = 67479940156888717L;

	//�˲����߼��޷�����jsp�У���jsp��Ĭ�ϴ�reponse���������������ܻ�ȡ���Ρ�

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ����ҳ�治����
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// ���ڴ��д���ͼ��
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// ��ȡͼ��������
		Graphics g = image.getGraphics();

		// ���������
		Random random = new Random();

		// �趨����ɫ
		g.setColor(new Color(0xD8E3E8));
		g.fillRect(0, 0, width, height);

		// �趨����
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// ���߿�
		//g.setColor(new Color(0x000000));
		//g.drawRect(0,0,width-1,height-1);

		// �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
		g.setColor(getRandColor(0, 120));
		for (int i = 0; i < 4; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(20);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// ȡ�����������֤��(4λ����)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// ����֤����ʾ��ͼ����
//			g.setColor(new Color(20 + random.nextInt(110), 20 + random
//					.nextInt(110), 20 + random.nextInt(110)));
			g.setColor(new Color(0x000000));
			// ���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
			g.drawString(rand, 13 * i + 6, 16);
		}

		// ����֤�����SESSION
		request.getSession(true).setAttribute("rand", sRand);

		// ͼ����Ч
		g.dispose();

		// ���ͼ��ҳ��
		// ImageIO.write(image, "JPEG", response.getOutputStream());

		ServletOutputStream sos = response.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
		encoder.encode(image);
		sos.flush();
		sos.close();
	}
	
	private Color getRandColor(int fc, int bc) {
		// ������Χ��������ɫ
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}

package com.spider.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 生成验证码图片
 * @author 16967
 *
 */
@Slf4j
public class Code extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try{
			//1.创建空白图片
			BufferedImage image = new BufferedImage(100,30,BufferedImage.TYPE_INT_RGB);
			//2.获取画笔
			Graphics g = image.getGraphics();
			//3.创建随机数对象
			Random r = new Random();
			//4.设置画笔的颜色(rgb)
			g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
			//5.绘制一个矩形背景(坐标+宽高)
			g.fillRect(0,0,100,30);
			//6.生成随机字符串
			String number = getNumber(r);
			// 设置session 值
			HttpSession session = request.getSession();
			session.setAttribute("code", number);

			g.setColor(new Color(0,0,0));
			//8.设置验证码字体样式,大小
			g.setFont(new Font(null,Font.BOLD,24));
			//9.绘制字符串
			g.drawString(number, 5, 25);
			//10.绘制干扰线
			for(int i=0;i<8;i++) {
				//11.设置干扰先颜色
				g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255),r.nextInt(255)));
				//12.绘制干扰线（两端点的坐标）
				g.drawLine(r.nextInt(100), r.nextInt(30),r.nextInt(100), r.nextInt(30));
			}
			response.setContentType("image/jpeg");
			out = response.getOutputStream();
			ImageIO.write(image, "jpeg", out);

		} catch(Exception e){
			log.error("创建yan验证码图片失败:", e);
		} finally{
			try {
				out.close();
			} catch (IOException e) {
				log.error("关闭输出流失败",e);
				e.printStackTrace();
			}
		}

		
	}

	private String getNumber(Random r) {
		char [] c = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
				'0','1','2','3','4','5','6','7','8','9'};
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<5;i++) {
			sb.append(c[r.nextInt(c.length-1)]);
		}
		return sb.toString();
	}
	
}

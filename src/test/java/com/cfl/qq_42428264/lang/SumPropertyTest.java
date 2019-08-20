package com.cfl.qq_42428264.lang;

import com.cfl.qq_42428264.lang.pojo.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName SumPropertyTest
 * @Description TODO
 * @Author msi
 * @Date 2019/8/20 20:45
 */
public class SumPropertyTest {
	@Test
	public void test1(){
		List<User> list = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setaByte((byte)(i*2));
			user.setaShort((short)(i*30));
			user.setaInt(i*50);
			user.setaLong(i*100L);
			user.setaFloat((float)(i*1000));
			user.setaDouble(3.14*10000);

			list.add(user);
		}
		System.out.println("list");
		for (User u :
				list) {
			System.out.println(u.toString());
		}

		// 测试合计
		User u = new User();
		SumProperty.doTotale(list, u);
		System.out.println("u = " + u);



	}
}

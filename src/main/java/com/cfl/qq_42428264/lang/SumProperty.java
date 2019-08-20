package com.cfl.qq_42428264.lang;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;


/**
 * 计算List类型集合的 合计
 * 前提：
 *      1.成员变量类型为引用类型
 *      2.有无参构造方法
 *      3.需计算的成员变量有getter setter方法
 * @ClassName SumProperty
 * @Description TODO
 * @Author msi
 * @Date 2019/8/20 20:41
 */
public class SumProperty {
	/**
	 * 将结果计算并赋值给对象
	 * @param list  需要计算合计的集合
	 * @param object   合计对象
	 * @param <T> 类型
	 */
	public static <T> void doTotale(List<T> list, Object object) {
		try {
			Number[] nums = sum(list,object );
			Class<?> clazz = object.getClass();
			// object = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				// 如果不是Number类型的字段，跳过
				if (!Number.class.isAssignableFrom(fields[i].getType())) {
					continue;
				}
				// 序列化id跳过加和
				if (fields[i].getName().equalsIgnoreCase("serialVersionUID")) {
					continue;
				}
				fields[i].setAccessible(true);
				// 默认nums中存放的Integer类型。
				if (Byte.class.isAssignableFrom(fields[i].getType())) {
					fields[i].set(object, Byte.parseByte(nums[i].toString()));
					continue;
				}
				if (Short.class.isAssignableFrom(fields[i].getType())) {
					fields[i].set(object, Short.parseShort(nums[i].toString()));
					continue;
				}
				if (Double.class.isAssignableFrom(fields[i].getType())) {
					fields[i].set(object, Double.parseDouble(nums[i].toString()));
					continue;
				}
				if (Float.class.isAssignableFrom(fields[i].getType())) {
					fields[i].set(object, Float.parseFloat(nums[i].toString()));
					continue;
				}
				if (Long.class.isAssignableFrom(fields[i].getType())) {
					fields[i].set(object, Long.parseLong(nums[i].toString()));
					continue;
				}

				fields[i].set(object, nums[i]);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws
	 * @Title: sum
	 * @Description: TODO()
	 * @param: list:需要计算的集合
	 * @param: clazzName：list的泛型
	 * @return: Number[]，数组下标index即是属性从上至下从0开始递增。
	 * object类中由Number类型修饰的属性求和。
	 * 属性类型为Number的计算值，不是Number类型的字段，nums[index]=null
	 */
	private static <T> Number[] sum(List<T> list, Object object){
		Class<?> clazz = object.getClass();
		// 获取所有字段(成员变量，即使没有getter、setter)
		Field[] clazzFileds = clazz.getDeclaredFields();
		Number[] nums = new Number[clazzFileds.length];
		// 初始每个元素为0
		for (int i = 0; i < nums.length; i++) {
			nums[i] = 0;
		}
		// 循环集合，让nums数组对应字段的值累加
		for (T t : list) {
			clazzFileds = clazz.getDeclaredFields();
			// 对象的属性类型
			for (int i = 0; i < clazzFileds.length; i++) {
				// System.out.println(clazzFileds[i].getName());
				// 设置越过private
				clazzFileds[i].setAccessible(true);

				// 如果不是Number类型的字段，跳过(所以pojo的属性要引用类型才能判断)
				if (!Number.class.isAssignableFrom(clazzFileds[i].getType())) {
					nums[i] = null;
					continue;
				}
				// 字段对应的getter()的值
				Number n = null;
				try {
					n = (Number) clazzFileds[i].get(t);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (n != null) {
					// 相当于 nums[i] += n;
					nums[i] = SumProperty.getValue(n, nums[i], n.getClass());
				}
			}
		}
		return nums;
	}




	/**
	 * 将Number类型的对象判断为基本类型，然后再做加法
	 * @param n 循环中元素属性值
	 * @param n2 对应属性之前累加的值
	 * @param type
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static Number getValue(Number n, Number n2, Type type) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(type.getTypeName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// java.lang.Double 类型的字符串
//		String n2Name = getType(n2).getTypeName();
		String n2Name = n2.getClass().getTypeName();
		// 根据type来调用sum的重载方法
		if (Double.class.isAssignableFrom(clazz)) {
			double n1 = n.doubleValue();
			return sum(n1, n2, n2Name);
		} else if (Float.class.isAssignableFrom(clazz)) {
			float n1 = n.floatValue();
			return sum(n1, n2, n2Name);
		} else if (Long.class.isAssignableFrom(clazz)) {
			long n1 = n.longValue();
			return sum(n1, n2, n2Name);
		} else if (Integer.class.isAssignableFrom(clazz)) {
			int n1 = n.intValue();
			return sum(n1, n2, n2Name);
		} else if (Short.class.isAssignableFrom(clazz)) {
			short n1 = n.shortValue();
			return sum(n1, n2, n2Name);
		} else if (Byte.class.isAssignableFrom(clazz)) {
			byte n1 = n.byteValue();
			return sum(n1, n2, n2Name);
		}
		throw new RuntimeException("com.mf.invo.utils.SumField.getValue(Number, Number, Type)错误");
	}

	// double
	private static Number sum(double n1, Number n2, String n2Name) {
		switch (n2Name) {
			case "java.lang.Double":
				return n1 + (Double) (n2);
			case "java.lang.Float":
				return n1 + (Float) (n2);
			case "java.lang.Long":
				return n1 + (Long) (n2);
			case "java.lang.Integer":
				return n1 + (Integer) (n2);
			case "java.lang.Short":
				return n1 + (Short) (n2);
			case "java.lang.Byte":
				return n1 + (Byte) (n2);
		}
		return null;
	}

	// float
	private static Number sum(float n1, Number n2, String n2Name) {
		switch (n2Name) {
			case "java.lang.Double":
				return n1 + (Double) (n2);
			case "java.lang.Float":
				return n1 + (Float) (n2);
			case "java.lang.Long":
				return n1 + (Long) (n2);
			case "java.lang.Integer":
				return n1 + (Integer) (n2);
			case "java.lang.Short":
				return n1 + (Short) (n2);
			case "java.lang.Byte":
				return n1 + (Byte) (n2);
		}
		return null;
	}

	// long
	private static Number sum(long n1, Number n2, String n2Name) {
		switch (n2Name) {
			case "java.lang.Double":
				return n1 + (Double) (n2);
			case "java.lang.Float":
				return n1 + (Float) (n2);
			case "java.lang.Long":
				return n1 + (Long) (n2);
			case "java.lang.Integer":
				return n1 + (Integer) (n2);
			case "java.lang.Short":
				return n1 + (Short) (n2);
			case "java.lang.Byte":
				return n1 + (Byte) (n2);
		}
		return null;
	}

	// int
	private static Number sum(int n1, Number n2, String n2Name) {
		switch (n2Name) {
			case "java.lang.Double":
				return n1 + (Double) (n2);
			case "java.lang.Float":
				return n1 + (Float) (n2);
			case "java.lang.Long":
				return n1 + (Long) (n2);
			case "java.lang.Integer":
				return n1 + (Integer) (n2);
			case "java.lang.Short":
				return n1 + (Short) (n2);
			case "java.lang.Byte":
				return n1 + (Byte) (n2);
		}
		return null;
	}

	// short
	private static Number sum(short n1, Number n2, String n2Name) {
		switch (n2Name) {
			case "java.lang.Double":
				return n1 + (Double) (n2);
			case "java.lang.Float":
				return n1 + (Float) (n2);
			case "java.lang.Long":
				return n1 + (Long) (n2);
			case "java.lang.Integer":
				return n1 + (Integer) (n2);
			case "java.lang.Short":
				return n1 + (Short) (n2);
			case "java.lang.Byte":
				return n1 + (Byte) (n2);
		}
		return null;
	}

	// byte
	private static Number sum(byte n1, Number n2, String n2Name) {
		switch (n2Name) {
			case "java.lang.Double":
				return n1 + (Double) (n2);
			case "java.lang.Float":
				return n1 + (Float) (n2);
			case "java.lang.Long":
				return n1 + (Long) (n2);
			case "java.lang.Integer":
				return n1 + (Integer) (n2);
			case "java.lang.Short":
				return n1 + (Short) (n2);
			case "java.lang.Byte":
				return n1 + (Byte) (n2);
		}
		return null;
	}


	/**
	 * 将Number类型转换为基本数据类型 @Title: getType @Description: TODO() @param: @return:
	 * Type @throws
	 */
//	private static Type getType(Number n) {
//		Class<?> clazz = n.getClass();
//		return clazz;
//		if (Double.class.isAssignableFrom(clazz)) {
//			return n.getClass();
//		} else if (Float.class.isAssignableFrom(clazz)) {
//			return Float.class;
//		} else if (Long.class.isAssignableFrom(clazz)) {
//			return Long.class;
//		} else if (Integer.class.isAssignableFrom(clazz)) {
//			return Integer.class;
//		} else if (Short.class.isAssignableFrom(clazz)) {
//			return Short.class;
//		} else if (Byte.class.isAssignableFrom(clazz)) {
//			return Byte.class;
//		} else {
//			throw new RuntimeException("com.mf.invo.utils.SumField.getType(Number)的Number无效");
//		}
//	}

}

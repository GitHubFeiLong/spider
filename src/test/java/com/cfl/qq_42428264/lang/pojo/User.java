package com.cfl.qq_42428264.lang.pojo;

/**
 * @ClassName User
 * @Description TODO
 * @Author msi
 * @Date 2019/8/20 20:46
 */
public class User {
	private Byte aByte;
	private Short aShort;
	private Integer aInt;
	private Long aLong;
	private Float aFloat;
	private Double aDouble;

	public User() {
	}

	@Override
	public String toString() {
		return "User{" +
				"aByte=" + aByte +
				", aShort=" + aShort +
				", aInt=" + aInt +
				", aLong=" + aLong +
				", aFloat=" + aFloat +
				", aDouble=" + aDouble +
				'}';
	}

	public Byte getaByte() {
		return aByte;
	}

	public void setaByte(Byte aByte) {
		this.aByte = aByte;
	}

	public Short getaShort() {
		return aShort;
	}

	public void setaShort(Short aShort) {
		this.aShort = aShort;
	}

	public Integer getaInt() {
		return aInt;
	}

	public void setaInt(Integer aInt) {
		this.aInt = aInt;
	}

	public Long getaLong() {
		return aLong;
	}

	public void setaLong(Long aLong) {
		this.aLong = aLong;
	}

	public Float getaFloat() {
		return aFloat;
	}

	public void setaFloat(Float aFloat) {
		this.aFloat = aFloat;
	}

	public Double getaDouble() {
		return aDouble;
	}

	public void setaDouble(Double aDouble) {
		this.aDouble = aDouble;
	}

	public User(Byte aByte, Short aShort, Integer aInt, Long aLong, Float aFloat, Double aDouble) {
		this.aByte = aByte;
		this.aShort = aShort;
		this.aInt = aInt;
		this.aLong = aLong;
		this.aFloat = aFloat;
		this.aDouble = aDouble;
	}
}

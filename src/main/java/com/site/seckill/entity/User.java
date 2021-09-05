package com.site.seckill.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int id;
	private String userName;
	private String phone;
	private String password;
	private String salt;//用于拼接密码后加密
	private String head;
	private int loginCount;
	private Date registerDate;
	private Date lastLoginDate;
}

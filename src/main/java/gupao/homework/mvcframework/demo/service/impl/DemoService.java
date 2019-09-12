package gupao.homework.mvcframework.demo.service.impl;


import gupao.homework.mvcframework.annotation.GPService;
import gupao.homework.mvcframework.demo.service.IDemoService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name;
	}

}

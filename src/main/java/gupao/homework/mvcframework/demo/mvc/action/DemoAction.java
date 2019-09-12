package gupao.homework.mvcframework.demo.mvc.action;



import gupao.homework.mvcframework.annotation.GPAutowired;
import gupao.homework.mvcframework.annotation.GPController;
import gupao.homework.mvcframework.annotation.GPRequestMapping;
import gupao.homework.mvcframework.annotation.GPRequestParam;
import gupao.homework.mvcframework.demo.service.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@GPController
@GPRequestMapping("/demo")
public class DemoAction {

  	@GPAutowired
	private IDemoService demoService;

	@GPRequestMapping("/query")
	public void query(HttpServletRequest req, HttpServletResponse resp,
                      @GPRequestParam("name") String name){
		String result = demoService.get(name);
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GPRequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse resp,
                    @GPRequestParam("a") Integer a, @GPRequestParam("b") Integer b){
		try {
			resp.getWriter().write(a + "+" + b + "=" + (a + b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

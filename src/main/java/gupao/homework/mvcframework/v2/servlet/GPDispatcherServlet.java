package gupao.homework.mvcframework.v2.servlet;

import gupao.homework.mvcframework.annotation.GPAutowired;
import gupao.homework.mvcframework.annotation.GPController;
import gupao.homework.mvcframework.annotation.GPRequestMapping;
import gupao.homework.mvcframework.annotation.GPService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 简易版MVC
 * @author alan
 * @Date 2019/09/11
 */
public class GPDispatcherServlet extends HttpServlet {
    /**
     * 存储aplication.properties的配置内容
     */
    private Properties contextConfig = new Properties();

    /**
     * 存储扫描到的类
     */
    private List<String> classNames = new ArrayList<>();

    /**
     * ioc容器
     */
    private Map<String, Object> ioc = new HashMap<>();

    /**
     * Method映射
     */
    private Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 任务分发
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Excetion Detail:" + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+","/");
        if (!this.handlerMapping.containsKey(url)) {
            try {
                resp.getWriter().write("404 Not Found!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        Method method = this.handlerMapping.get(url);
        Map<String,String[]> params = req.getParameterMap();

        // 动态获取形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();

        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        try {
            method.invoke(ioc.get(beanName),new Object[]{req,resp,params.get("name")[0]});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        // 2.扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        // 3.初始化相关的类的实例，并放入到IOC容器中
        doInstance();
        // 4.依赖注入
        doAutowried();
        // 5.初始化HandlerMapping，做映射
        initHandlerMapping();

        System.out.println("GP Spring framework is init.");
    }

    /**
     * 初始化HandlerMapping，做映射
     */
    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            System.out.println("IOC is null");
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(GPController.class)) {
                continue;
            }

            String baseUrl = "";
            //获取Controller的url配置
            if(clazz.isAnnotationPresent(GPRequestMapping.class)) {
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)) {
                    continue;
                }
                GPRequestMapping annotation1 = method.getAnnotation(GPRequestMapping.class);
                String url = ("/" + baseUrl + "/" + annotation1.value())
                        .replaceAll("/+", "/");
                handlerMapping.put(url,method);
                System.out.println("Mapped " + url + "," + method);
            }
        }
    }

    /**
     * 依赖注入
     */
    private void doAutowried() {
        if (ioc.isEmpty()) {
            System.out.println("IOC is null");
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(GPAutowired.class)) {
                    continue;
                }
                GPAutowired annotation = field.getAnnotation(GPAutowired.class);
                String beanName = annotation.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                    System.out.println("beanName = " + beanName);
                }
                field.setAccessible(true);
                try {
                    // 被修改的对象名，新的值
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化IOC容器
     */
    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(GPController.class)) {
                    Object o = clazz.newInstance();
                    //beanName首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, o);
                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    Object o = clazz.newInstance();
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    GPService annotation = clazz.getAnnotation(GPService.class);
                    if (!"".equals(annotation.value())) {
                        beanName = annotation.value();
                    }
                    ioc.put(beanName, o);
                    // 根据类型注入实现
                    for (Class<?> aClass : clazz.getInterfaces()) {
                        if (ioc.containsKey(aClass.getName())) {
                            throw new Exception("The beanName is exists!!");
                        }
                        ioc.put(aClass.getName(), o);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 扫描包路径下的所有类文件
     *
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {
        // 扫描传过来的包中的所有类
        URL url = this.getClass().getClassLoader()
                .getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        for (File file1 : file.listFiles()) {
            if (file1.isDirectory()) {
                doScanner(scanPackage + "." + file1.getName());
            } else {
                if (!file1.getName().endsWith(".class")) {
                    continue;
                }
                String className = scanPackage + "." + file1.getName().replaceAll(".class", "");
                classNames.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     *
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = null;
        try {
            fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

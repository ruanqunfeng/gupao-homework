package gupao.homework.mvcframework.v3.servlet;

import gupao.homework.mvcframework.annotation.GPAutowired;
import gupao.homework.mvcframework.annotation.GPController;
import gupao.homework.mvcframework.annotation.GPRequestMapping;
import gupao.homework.mvcframework.annotation.GPRequestParam;
import gupao.homework.mvcframework.annotation.GPService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
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
 *
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
    //private Map<String, Method> handlerMapping = new HashMap<>();
    private List<HandlerMapping> handlerMapping = new ArrayList<>();

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
        try {
            HandlerMapping handler = getHandler(req);
            if (null == handler) {
                resp.getWriter().write("404 Not Found");
                return;
            }
            // 获取方法的参数列表
            Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
            // 保存所有需要自动赋值的参数值
            Object [] paramValues = new Object[parameterTypes.length];

            Map<String, String[]> parameterMap = req.getParameterMap();
            for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");

                if (!handler.paramIndexMapping.containsKey(param.getKey())) {
                    continue;
                }
                int index = handler.paramIndexMapping.get(param.getKey());
                paramValues[index] = convert(parameterTypes[index], value);
            }

            //设置方法中的request和response对象
            if (handler.paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
                int reqIndex = handler.paramIndexMapping.get(HttpServletRequest.class.getName());
                paramValues[reqIndex] = req;
            }
            if (handler.paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
                int respIndex = handler.paramIndexMapping.get(HttpServletResponse.class.getName());
                paramValues[respIndex] = resp;
            }

            handler.method.invoke(handler.obj, paramValues);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        if (handlerMapping.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        // 返回当前站点的根目录名字
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (HandlerMapping mapping : handlerMapping) {
            if (mapping.getUrl().equals(url)) {
                return mapping;
            }
        }
        return null;
    }

    /**
     * 类型转换，暂时只实现了Integer的转换
     * @param type
     * @param value
     * @return
     */
    private Object convert(Class<?> type,String value){
        if(Integer.class == type){
            return Integer.valueOf(value);
        }
        return value;
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
            if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
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
                handlerMapping.add(new HandlerMapping(entry.getValue(), method, url));
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

    public class HandlerMapping {
        // 对象
        private Object obj;
        // 需要调用的方法
        private Method method;
        // 方法对应的url
        private String url;
        // 方法中的参数与对应的下标
        protected Map<String, Integer> paramIndexMapping;

        public HandlerMapping(Object obj, Method method, String url) {
            this.obj = obj;
            this.method = method;
            this.url = url;

            paramIndexMapping = new HashMap<>();
            putParamIndexMapping(method);
        }

        public Object getObj() {
            return obj;
        }

        public Method getMethod() {
            return method;
        }

        public String getUrl() {
            return url;
        }

        private void putParamIndexMapping(Method method) {
            // 外层是参数名，内层是各个注解
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof GPRequestParam) {
                        String paramName = ((GPRequestParam) annotation).value();
                        if (!"".equals(paramName.trim())) {
                            paramIndexMapping.put(paramName, i);
                        }
                    }
                }
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if (parameterType == HttpServletRequest.class ||
                        parameterType == HttpServletResponse.class) {
                    paramIndexMapping.put(parameterType.getName(), i);
                }
            }
        }
    }
}

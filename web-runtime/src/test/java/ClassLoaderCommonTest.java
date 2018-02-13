import com.anyun.cloud.web.common.task.YwAz;
import com.anyun.cloud.web.service.common.ClassLoaderCommon;
import com.anyun.cloud.web.service.common.entity.ClassMethodParams;
import com.anyun.cloud.web.service.common.entity.MethodParams;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassLoaderCommonTest {

    URLClassLoader classLoader;
    ClassLoaderCommon classLoaderCommon;
    String propFile = "application.properties";
    String type = "YwAz";

    @Before
    public void before() throws MalformedURLException {
        URL[] urls = new URL[]{
                new File("/home/yw/IdeaProjects/anyun-cloud-web-main/web-manage/target/anyun-cloud-web-main-manage-1.0.0.jar").toURI().toURL()
        };
        classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
        classLoaderCommon = ClassLoaderCommon.getCommon();
    }

    /**
     * 测试通过配置文件查找需要扫描的包名称
     * @throws Exception
     */
    @Test
    public void test_scan_jar() throws Exception {
        URL[] urls = new URL[]{
                new File("/home/yw/IdeaProjects/anyun-cloud-web-main/web-manage/target/anyun-cloud-web-main-manage-1.0.0.jar").toURI().toURL()
        };
        URLClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
        String propFile = "application.properties";
        String type = "YwAz";
        List<String> list = ClassLoaderCommon.getCommon().scanPackageNames(classLoader,propFile,type);
        list.forEach(object -> System.out.println(object));
    }

    /**
     * 测试扫描包里面的类
     */
    @Test
    public void test_scan_type() throws Exception {
        List<ClassMethodParams> classList = classLoaderCommon.scanClasses(classLoader, YwAz.class);
        classList.forEach(clazz -> {
            try {
                Class aClass = clazz.getClazz();
                Method method = clazz.getMethodList().get(0).getMethod();
                Object instance = aClass.getDeclaredConstructor().newInstance();
                Object[] params = new Object[method.getParameterTypes().length];
                params[0] = "aa";
                params[1] = Integer.valueOf(1);
                method.invoke(instance,params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 测试直接执行方法
     * @throws Exception
     */
    @Test
    public void test_DoMethod() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("a","YwTest");
        map.put("b",1);
        map.put("c","error test");
        map.put("d","may be this is a error");
        map.put("need","need to learn mysql");
        classLoaderCommon.scanMethodAndDoIt(classLoader,"ywTest","ywMethod",map);
    }

    @Test
    public void test_scan() throws Exception {
        List<String> list = classLoaderCommon.scanPackageNames(classLoader,propFile,type);
        List<Class> classList = classLoaderCommon.scanClasses(classLoader,YwAz.class,list);
        classList.forEach(clazz -> {
            List<MethodParams> methodParamsList = classLoaderCommon.scanMethodParams(clazz);
            methodParamsList.stream().forEach(methodParams -> {
                if (methodParams.getParams() != null)
                    methodParams.getParams().stream().forEach(param -> {
                        System.out.println(param);
                    });
            });
        });
    }
}
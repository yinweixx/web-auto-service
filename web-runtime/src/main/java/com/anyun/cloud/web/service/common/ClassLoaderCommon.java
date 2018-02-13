package com.anyun.cloud.web.service.common;

import com.anyun.cloud.web.common.task.YwAz;
import com.anyun.cloud.web.common.task.YwMethod;
import com.anyun.cloud.web.common.task.YwParams;
import com.anyun.cloud.web.service.common.entity.ClassDescription;
import com.anyun.cloud.web.service.common.entity.ClassMethodParams;
import com.anyun.cloud.web.service.common.entity.MethodParams;
import com.anyun.cloud.web.service.runtime.YwazServiceClassLoader;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import java.lang.reflect.Parameter;
import java.util.*;

public class ClassLoaderCommon {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderCommon.class);
    private static ClassLoaderCommon common;

    private ClassLoaderCommon(){}

    public static ClassLoaderCommon getCommon(){
        synchronized (ClassLoaderCommon.class){
            if(common == null)
                common = new ClassLoaderCommon();
        }
        return common;
    }

    /**
     * 根据注解value获取对应的类
     */
    public Class scanClassFromAnnotationValue(List<Class> classList,String annotationClass,String annotationMethod,Map<String,?> params){
        classList.stream().forEach(aClass -> {
            YwAz annotation = (YwAz) aClass.getDeclaredAnnotation(YwAz.class);
            String annotationName = annotation.name();
            if(!annotationName.equals(annotationClass))
                return;
            Arrays.stream(aClass.getDeclaredMethods()).forEach(method -> {
                YwMethod ywMethod = method.getDeclaredAnnotation(YwMethod.class);
                String name = ywMethod.name();
                if(!name.equals(annotationMethod))
                    return;

            });
        });
        return null;
    }

    /**
     * 扫描本地服务
     */
    public ClassDescription scan_Local(ClassLoader scanClassLoader,String propFile,String type) throws Exception {
        List<String> packageNames = scanPackageNames(scanClassLoader,propFile,type);
        ClassDescription classDescription = new ClassDescription();
        classDescription.setPackageNames(packageNames);
        if(type.equals(ClassDescription.TYPE_SERVICE)){
//            List<Class> allClasses =
        }
        return null;
    }

    /**
     * 扫描配置文件查找需要扫描的包名称
     * 获得包名称
     */
    public List<String> scanPackageNames(ClassLoader classLoader,String proFile,String type) throws Exception {
        List<String> packageNames = new ArrayList<>();
        Properties properties = new Properties();
        properties.load(Resources.getResourceAsReader(classLoader,proFile));
        Enumeration<?> prokeys = properties.propertyNames();
        String pkgPrefix = "";
        if(type.equals(ClassDescription.TYPE_SERVICE))
            pkgPrefix = YwazServiceClassLoader.PREFIX_SERVICE_PKG;
        else
            throw new Exception("error");
        while (prokeys.hasMoreElements()){
            String key = prokeys.nextElement().toString();
            if(!key.startsWith(pkgPrefix))
                continue;
            String serviceName = properties.getProperty(key);
            packageNames.add(serviceName);
        }
        return packageNames;
    }

    /**
     * 扫描类，方法，方法参数
     */
    @Deprecated
    public List<ClassMethodParams> scanClasses(ClassLoader classLoader, Class annotationClass){
        List<ClassMethodParams> allClasses = new ArrayList<>();
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.addClassLoader(classLoader);
        scanner.matchClassesWithAnnotation(annotationClass,new ClassAnnotationMatchProcessor(){
            @Override
            public void processMatch(Class<?> aClass) {
                ClassMethodParams classMethodParams = new ClassMethodParams();
                List<ClassMethodParams.Methods> methodsList = new ArrayList<>();
                classMethodParams.setClazz(aClass);
                Arrays.stream(aClass.getDeclaredMethods()).forEach(method ->{
                    YwMethod annotation = method.getAnnotation(YwMethod.class);
                    if(annotation == null)
                        return;
                    ClassMethodParams.Methods methods = new ClassMethodParams.Methods();
                    methods.setMethod(method);
                    List<String> paramAnnoations = new ArrayList<>();
                    try {
                        Arrays.stream(method.getParameters()).forEach(param -> {
                            YwParams ywParams = param.getAnnotation(YwParams.class);
                            if(ywParams == null)
                                return;
                            paramAnnoations.add(ywParams.value());
                        });
                        methods.setParams(paramAnnoations);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    methodsList.add(methods);
                });
                classMethodParams.setMethodList(methodsList);
                allClasses.add(classMethodParams);
            }
        }).scan();
        return allClasses;
    }

    /**
     * 扫描配置文件中的带有注解并实现某接口的类
     * @param classLoader
     * @param annotationClass
     * @param pkgNames
     * @param classType
     * @return
     */
    public List<Class> scanClasses(ClassLoader classLoader,Class annotationClass,List<String> pkgNames,Class classType) {
        List<Class> allClasses = new ArrayList<>();
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.addClassLoader(classLoader);
        scanner.matchClassesWithAnnotation(annotationClass,
                aClass -> Arrays.stream(aClass.getInterfaces()).filter(c -> {
                    boolean exist = pkgExist(pkgNames, aClass);
                    boolean isTypeInterface = isTypeInterface(aClass, classType);
                    LOGGER.debug("scan class: [{}]. is define? [{}] . is cloud impl? [{}] "
                            , aClass.getCanonicalName(), exist, isTypeInterface);
                    if (!exist)
                        return false;
                    if (isTypeInterface) {
                        allClasses.add(aClass);
                        return true;
                    }
                    return false;
                }).findAny());
        scanner.scan();
        return allClasses;
    }

    /**
     * 扫描配置文件中所有带注解的类
     */
    public List<Class> scanClasses(ClassLoader classLoader,Class annotationClass,List<String> pkgNames){
        List<Class> allClasses = new ArrayList<>();
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.addClassLoader(classLoader);
        scanner.matchClassesWithAnnotation(annotationClass, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                boolean exist = pkgExist(pkgNames,aClass);
                if(!exist)
                    return;
                allClasses.add(aClass);
            }
        }).scan();
        return allClasses;
    }

    /**
     * 扫描配置文件中所有带注解的类，并且注解value对应
     * @param classLoader           classloader
     * @param annotationClass       注释类
     * @param classAnnotation       类注释value
     * @param pkgNames              扫描路径
     * @return
     */
    public Class scanClass(ClassLoader classLoader,Class annotationClass,String classAnnotation,List<String> pkgNames){
        List<Class> allClasses = new ArrayList<>();
        new FastClasspathScanner().addClassLoader(classLoader).matchClassesWithAnnotation(annotationClass, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                boolean exist = pkgExist(pkgNames,aClass);
                if (!exist)
                    return;
                YwAz clazz = aClass.getAnnotation(YwAz.class);
                if (clazz.name().equals(classAnnotation))
                    allClasses.add(aClass);
            }
        }).scan();
        if (allClasses.size() != 0)
            return allClasses.get(0);
        return null;
    }

    /**
     * 扫描类里面的方法和参数
     */
    public List<MethodParams> scanMethodParams(Class aClass){
        MethodParams methodParams = new MethodParams();
        List<MethodParams> methodParamsList = new ArrayList<>();
        Arrays.stream(aClass.getDeclaredMethods()).forEach(method -> {
            if(method.getAnnotation(YwMethod.class) == null)
                return;
            methodParams.setMethod(method);
            List<String> params = new ArrayList<>();
            Arrays.stream(method.getParameters()).forEach(parameter -> {
                YwParams ywParams = parameter.getAnnotation(YwParams.class);
                if(ywParams == null)
                    return;
                params.add(ywParams.value());
            });
            methodParams.setParams(params);
        });
        methodParamsList.add(methodParams);
        return methodParamsList;
    }

    /**
     * 获取对应的方法并执行该方法
     * @param classLoader        classloader
     * @param classAnnotation    类注释value
     * @param methodAnnotation   方法注释value
     */
    public void scanMethodAndDoIt(ClassLoader classLoader,String classAnnotation,String methodAnnotation,Map<String,?> paramsList) throws Exception {
        List<String> pkgNames = scanPackageNames(classLoader,"application.properties","YwAz");
        Class aClass = scanClass(classLoader,YwAz.class,classAnnotation,pkgNames);
        Arrays.stream(aClass.getDeclaredMethods()).forEach(method -> {
            YwMethod ywMethod = method.getAnnotation(YwMethod.class);
            if(ywMethod == null)
                return;
            if(!ywMethod.name().equals(methodAnnotation))
                return;
            Object instance = null;
            try {
                instance = aClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
            Object[] _params = new Object[method.getParameterTypes().length];
            Parameter[] parameters = method.getParameters();
            Set<String> sets = paramsList.keySet();
            for (int i = 0;i < parameters.length; i++){
                YwParams ywParams = parameters[i].getAnnotation(YwParams.class);
                if (ywParams == null)
                    continue;
                for(String key : sets){
                    if (key.equals(ywParams.value())) {
                        _params[i] = paramsList.get(key);
                        break;
                    }
                }
            }
            try {
                method.invoke(instance,_params);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        });
    }

    private boolean pkgExist(List<String> pkgNames, Class aClass) {
        if (pkgNames == null || pkgNames.size() == 0)
            return true;
        String classPkgName = aClass.getCanonicalName();
        for (String pkgName : pkgNames) {
            if (classPkgName.startsWith(pkgName))
                return true;
        }
        return false;
    }

    private boolean isTypeInterface(Class aClass, Class classType) {
        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(aClass);
        for (Class<?> i : interfaces) {
            if (i.getCanonicalName().equals(classType.getCanonicalName()))
                return true;
        }
        return false;
    }
}

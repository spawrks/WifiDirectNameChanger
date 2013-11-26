package com.spawrks.wifidirecttest;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Allow the access to the private parts of objects and classes.
 * 
 * @author llowenthal, dkogan, edelvalle
 */
public final class ReflectionUtils {

    /**
     * Private constructor.
     */
    private ReflectionUtils() {

    }

    /**
     * Call a private method without arguments.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param declaringClass
     *            Class of the Object owner of the private field
     * @param methodName
     *            name of the method to call
     * @return return of the method execution
     */
    public static Object executePrivateMethod(final Object targetObject, final Class<?> declaringClass,
            final String methodName) {
        return executePrivateMethod(targetObject, declaringClass, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * Call a private method.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param declaringClass
     *            Class of the Object owner of the private field
     * @param methodName
     *            name of the method to call
     * @param argsTypes
     *            types of the arguments of the method to call
     * @param args
     *            arguments of the method to call
     * @return return of the method execution
     */
    public static Object executePrivateMethod(final Object targetObject, final Class<?> declaringClass,
            final String methodName, final Class<?>[] argsTypes, final Object[] args) {
        Object result;
        try {
            Method method = declaringClass.getDeclaredMethod(methodName, argsTypes);
            method.setAccessible(true);
            result = method.invoke(targetObject, args);
            method.setAccessible(false);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
        return result;
    }

    /**
     * Call a private method without arguments.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param methodName
     *            name of the method to call
     * @return return of the method execution
     */
    public static Object executePrivateMethod(final Object targetObject, final String methodName) {
        return executePrivateMethod(targetObject, targetObject.getClass(), methodName, new Class[] {}, new Object[] {});
    }

    /**
     * Call a private method.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param methodName
     *            name of the method to call
     * @param argsTypes
     *            types of the arguments of the method to call
     * @param args
     *            arguments of the method to call
     * @return return of the method execution
     */
    public static Object executePrivateMethod(final Object targetObject, final String methodName,
            final Class<?>[] argsTypes, final Object[] args) {
        return executePrivateMethod(targetObject, targetObject.getClass(), methodName, argsTypes, args);
    }

    /**
     * Call a private static method without arguments.
     * 
     * @param clazz
     *            Class owner of the private method
     * @param methodName
     *            name of the method to call
     * @return return of the method execution
     */
    public static Object executePrivateStaticMethod(final Class<?> clazz, final String methodName) {
        return executePrivateStaticMethod(clazz, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * Call a private static method.
     * 
     * @param clazz
     *            Class owner of the private method
     * @param methodName
     *            name of the method to call
     * @param argsTypes
     *            types of the arguments of the method to call
     * @param args
     *            arguments of the method to call
     * @return return of the method execution
     */
    public static Object executePrivateStaticMethod(final Class<?> clazz, final String methodName,
            final Class<?>[] argsTypes, final Object[] args) {
        Object result;
        try {
            Method method = clazz.getDeclaredMethod(methodName, argsTypes);
            method.setAccessible(true);
            result = method.invoke(null, args);
            method.setAccessible(false);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
        return result;
    }

    /**
     * Returns a private field.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param declaringClass
     *            Class of the Object owner of the private field
     * @param fieldName
     *            name of the field
     * @return the private field object
     */
    public static Object getPrivateField(final Object targetObject, final Class<?> declaringClass,
            final String fieldName) {
        Object result;
        try {
            Field propertyField = declaringClass.getDeclaredField(fieldName);
            propertyField.setAccessible(true);
            result = propertyField.get(targetObject);
            propertyField.setAccessible(false);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
        return result;
    }

    /**
     * Returns a private field.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param fieldName
     *            name of the field
     * @return the private field object
     */
    public static Object getPrivateField(final Object targetObject, final String fieldName) {
        return getPrivateField(targetObject, targetObject.getClass(), fieldName);
    }

    /**
     * Returns a private static field.
     * 
     * @param clazz
     *            the class that owns the field
     * @param fieldName
     *            the name of the field
     * @return the value of the private static field
     */
    public static Object getPrivateStaticField(final Class<?> clazz, final String fieldName) {
        Object result;
        try {
            Field propertyField = clazz.getDeclaredField(fieldName);
            propertyField.setAccessible(true);
            result = propertyField.get(null);
            propertyField.setAccessible(false);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
        return result;
    }

    /**
     * Set a private field.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param declaringClass
     *            The class that declares the field
     * @param fieldName
     *            name of the field to be set
     * @param value
     *            value to set on the private field
     */
    public static void setPrivateField(final Object targetObject, final Class<?> declaringClass,
            final String fieldName, final Object value) {
        try {
            Field propertyField = declaringClass.getDeclaredField(fieldName);
            propertyField.setAccessible(true);
            propertyField.set(targetObject, value);
            propertyField.setAccessible(false);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Set a private field.
     * 
     * @param targetObject
     *            Object owner of the private field
     * @param fieldName
     *            name of the field to be set
     * @param value
     *            value to set on the private field
     */
    public static void setPrivateField(final Object targetObject, final String fieldName, final Object value) {
        setPrivateField(targetObject, targetObject.getClass(), fieldName, value);
    }

    /**
     * Set a private static field.
     * 
     * @param clazz
     *            the Class owner of the private field
     * @param fieldName
     *            name of the field to be set
     * @param value
     *            value to set on the private field
     */
    public static void setStaticPrivateField(final Class<?> clazz, final String fieldName, final Object value) {
        try {
            Field propertyField = clazz.getDeclaredField(fieldName);
            propertyField.setAccessible(true);
            propertyField.set(null, value);
            propertyField.setAccessible(false);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public static Object getInstanceFromPrivateEmptyConstructor(final Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            constructor.setAccessible(false);
            return instance;
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

}

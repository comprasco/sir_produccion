/*
 * Created on Apr 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.core.is21;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;

/**
 * @author gardila
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Introspector {
	
	/**
     * Creates a new object of the class name. <br>
     * Si la constructora recibe parametros utilice: <br>
     * Object constuirObjeto(String nombre, Object[] parametros) <br>
     *
     * @param className
     *            name of the class to build.
     * @return new Object instance of class name.
     * @throws IntrospectorException
     *             if the build process fails
     */
    public static Object buildObject(String className) throws IntrospectorException {
        if (className == null) {
            throw new IntrospectorException("The class name cannot be null");
        }

        Object obj = null;
        Class clase = null;

        try {
            clase = Class.forName(className);
            obj = clase.newInstance();
        } 
        catch (InstantiationException e) {
            throw new IntrospectorException(
            		Introspector.exceptionBuildObjectMessage(e, className, null, null), e);
        } 
        catch (IllegalAccessException e) {
            throw new IntrospectorException(
            		Introspector.exceptionBuildObjectMessage(e, className, null, null), e);
        } 
        catch (ClassNotFoundException e) {
            throw new IntrospectorException(
            		Introspector.exceptionBuildObjectMessage(e, className, null, null), e);
        } 
        catch (ExceptionInInitializerError e) {
            throw new IntrospectorException(e.getCause());
        } 
        catch (RuntimeException e) {
            throw new IntrospectorException(
            		Introspector.exceptionBuildObjectMessage(e, className, null, null), e);
        } 
        catch (Throwable e) {
            throw new IntrospectorException(e);
        }

        return obj;
    }

    /**
     * Creates a new object of the class name, using a constructor with
     * parameters. <br>
     *
     * @param className
     *            name of the class to build.
     * @param parameterTypes
     *            class types of the parameters
     * @param parameters
     *            parameters needed by the constructor
     * @return new Object instance of class name.
     * @throws IntrospectorException
     *             if the build process fails
     */
    public static Object buildObject(String className, Class[] parameterTypes,
        Object[] parameters) throws IntrospectorException {
        Object obj = null;

        if (className == null) {
            throw new IntrospectorException("The paremeter className cannot be null");
        }

        Class clase = null;
        Constructor cons = null;

        try {
            if (parameters == null) {
                return Introspector.buildObject(className);
            }

            clase = Class.forName(className);
            cons = clase.getConstructor(parameterTypes);
            obj = cons.newInstance(parameters);
        } 
        catch (SecurityException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionBuildObjectMessage(
                    e, className, parameterTypes, parameters), e);
        } 
        catch (IllegalArgumentException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionBuildObjectMessage(
                    e, className, parameterTypes, parameters), e.getCause());
        } 
        catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionBuildObjectMessage(
                    e, className, parameterTypes, parameters), e);
        } 
        catch (NoSuchMethodException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionBuildObjectMessage(
                    e, className, parameterTypes, parameters), e);
        } 
        catch (InstantiationException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionBuildObjectMessage(
                    e, className, parameterTypes, parameters), e);
        } 
        catch (IllegalAccessException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionBuildObjectMessage(
                    e, className, parameterTypes, parameters), e);
        } 
        catch (InvocationTargetException e) {
            //e.printStackTrace();
            throw new IntrospectorException(e.getTargetException());
        } 
        catch (RuntimeException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionBuildObjectMessage(
                    e, className, parameterTypes, parameters), e);
        } 
        catch (Throwable e) {
            //e.printStackTrace();
            throw new IntrospectorException(e);
        }

        return obj;
    }

    /**
     * This method is used to invoke a method of an object or class.
     * <p>
     * Eaxmple: invoke the method String.charAt(int); <br>
     * 1. Define types of the parameters: <br>
     * Class types [] = new Class[]{ Integer.TYPE };<br>
     * 2. Define the parameter objects: <br>
     * Object parameters [] = new Object[]{ new Integer(7) };<br>
     * </p>
     *
     * @param obj
     *            The executing object. It can be null, only if the method to be
     *            executed is static. <br>
     *            Example: <br>
     *            Calendar.getInstance()
     * @param objectClass
     *            Class of the executing object.
     * @param methodName
     *            name of the method to be invoked
     * @param parameterTypes
     *            Class [] with the parameter types
     * @param parameters
     *            Object [] with the parameters. it can be null only if the
     *            method doesn´t receive parameters For primitive types use
     *            Wrapper clases (Integer, Float, Double, Char, Boolean ... )
     * @return returns an object with the return of the invoked object
     * @throws IntrospectorException
     *             If the process fails.
     */
    public static Object executeMethod(Object obj, Class objectClass,
        String methodName, Class[] parameterTypes, Object[] parameters)
        throws IntrospectorException {
    	
        if (objectClass == null) {
            throw new IntrospectorException(
                "The objectClass parameter cannot be null");
        }

        try {
            Method met = objectClass.getMethod(methodName, parameterTypes);

            met.setAccessible(true);

            return met.invoke(obj, parameters);
        } 
        catch (NoSuchMethodException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionExecuteMethodMessage(
                    e, objectClass, methodName, parameterTypes, parameters), e);
        } 
        catch (InvocationTargetException e) {
            //e.printStackTrace();
            throw new IntrospectorException(e.getTargetException());
        } 
        catch (IllegalAccessException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionExecuteMethodMessage(
                    e, objectClass, methodName, parameterTypes, parameters), e);
        } 
        catch (IllegalArgumentException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionExecuteMethodMessage(
                    e, objectClass, methodName, parameterTypes, parameters),
                e.getCause());
        } 
        catch (RuntimeException e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionExecuteMethodMessage(
                    e, objectClass, methodName, parameterTypes, parameters), e);
        } 
        catch (Throwable e) {
            //e.printStackTrace();
            throw new IntrospectorException(Introspector.exceptionExecuteMethodMessage(
                    e, objectClass, methodName, parameterTypes, parameters), e);
        }
    }

    /**
     * Only use this method if you're complety sure the method doesn't have
     * Interface types or primitive types in the parameters. Otherwise use: <br>
     *
     * executeMethod(Object obj, Class objectclass, String methodName, Class
     * parameterTypes[], Object parameters[]) <br>
     *
     * @param obj
     *            The executing object. It can be null, only if the method to be
     *            executed is static. <br>
     *            Example: <br>
     *            Calendar.getInstance()
     * @param objectClass
     *            Class of the executing object.
     * @param methodName
     *            name of the method to be invoked
     * @param parameters
     *            Object [] with the parameters. it can be null only if the
     *            method doesn´t receive parameters.
     * @return returns an object with the return of the invoked object
     * @throws IntrospectorException
     *             If the process fails.
     */
    public static Object executeMethod(Object obj, Class objectClass,
        String methodName, Object[] parameters) throws IntrospectorException {
        Class[] tipos = Introspector.parameterTypes(parameters);

        return Introspector.executeMethod(obj, objectClass, methodName, tipos,
            parameters);
    }

    /**
     * @param parameters
     *            Object Array
     * @return returns an Object array with the class types of the parameter
     *         Array
     */
    private static Class[] parameterTypes(Object[] parameters) {
        Class[] types = new Class[] {  };

        if (parameters == null) {
            return types;
        }

        types = new Class[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Class clase = null;

            if (parameters[i] != null) {
                clase = parameters[i].getClass();
            }

            types[i] = clase;
        }

        return types;
    }

    private static String toString(Object[] array) {
        if (array == null) {
            return "";
        }

        List list = Arrays.asList(array);

        return list.toString();
    }

    private static String exceptionExecuteMethodMessage(Throwable e,
        Class objectClass, String methodName, Class[] parameterTypes,
        Object[] parameters) {
        String name;
        StringBuffer message = new StringBuffer();

        name = e.getClass().getName();

        if (name == null) {
            name = "";
        }

        message.append(name);
        message.append(" ocurred when trying to invoke: \n\t'");
        message.append(methodName);
        message.append("' method of the object of type class: '");
        message.append(objectClass.toString());
        message.append("'\n\t with parameterTypes: ");
        message.append(Introspector.toString(parameterTypes));
        message.append("\n\t and parameters: ");
        message.append(Introspector.toString(parameters));

        return message.toString();
    }

    private static String exceptionBuildObjectMessage(Throwable e,
        String className, Class[] parameterTypes, Object[] parameters) {
        String name;
        StringBuffer message = new StringBuffer();

        name = e.getClass().getName();

        if (name == null) {
            name = "";
        }

        message.append(name);
        message.append(" ocurred when trying to instantiate ");
        message.append("an object of class: \n\t'");
        message.append(className);
        message.append("' using a constructor\n\t with parameterTypes: ");
        message.append(Introspector.toString(parameterTypes));
        message.append("\n\t and parameters: ");
        message.append(Introspector.toString(parameters));

        return message.toString();
    }
}
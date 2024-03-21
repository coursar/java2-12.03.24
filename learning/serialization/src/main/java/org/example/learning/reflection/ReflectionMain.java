package org.example.learning.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionMain {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        // TODO: Class -> description of loaded class
        //  1. Class.forName
        //  2. Patient.class
        //  3. new Patient().getClass()

        {
            Class<?> clazz = Class.forName("org.example.learning.reflection.Patient"); // FQN
            // TODO:
            //  1. New Instance
            //  2. Call methods, write/read fields
            //  3. Analyze class (methods, fields, ...)

            // java 8-
            // Object patient = clazz.newInstance(); // no-args constructor <- most serialization library likes it
            // TODO: regular code Clazz.callConstructor -> new Clazz
            // TODO: reflection (mirror) Constructor.newInstance() -> new Clazz
            Object patient = clazz.getConstructors()[0].newInstance(); // no-args constructor <- most serialization library likes it

            // TODO: object.method()
            // TODO: reflection (mirror) Method.invoke(object)
            Method getNonSecretMethod = clazz.getDeclaredMethod("getNonSecret");
            Object result = getNonSecretMethod.invoke(patient);
            System.out.println("result = " + result);

            Method getSecretMethod = clazz.getDeclaredMethod("getSecret");
            getSecretMethod.setAccessible(true);
            Object resultNonPublic = getSecretMethod.invoke(patient);
            System.out.println("resultNonPublic = " + resultNonPublic);

            Field secretField = clazz.getDeclaredField("secret");
            secretField.setAccessible(true);
            Object oldValue = secretField.get(patient);
            System.out.println("oldValue = " + oldValue);

            secretField.set(patient, "top secret");
            Object newValue = secretField.get(patient);
            System.out.println("newValue = " + newValue);
        }
        {
            Class<Patient> clazz = Patient.class; // cls, aClass
        }
        {
            Patient patient = new Patient();
            Class<? extends Patient> clazz = patient.getClass();
        }
    }
}

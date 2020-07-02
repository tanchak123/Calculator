import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StartTest {
    private static final Panel PANEL = new Panel();

    @Test
    public void setSpace() {
        try {
            Method method = createMethod("setSpace");
            Assert.assertEquals("123 * 123", method.invoke(PANEL, "123*123"));
            Assert.assertEquals("123 123 123 * 123 123", method.invoke(PANEL, "123123123*123123"));
            Assert.assertEquals("12 312 312 * 123 123", method.invoke(PANEL, "12312312*123123"));
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getResult() {
        try {
            Method method = createMethod("getResult");
            Assert.assertEquals("2", method.invoke(PANEL, "1+1"));
            Assert.assertEquals("1.5", method.invoke(PANEL, "1*1+0.5"));
            Assert.assertEquals("8.653", method.invoke(PANEL, "1*2.5+0.5+3.123+2.53"));
            Assert.assertEquals("1021", method.invoke(PANEL, "1021 * 12"));
            Assert.assertEquals("5.4183101731432512E17", method.invoke(PANEL, "23123123*23432432432"));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Method createMethod(String name) {

        Method method = null;
        try {
            method = PANEL.getClass().getDeclaredMethod(name, String.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }
}
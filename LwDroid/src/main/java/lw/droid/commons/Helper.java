package lw.droid.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import lw.droid.forms.MessageBox;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class Helper {

	public static boolean isStringEmpty(String str) {
		return str == null || str.equals("");
	}

	public static boolean setProperty(Object object, String fieldName,
			Object fieldValue) {
		Class<?> clazz = object.getClass();
		String mname = "set" + fieldName;
		try {
			for (Method m : clazz.getMethods()) {
				if (m.getName().equalsIgnoreCase(mname)) {
					m.invoke(object, fieldValue);
					return true;
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public static <E> E getProperty(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return (E) field.get(object);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return null;
	}

	public static void showError(Context ctx, String tag, Throwable error) {
		Log.e(tag, error.getMessage(), error);
		MessageBox.showError(ctx, error);
	}

	
}

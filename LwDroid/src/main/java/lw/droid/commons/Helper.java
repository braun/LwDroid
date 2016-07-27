package lw.droid.commons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import lw.droid.LwApplication;
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

	public interface FieldSource {
		String getFieldVal(String fname, String defVal);
	}

	public static String replaceFields(String pattern, FieldSource source, String defval) {
		boolean literal = !pattern.startsWith("%");
		String[] segments = split(pattern, "%");
		StringBuilder rv = new StringBuilder();

		for (String segment : segments) {
			String apendee = segment;

			if (!literal) {
				String[] sgs = split(segment, "|");
				if (source == null)
					apendee = defval;
				else
					apendee = source.getFieldVal(sgs[sgs.length - 1], defval);
				if (!isStringEmpty(apendee) && sgs.length > 1)
					rv.append(sgs[0]);
			}
			rv.append(apendee);
			literal = !literal;
		}

		return rv.toString();
	}

	public static String[] split(String strString, String strDelimiter) {
		int iOccurrences = 0;
		int iIndexOfInnerString = 0;
		int iIndexOfDelimiter = 0;
		int iCounter = 0;

		// Check for null input strings.
		if (strString == null) {
			throw new NullPointerException("Input string cannot be null.");
		}
		// Check for null or empty delimiter
		// strings.
		if (strDelimiter.length() <= 0 || strDelimiter == null) {
			throw new NullPointerException("Delimeter cannot be null or empty.");
		}

		// If strString begins with delimiter
		// then remove it in
		// order
		// to comply with the desired format.

		if (strString.startsWith(strDelimiter)) {
			strString = strString.substring(strDelimiter.length());
		}

		// If strString does not end with the
		// delimiter then add it
		// to the string in order to comply with
		// the desired format.
		if (!strString.endsWith(strDelimiter)) {
			strString += strDelimiter;
		}

		// Count occurrences of the delimiter in
		// the string.
		// Occurrences should be the same amount
		// of inner strings.
		while ((iIndexOfDelimiter = strString.indexOf(strDelimiter, iIndexOfInnerString)) != -1) {
			iOccurrences += 1;
			iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();
		}

		// Declare the array with the correct
		// size.
		String[] strArray = new String[iOccurrences];

		// Reset the indices.
		iIndexOfInnerString = 0;
		iIndexOfDelimiter = 0;

		// Walk across the string again and this
		// time add the
		// strings to the array.
		while ((iIndexOfDelimiter = strString.indexOf(strDelimiter, iIndexOfInnerString)) != -1) {

			// Add string to
			// array.
			strArray[iCounter] = strString.substring(iIndexOfInnerString, iIndexOfDelimiter);

			// Increment the
			// index to the next
			// character after
			// the next
			// delimiter.
			iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();

			// Inc the counter.
			iCounter += 1;
		}
		return strArray;
	}

	public static String loadStringFromStream(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try {
			int len = 0;
			while (-1 != (len = is.read(buffer, 0, buffer.length))) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			return new String(bos.toByteArray(),"UTF-8");
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
			if (bos != null) {
				bos.close();
				bos = null;
			}
		}
	}
}

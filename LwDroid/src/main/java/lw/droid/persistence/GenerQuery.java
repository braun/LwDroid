package lw.droid.persistence;

public @interface GenerQuery {
		String where();
		String orderBy() default "";
		
}

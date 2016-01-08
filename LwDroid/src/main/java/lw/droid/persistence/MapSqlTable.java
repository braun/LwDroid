/**
 * 
 */
package lw.droid.persistence;

/**
 * @author Standa
 *
 */
public @interface MapSqlTable {
	String table() default "auto";
	String database() default "default";
}

package uk.ac.ncl.cs.groupproject.interceptor;

import java.lang.annotation.*;

/**
 * @author ZequnLi
 *         Date: 14-2-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}

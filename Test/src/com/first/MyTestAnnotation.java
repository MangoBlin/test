package com.first;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyTestAnnotation {
    String value();
}

package com.first;

import java.lang.annotation.*;

/**定义限额注解*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BankTransferMoney {
    double maxMoney() default 10000;
}








































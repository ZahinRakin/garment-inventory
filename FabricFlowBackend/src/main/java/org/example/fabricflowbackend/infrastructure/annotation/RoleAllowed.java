package org.example.fabricflowbackend.infrastructure.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAllowed {
    String[] value(); // e.g., {"ADMIN", "SALES_OFFICER"}
}

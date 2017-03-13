package com.ljy.librarymanager.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by jiayu on 2017/3/11.
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}
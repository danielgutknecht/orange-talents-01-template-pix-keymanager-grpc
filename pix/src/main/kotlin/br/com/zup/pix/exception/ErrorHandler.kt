package br.com.zup.pix.exception

import io.micronaut.aop.Around
import io.micronaut.aop.Introduction
import io.micronaut.context.annotation.Type
import io.micronaut.core.annotation.Internal

@Retention(AnnotationRetention.RUNTIME)
@Introduction
@Type(ExceptionHandlerInterceptor::class)
@Internal
@Around
annotation class ErrorHandler

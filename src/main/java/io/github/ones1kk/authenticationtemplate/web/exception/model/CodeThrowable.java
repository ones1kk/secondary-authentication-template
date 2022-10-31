package io.github.ones1kk.authenticationtemplate.web.exception.model;

public interface CodeThrowable<SELF extends Throwable & CodeThrowable<SELF, T>, T> {

    T getCode();
}

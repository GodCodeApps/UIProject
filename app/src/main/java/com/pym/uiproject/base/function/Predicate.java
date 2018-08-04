package com.pym.uiproject.base.function;

/**
 * T -> boolean
 *
 * @author gavin.xiong 2017/8/10
 */
@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);
}

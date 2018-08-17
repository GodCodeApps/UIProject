package com.pym.uiproject.base.function;

/**
 * T -> Void
 *
 * @author Peng YanMing 2017/8/10
 */
@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);
}

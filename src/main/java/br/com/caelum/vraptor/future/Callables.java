package br.com.caelum.vraptor.future;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;

class Callables {

	static <T> Class getReturnTypeFor(Callable<T> callable) {
		Type[] interfaces = callable.getClass().getGenericInterfaces();
		for (Type type : interfaces) {
			if(type instanceof ParameterizedType) {
				ParameterizedType t = (ParameterizedType) type;
				if(t.getRawType().equals(Callable.class)) {
					Type argument = t.getActualTypeArguments()[0];
					if(argument instanceof ParameterizedType) {
						return (Class) ((ParameterizedType) argument).getRawType();
					}
					return (Class) argument;
				}
			}
		}
		throw new RuntimeException("Your callable does not define its return type? " + callable.getClass());
	}
}

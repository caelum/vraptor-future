package br.com.caelum.vraptor.future;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;

/**
 * Allows the program to execute or include variables to the request by
 * executing the code in a new thread.
 * 
 * @author guilherme silveira
 * @author rodrigo turini
 */
@Component
public class Async {


	private final List<FutureCallback> callbacks;
	private final List<Future<?>> tasks = new ArrayList<>();

	private final HttpServletRequest request;

	private final Proxifier profixier;
	private final AsyncPool pool;


	public Async(List<FutureCallback> callbacks, HttpServletRequest request,
			Proxifier profixier, AsyncPool pool) {
		this.callbacks = callbacks;
		this.request = request;
		this.profixier = profixier;
		this.pool = pool;
	}

	public <T> Async execute(Callable<T> callable) {
		FutureTask<T> task = new FutureTask<T>(new CallbackableTask<T>(
				callable, callbacks));
		pool.run(task);
		return this;
	}

	public <T> Async execute(Runnable... runnables) {
		for (Runnable runnable : runnables) {
			execute(runnable);
		}
		return this;
	}

	public Async execute(Callable<?>... cbs) {
		for (Callable<?> cb : cbs) {
			execute(cb);
		}
		return this;
	}

	public <T> Async execute(final Runnable runnable) {
		execute(Executors.callable(runnable));
		return this;
	}

	public <T> void include(String key, Callable<T> callable) {
		CallbackableTask<T> callbackable = new CallbackableTask<T>(callable,
				callbacks);
		final Future<T> task = pool.submit(callbackable);
		tasks.add(task);

		MethodInvocation handler = new  MethodInvocation() {
			@Override
			public Object intercept(Object arg0, Method m, Object[] args,
					SuperMethod method) {
				try {
					Object obj = task.get();
					Object result = m.invoke(obj, args);
					return result;
				} catch (InterruptedException | ExecutionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
			
		};

		Class type = Callables.getReturnTypeFor(callable);

		request.setAttribute(key, this.profixier.proxify(type, handler));
	}

	void waitUntilFinished() throws InterruptedException, ExecutionException {
		for (Future<?> task : tasks) {
			task.get();
		}
	}
	
}

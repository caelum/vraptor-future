package br.com.caelum.vraptor.future;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * A task that ensures all callbacks were invoked after the task finishes.
 * 
 * @author guilherme silveira
 * @author rodrigo turini
 * @param <T>
 */
public class CallbackableTask<T> implements Callable<T> {

	private final Callable<T> callable;
	private final List<FutureCallback> callbacks;

	/**
	 * The callable to invoke and all callbacks to invoke after its finished.
	 * 
	 * @param callable
	 * @param callbacks
	 */
	public CallbackableTask(Callable<T> callable, List<FutureCallback> callbacks) {
		this.callable = callable;
		this.callbacks = callbacks;
	}

	/**
	 * Starts the callable and when finished invoke all callbacks. This is a *sync* operation.
	 */
	@Override
	public T call() throws Exception {
		T result = callable.call();
		for (FutureCallback callback : callbacks) {
			callback.run();
		}
		return result;
	}

}

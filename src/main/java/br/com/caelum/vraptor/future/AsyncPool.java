package br.com.caelum.vraptor.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import br.com.caelum.vraptor.ioc.ApplicationScoped;

@ApplicationScoped
public class AsyncPool {

	private final ExecutorService executors;

	public AsyncPool(AsyncConfig config) {
		this.executors = Executors
				.newFixedThreadPool(config.getPoolSize());
	}
	
	public <T> void run(FutureTask<T> cb) {
		executors.execute(cb);
	}
	
	public <T> Future<T> submit(CallbackableTask<T> cb) {
		return executors.submit(cb);
	}

}

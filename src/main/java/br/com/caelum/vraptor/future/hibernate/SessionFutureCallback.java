package br.com.caelum.vraptor.future.hibernate;

import br.com.caelum.vraptor.future.FutureCallback;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class SessionFutureCallback implements FutureCallback {

	private final FutureSessionProvider provider;

	SessionFutureCallback(FutureSessionProvider provider) {
		this.provider = provider;
	}

	@Override
	public void run() {
		if(shouldAllowOpenSessionInView()) {
			provider.freeThreadLocal();
			provider.finished();
		}
	}

	/**
	 * Whether the session should be left open until the view rendering process
	 * is finished. Set this to false and run your integration tests if you want
	 * to optimize your code.
	 */
	protected boolean shouldAllowOpenSessionInView() {
		return true;
	}

}

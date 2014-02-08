package br.com.caelum.vraptor.future.hibernate;

import br.com.caelum.vraptor.future.FutureCallback;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class SessionFutureCallback<T> implements FutureCallback {
	
	private final FutureSessionProvider provider;

	SessionFutureCallback(FutureSessionProvider provider) {
		this.provider = provider;
	}

	@Override
	public void run() {
//		tenh oque fazer algo aqui.... liberar ela para A MESMA THREAD!!! ASSIM SE O HIBRENATE PRECISAR, VOCE AINDA CONSEGUE COM ESSE CARA!
//		merda...
		
		// System.out.println("Session future callback for " + provider);
//		provider.finished();
	}

}

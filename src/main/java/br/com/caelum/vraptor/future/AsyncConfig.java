package br.com.caelum.vraptor.future;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@ApplicationScoped
@Component
public class AsyncConfig {

	public int getPoolSize() {
		return 10;
	}

}

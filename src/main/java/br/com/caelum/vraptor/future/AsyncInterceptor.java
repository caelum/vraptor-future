package br.com.caelum.vraptor.future;

import java.util.concurrent.ExecutionException;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.ExecuteMethodInterceptor;
import br.com.caelum.vraptor.interceptor.ForwardToDefaultViewInterceptor;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

/**
 * Intercepts all requests and ensure that all futures are executed prior to
 * rendering the view.
 * 
 * @author guilherme silveira
 * @author rodrigo turini
 */
//@Intercepts(before = ForwardToDefaultViewInterceptor.class, after = ExecuteMethodInterceptor.class)
//public class AsyncInterceptor implements Interceptor {
//
//	private final Async result;
//
//	public AsyncInterceptor(Async result) {
//		this.result = result;
//	}
//
//	@Override
//	public boolean accepts(ResourceMethod resource) {
//		return true;
//	}
//
//	@Override
//	public void intercept(InterceptorStack stack, ResourceMethod method,
//			Object object) throws InterceptionException {
//		try {
//			result.waitUntilFinished();
//			stack.next(method, object);
//		} catch (InterruptedException | ExecutionException e) {
//			throw new InterceptionException(e);
//		}
//	}
//
//}
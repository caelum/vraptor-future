package br.com.caelum.vraptor.future.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
public class FutureSessionProvider implements ComponentFactory<Session> {

	private final ThreadLocal<Session> sessions = new ThreadLocal<>();
	private final SessionFactory factory;
	private final List<Session> allSessions = new ArrayList<>();

	public FutureSessionProvider(SessionFactory factory) {
		this.factory = factory;
	}

	protected Session getSessionForThisThread() {
		if (sessions.get() == null) {
			Session session = factory.openSession();
//			System.out.println("Opening session " + session.hashCode());
			allSessions.add(session);
			sessions.set(session);
			return session;
		}
		return sessions.get();
	}

	public void finished() {
		if (sessions.get() != null) {
			Session session = getSessionForThisThread();
//			System.out.println("Closing session " + this + " @ " + session.hashCode());
			session.close();
		}
	}

	@Override
	public Session getInstance() {
		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Session session = getSessionForThisThread();
				return method.invoke(session, args);
			}

		};
		return (Session) Proxy.newProxyInstance(
				FutureSessionProvider.class.getClassLoader(),
				new Class[] { Session.class }, handler);
	}


	@PreDestroy
	public void shutdown() {
		for(Session s : allSessions) {
//			System.out.println("Closing " + s.hashCode());
			s.close();
		}
	}

}

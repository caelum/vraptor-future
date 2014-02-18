VRaptor Future
==============

This library helps you to make full use of all resources in a machine where they are not fully used.
For example, if a machine hits 30% CPU usage and each request hits the database for 3 different queries,
you could execute the queries in parallel, achieve (i.e.) 90% CPU usage and cut down your request time (i.e.) by 1/3.

This library might help even in situations where the CPU (or database connections, or any other managed resource)
reaches 100% usage since it breaks sync tasks into multiple pieces.


Traditiona sync code
=========

		watcher.updateEnrollmentsIfFinished();
		result.include("subscription", enrollments.lastValidSubscription(loggedUser));
		result.include("enrollments", enrollments.getCurrentMonth(loggedUser));


Async Java 8+
=============


		async.execute(() -> watcher.updateEnrollmentsIfFinished());
		
		async.include("subscription", () -> enrollments.lastValidSubscription(loggedUser), Subscription.class);
		
		async.include("enrollments", () -> enrollments.getCurrentMonth(loggedUser), List.class);


Async java 7-	
=============	

		async.execute(new Runnable() {
			public void run() {
				watcher.updateEnrollmentsIfFinished();
			}
		});
		
		async.include("subscription", new Callable<Subscription>() {
			public Subscription call() throws Exception {
				return enrollments.lastValidSubscription(loggedUser);
			}
		});
		async.include("enrollments", new Callable<List<Enrollment>>() {
			public List<Enrollment> call() throws Exception {
				return enrollments.getCurrentMonth(loggedUser);
			}
		});

		
Pool Size
=================

To configure the pool size (default 10) override your AsyncConfig class:

@ApplicationScoped
@Component
public class MyConfig extends AsyncConfig {

	public int getPoolSize() {
		return 100;
	}

}


Hibernate Support
=================

Hibernate is supported by default using the OpenSessionInView pattern. If you want to optimize your code and
not allow any hibernate lazy loading, override the:

	@Component
	public class CustomSessionFutureCallback extends SessionFutureCallback {
	
		SessionFutureCallback(FutureSessionProvider provider) {
			super(provider);
		}
	
		protected boolean shouldAllowOpenSessionInView() {
			return false;
		}
	
	}

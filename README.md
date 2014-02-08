sync

		watcher.updateEnrollmentsIfFinished();
		result.include("subscription", enrollments.lastValidSubscription(loggedUser));
		result.include("enrollments", enrollments.getCurrentMonth(loggedUser));


async java 8+

		async.execute(() -> watcher.updateEnrollmentsIfFinished());
		
		async.include("subscription", () -> enrollments.lastValidSubscription(loggedUser), Subscription.class);
		
		async.include("enrollments", () -> enrollments.getCurrentMonth(loggedUser), List.class);


async java 7-		

		async.execute(new Runnable() {
			public void run() {
				watcher.updateEnrollmentsIfFinished();
			}
		});
		
		async.include("subscription", new Callable<Subscription>() {
			public Subscription call() throws Exception {
				return enrollments.lastValidSubscription(loggedUser);
			}
		}, Subscription.class);
		async.include("enrollments", new Callable<List<Enrollment>>() {
			public List<Enrollment> call() throws Exception {
				return enrollments.getCurrentMonth(loggedUser);
			}
		}, List.class);

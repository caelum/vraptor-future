package br.com.caelum.vraptor.future;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Test;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CallablesTest {
	
	class ReturnNothing implements Callable {
		@Override
		public String call() throws Exception {
			return null;
		}
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldComplainIfNotSpecified() {
		Callables.getReturnTypeFor(new ReturnNothing());
	}

	class ReturnString implements Callable<String> {
		@Override
		public String call() throws Exception {
			return null;
		}
	}
	
	@Test
	public void shouldReturnTheTypeItself() {
		Class type = Callables.getReturnTypeFor(new ReturnString());
		assertEquals(String.class, type);
	}


	class ReturnListString implements Callable<List<String>> {
		@Override
		public List<String> call() throws Exception {
			return null;
		}
	}
	
	@Test
	public void shouldReturnTheEvenIfGeneric() {
		Class type = Callables.getReturnTypeFor(new ReturnListString());
		assertEquals(List.class, type);
	}


	class ReturnList implements Callable<List> {
		@Override
		public List call() throws Exception {
			return null;
		}
	}
	
	@Test
	public void shouldReturnTheEvenIfNonSpecificGeneric() {
		Class type = Callables.getReturnTypeFor(new ReturnList());
		assertEquals(List.class, type);
	}
}

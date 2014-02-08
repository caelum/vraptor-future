package br.com.caelum.vraptor.future;

/**
 * A future callback. Every future callback existing in your system will be
 * invoked after each future has finished.
 * 
 * @author guilherme  silveira
 * @author rodrigo turini
 * 
 */
public interface FutureCallback {

	void run();

}

package de.jalin.maven;

public class SimpleException extends Exception {

	private static final long serialVersionUID = 1L;

	public SimpleException(final String error) {
		super(error);
	}
}

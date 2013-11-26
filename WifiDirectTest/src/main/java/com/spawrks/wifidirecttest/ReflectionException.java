package com.spawrks.wifidirecttest;


/**
 * Raised when an reflection related exception is thrown.
 * 
 * @author dkogan
 * @date Jul 3, 2008
 */
public class ReflectionException extends RuntimeException {
    /**
     * Uid.
     */
    private static final long serialVersionUID = -5110160439913415481L;

    /**
     * Constructor.
     * 
     * @param e
     *            exception thrown by <code>java.lang.reflection</code>.
     */
    public ReflectionException(final Exception e) {
        super(e);
    }

}

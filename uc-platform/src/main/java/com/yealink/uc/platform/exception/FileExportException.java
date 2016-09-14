/**
 * 
 */
package com.yealink.uc.platform.exception;

/**
 * @author yl0167
 * 
 */
public class FileExportException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4302723223398641196L;

	public FileExportException(Exception exc) {
		super(exc.getMessage(), exc.getCause());
	}
}

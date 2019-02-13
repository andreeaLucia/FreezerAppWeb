package freezer.model;

import com.mysql.cj.util.StringUtils;

public class ExceptionName extends Exception{

	/**
	 * 
	 */
	private String messages;
	private static final long serialVersionUID = 1L;
	public ExceptionName(String message) {
		super(message);
	  
	}

}

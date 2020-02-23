/* Written by Shivansh Anand
 * types :
 * 			num = number
 * 			opr = operand
 * 			bo = bracket open
 * 			bc = bracket close
 */

public class Token {
	
	private String val;
	private String type;
	
	Token(String val, String type) {
		this.val = val;
		this.type = type;
	}
	
	public String getVal() {
		return val;
	}
	
	public String getType() {
		return type;
	}

}

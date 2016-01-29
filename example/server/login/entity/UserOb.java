/**
 * 
 */
package server.login.entity;

import ovar.Variable;
import server.login.observer.AddLimit;

/**
 * @author Xiong
 * 可观察对象User
 */
public class UserOb {
	public Variable<String> password = new Variable<String>();
	
	public UserOb(String username){
		password.init("").subscribe(new AddLimit(username));
	}
}

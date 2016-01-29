/**
 * 
 */
package server.login.handler;

import server.RequestContext;
import server.login.entity.UserPo;
import server.login.service.LoginService;
import server.net.ErrorCode;
import server.net.NetDown;
import server.net.NetDownChangePwd;

/**
 * @author Xiong
 *
 */
public class ChangePassword {
	public static NetDownChangePwd handle(String username, String oldPwd, String newPwd){
		try{
			NetDownChangePwd down = new NetDownChangePwd();
			UserPo dbUser = LoginService.getUserPo(username);
			if (dbUser == null){
				return NetDown.returnError(ErrorCode.USER_NOT_FOUND);
			}
			
			if (dbUser.limit <= 0){
				return NetDown.returnError(ErrorCode.OUT_OF_LIMIT);
			}
			
			if (!dbUser.password.equals(oldPwd)){
				return NetDown.returnError(ErrorCode.VERIFY_FAILED);
			}
			
			if (oldPwd.equals(newPwd)){
				return NetDown.returnError(ErrorCode.PWD_INVALID);
			}
	
			dbUser.password = newPwd;
			LoginService.updateUserPo(dbUser);
			return down;
		}catch (Exception e){
			return NetDown.returnError(ErrorCode.SERVER_EXCEPTION);
		}finally{
			RequestContext.clear();
		}
	}
}

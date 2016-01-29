/**
 * 
 */
package server.login.observer;

import static org.junit.Assert.assertTrue;
import ovar.AbstractObserver;
import server.RequestContext;
import server.login.entity.UserPo;
import server.login.service.LoginService;
import server.net.NetDown;
import server.util.DataKey;

/**
 * @author Xiong
 * 增加密码的限制次数
 */
public class AddLimit extends AbstractObserver<String, String> {

	public AddLimit(String ctx) {
		super(ctx);
	}

	@Override
	public void onNext(String ctx, String oldValue, String newValue) {
		UserPo dbUser = LoginService.getUserPo(ctx);
		assertTrue(dbUser != null);
		
		dbUser.limit--;
		NetDown down = RequestContext.get(DataKey.NET_DOWN.getKey());
		assertTrue(down != null);
		down.addProp("user.limit", String.valueOf(dbUser.limit));
		LoginService.updateUserPo(dbUser);
	}

}

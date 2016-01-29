/**
 * 
 */
package server.login.handler;

import java.util.ArrayList;
import java.util.List;

import server.RequestContext;
import server.ConfigData;
import server.equip.entity.EquipPo;
import server.equip.service.EquipService;
import server.login.entity.UserPo;
import server.login.service.LoginService;
import server.net.ErrorCode;
import server.net.NetDown;
import server.net.NetDownLogin;
import server.player.entity.PlayerPo;
import server.player.service.PlayerService;

/**
 * @author Xiong
 * 玩家登录请求处理
 */
public class Login {
	public static NetDownLogin handle(String username, String password){
		try{
			NetDownLogin down = new NetDownLogin();
			// 从db查找用户
			UserPo dbUser = LoginService.getUserPo(username);
			if (dbUser == null){
				// 注册
				long uid = LoginService.generateUid();
				
				dbUser = new UserPo();
				dbUser.username = username;
				dbUser.password = password;
				dbUser.limit = ConfigData.initLimit;
				dbUser.uid = uid;
				LoginService.addUserPo(dbUser);
				
				// player初始数据
				PlayerPo dbPly = new PlayerPo();
				dbPly.uid = uid;
				dbPly.nickname = dbUser.username;
				dbPly.coin = ConfigData.initCoin;
				PlayerService.addPlayerPo(dbPly);
				
				// 初始装备
				List<EquipPo> equips = new ArrayList<EquipPo>(ConfigData.initEquipTypes.size());
				for (byte type : ConfigData.initEquipTypes){
					EquipPo equip = new EquipPo();
					equip.id = EquipService.generateEid();
					equip.uid = uid;
					equip.type = type;
					equips.add(equip);
				}
				EquipService.addAllEquips(uid, equips);
				
				down.setDbPly(dbPly);
				down.setDbAllEquips(equips);
			}else{
				if (!dbUser.password.equals(password)){
					return NetDown.returnError(ErrorCode.VERIFY_FAILED);
				}
				
				long uid = dbUser.uid;
				down.setDbPly(PlayerService.getPlayerPo(uid));
				down.setDbAllEquips(EquipService.getAllEquips(uid));
			}

			down.setUid(dbUser.uid);
			down.setLimit(dbUser.limit);
			return down;
		}catch (Exception e){
			return NetDown.returnError(ErrorCode.SERVER_EXCEPTION);
		}finally{
			RequestContext.clear();
		}
	}
}

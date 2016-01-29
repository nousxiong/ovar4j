/**
 * 
 */
package server;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import server.equip.entity.EquipPo;
import server.equip.handler.UpgradeEquip;
import server.equip.handler.UpgradeEquipStar;
import server.login.handler.ChangePassword;
import server.login.handler.Login;
import server.net.ErrorCode;
import server.net.NetDownChangePwd;
import server.net.NetDownCharge;
import server.net.NetDownCompleteQuest;
import server.net.NetDownLogin;
import server.net.NetDownUpgradeEquip;
import server.net.NetDownUpgradeEquipStar;
import server.player.entity.PlayerPo;
import server.player.handler.Charge;
import server.player.handler.CompleteQuest;

/**
 * @author Xiong
 * 可观察变量试验例子Main入口
 */
public class Main {
	@SuppressWarnings("unused")
	static void handle(int index){
		
		// 以下模拟处理两个用户的一系列请求
		String user1 = "user1" + index;
		String user2 = "user2" + index;
		
		// 用户1登录
		NetDownLogin downLogin1 = Login.handle(user1, "pwd");
		assertTrue(downLogin1.getErrc() == ErrorCode.OK);
		PlayerPo dbPly1 = downLogin1.getDbPly();
		long exp1 = dbPly1.exp;
		long coin1 = dbPly1.coin;
		long money1 = dbPly1.money;
		long charge1 = dbPly1.charge;
		int level1 = dbPly1.level;
		int vip1 = dbPly1.vip;
		int quest1 = dbPly1.quest;
		String title1 = dbPly1.title;
		assertTrue(coin1 == ConfigData.initCoin);
		List<EquipPo> dbEquips1 = downLogin1.getDbAllEquips();
		assertTrue(dbEquips1.size() == ConfigData.initEquipTypes.size());
		
		// 用户2登录
		NetDownLogin downLogin2 = Login.handle(user2, "pwd");
		assertTrue(downLogin2.getErrc() == ErrorCode.OK);
		PlayerPo dbPly2 = downLogin2.getDbPly();
		long exp2 = dbPly2.exp;
		long coin2 = dbPly2.coin;
		long money2 = dbPly2.money;
		long charge2 = dbPly2.charge;
		int level2 = dbPly2.level;
		int vip2 = dbPly2.vip;
		int quest2 = dbPly2.quest;
		String title2 = dbPly2.title;
		assertTrue(coin2 == ConfigData.initCoin);
		List<EquipPo> dbEquips2 = downLogin2.getDbAllEquips();
		assertTrue(dbEquips2.size() == ConfigData.initEquipTypes.size());
		
		// 用户1修改密码
		String tmpPwd1 = "pwd";
		for (int i=0; i<3; ++i){
			// 改3次，不会失败
			String newPwd = tmpPwd1 + "1";
			NetDownChangePwd down = ChangePassword.handle(user1, tmpPwd1, newPwd);
			assertTrue(down.getErrc() == ErrorCode.OK);
			tmpPwd1 = newPwd;
		}
		// 第4次修改失败，超过修改限制
		assertTrue(ChangePassword.handle(user1, tmpPwd1, tmpPwd1+"1").getErrc() == ErrorCode.OUT_OF_LIMIT);
		
		// 用户1用新密码登录
		downLogin1 = Login.handle(user1, "pwd111");
		assertTrue(downLogin1.getErrc() == ErrorCode.OK);
		
		// 用户2完成2个任务
		String tmpProp2 = null;
		for (int i=0; i<2; ++i){
			NetDownCompleteQuest down = CompleteQuest.handle(downLogin2.getUid());
			assertTrue(down.getErrc() == ErrorCode.OK);
			exp2 += ConfigData.questExp;
			coin2 += ConfigData.questCoin;
			quest2++;
			assertTrue(Long.parseLong(down.getProp("player.exp")) == exp2);
			assertTrue(Long.parseLong(down.getProp("player.coin")) == coin2);
			assertTrue(Integer.parseInt(down.getProp("player.quest")) == quest2);
			tmpProp2 = down.getProp("player.level");
		}
		level2++;
		assertTrue(Integer.parseInt(tmpProp2) == level2);
		
		// 用户1充值60代币
		NetDownCharge downCharge1 = Charge.handle(downLogin1.getUid(), 60);
		assertTrue(downCharge1.getErrc() == ErrorCode.OK);
		charge1 += 60;
		money1 += 60;
		vip1++;
		title1 = "小康";
		assertTrue(Long.parseLong(downCharge1.getProp("player.charge")) == charge1);
		assertTrue(Long.parseLong(downCharge1.getProp("player.money")) == money1);
		assertTrue(Integer.parseInt(downCharge1.getProp("player.vip")) == vip1);
		assertTrue(downCharge1.getProp("player.title").equals(title1));
		
		// 保存用户2的装备0的数据
		EquipPo dbEquip02 = dbEquips2.get(0);
		long equExp02 = dbEquip02.exp;
		int equLevel02 = dbEquip02.level;
		long equStar02 = dbEquip02.star;
		
		// 用户2花费consumeCoin2金币强化装备0
		long consumeCoin2 = 35;
		NetDownUpgradeEquip downUpEquip2 = UpgradeEquip.handle(downLogin2.getUid(), dbEquip02.id, consumeCoin2);
		assertTrue(downUpEquip2.getErrc() == ErrorCode.OK);
		coin2 -= consumeCoin2;
		equExp02 += consumeCoin2;
		equLevel02 += 3;
		assertTrue(Long.parseLong(downUpEquip2.getProp("player.coin")) == coin2);
		assertTrue(Long.parseLong(downUpEquip2.getProp("equip.exp#"+dbEquip02.id)) == equExp02);
		assertTrue(Integer.parseInt(downUpEquip2.getProp("equip.level#"+dbEquip02.id)) == equLevel02);
		
		// 保存用户1的装备0的数据
		EquipPo dbEquip01 = dbEquips1.get(0);
		long equExp01 = dbEquip01.exp;
		int equLevel01 = dbEquip01.level;
		long equStar01 = dbEquip01.star;
		
		// 用户1花费consumeCoin1金币强化装备0
		long consumeCoin1 = 85;
		NetDownUpgradeEquip downUpEquip1 = UpgradeEquip.handle(downLogin1.getUid(), dbEquip01.id, consumeCoin1);
		assertTrue(downUpEquip1.getErrc() == ErrorCode.OK);
		coin1 -= consumeCoin1;
		equExp01 += consumeCoin1;
		equLevel01 += 8;
		assertTrue(Long.parseLong(downUpEquip1.getProp("player.coin")) == coin1);
		assertTrue(Long.parseLong(downUpEquip1.getProp("equip.exp#"+dbEquip01.id)) == equExp01);
		assertTrue(Integer.parseInt(downUpEquip1.getProp("equip.level#"+dbEquip01.id)) == equLevel01);
		
		// 用户1花费consumeMoney1代币升星装备0，但金币不够，返回失败
		long consumeMoney1 = 25;
		assertTrue(UpgradeEquipStar.handle(downLogin1.getUid(), dbEquip01.id, consumeMoney1).getErrc() == ErrorCode.COIN_NOT_ENOUGH);
		
		// 用户1完成1个任务
		NetDownCompleteQuest downCompleteQuest1 = CompleteQuest.handle(downLogin1.getUid());
		assertTrue(downCompleteQuest1.getErrc() == ErrorCode.OK);
		exp1 += ConfigData.questExp;
		coin1 += ConfigData.questCoin;
		assertTrue(Long.parseLong(downCompleteQuest1.getProp("player.exp")) == exp1);
		assertTrue(Long.parseLong(downCompleteQuest1.getProp("player.coin")) == coin1);
		
		// 用户2完成1个任务
		NetDownCompleteQuest downCompleteQuest2 = CompleteQuest.handle(downLogin2.getUid());
		assertTrue(downCompleteQuest2.getErrc() == ErrorCode.OK);
		exp2 += ConfigData.questExp;
		coin2 += ConfigData.questCoin;
		level2++;
		assertTrue(Long.parseLong(downCompleteQuest2.getProp("player.exp")) == exp2);
		assertTrue(Long.parseLong(downCompleteQuest2.getProp("player.coin")) == coin2);
		assertTrue(Integer.parseInt(downCompleteQuest2.getProp("player.level")) == level2);
		
		// 用户1再次花费consumeMoney1代币升星装备0
		NetDownUpgradeEquipStar downUpEquipStar1 = UpgradeEquipStar.handle(downLogin1.getUid(), dbEquip01.id, consumeMoney1);
		assertTrue(downUpEquipStar1.getErrc() == ErrorCode.OK);
		money1 -= consumeMoney1;
		coin1 -= consumeMoney1;
		equStar01 += consumeMoney1;
		exp1 += consumeMoney1;
		level1 += exp1/ConfigData.expPerLevel;
		assertTrue(Long.parseLong(downUpEquipStar1.getProp("player.money")) == money1);
		assertTrue(Long.parseLong(downUpEquipStar1.getProp("player.coin")) == coin1);
		assertTrue(Long.parseLong(downUpEquipStar1.getProp("player.exp")) == exp1);
		assertTrue(Long.parseLong(downUpEquipStar1.getProp("player.level")) == level1);
		assertTrue(Long.parseLong(downUpEquipStar1.getProp("equip.star#"+dbEquip01.id)) == equStar01);
		
		assertTrue(exp1 == 32);
		assertTrue(level1 == 3);
		assertTrue(exp2 == 21);
		assertTrue(level2 == 2);
		
		System.out.println("done.");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 模拟多个线程并行处理客户端的请求
		int threadNum = Runtime.getRuntime().availableProcessors();
		List<Thread> thrs = new ArrayList<Thread>(threadNum);
		for (int i=0; i<threadNum; ++i){
			final int index = i;
			thrs.add(new Thread(){
				@Override
				public void run(){
					Main.handle(index);
				}
			});
			thrs.get(i).start();
		}
		
		for (Thread thr : thrs){
			try{
				thr.join();
			}catch (InterruptedException e){
				System.out.println(e.getMessage());
			}
		}
	}

}

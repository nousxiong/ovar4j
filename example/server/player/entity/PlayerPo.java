/**
 * 
 */
package server.player.entity;

/**
 * @author Xiong
 * 模拟数据库玩家数据
 */
public class PlayerPo {
	public long uid; // primary 
	public String nickname;
	public long exp; // 玩家的经验值，完成任务会增长经验值
	public long coin; // 游戏内金币
	public long money; // 充值后的代币
	public long charge; // 累计充值
	public int level; // 玩家等级，经验值积累越多，等级越高
	public int vip; // vip等级，累计充值越多，vip等级越高
	public int quest; // 任务进度，线性增长，1开始，每个数字表示一个任务完成；完成任务会奖励玩家金币和经验
	public String title = ""; // 称号，和vip等级相关，越高vip，称号越好
}

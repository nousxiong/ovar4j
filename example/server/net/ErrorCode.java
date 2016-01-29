/**
 * 
 */
package server.net;

/**
 * @author Xiong
 *
 */
public enum ErrorCode {
	OK, // 成功
	USER_NOT_FOUND, // 用户未找到
	EQUIP_NOT_FOUND, // 装备未找到
	OUT_OF_LIMIT, // 超出限制
	VERIFY_FAILED, // 验证失败
	PWD_INVALID, // 密码不合法 
	COIN_NOT_ENOUGH, // 金币不足
	MONEY_NOT_ENOUGH, // 代币不足
	SERVER_EXCEPTION, // 服务器异常
}

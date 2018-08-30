package com.ego.cart.dao;
/**
* @ClassName:JedisPoolDao 
* @Description: 在这个接口中定义操作redis 的方法。
* @Company: 北京尚学堂科技有限公司 www.bjsxt.com
* @author mengqx
* @date 2017年8月9日
*/

public interface JedisPoolDao {
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);
	/**
	 * 取内容
	 * @param key
	 * @return
	 */
	String get(String key);
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	Long del(String key);
	/**
	 * 新增
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key,String value);

}

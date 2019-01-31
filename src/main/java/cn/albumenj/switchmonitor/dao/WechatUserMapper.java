package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.WechatUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信用户
 *
 * @author Albumen
 */
@Mapper
public interface WechatUserMapper {
    /**
     * 插入单条数据
     *
     * @param wechatUser
     * @return
     */
    int insert(@Param("wechatUser") WechatUser wechatUser);

    /**
     * 修改单条数据
     * 用户名为索引
     *
     * @param wechatUser
     * @return
     */
    int update(@Param("wechatUser") WechatUser wechatUser);

    /**
     * 通过OpenId查找数据
     * 不包括SessionKey
     *
     * @param wechatUser
     * @return
     */
    WechatUser selectByOpenId(@Param("wechatUser") WechatUser wechatUser);

    /**
     * 查询SessionKey
     *
     * @param wechatUser
     * @return
     */
    WechatUser selectSessionKey(@Param("wechatUser") WechatUser wechatUser);

    /**
     * 查找Token
     *
     * @param wechatUser
     * @return
     */
    WechatUser selectByToken(@Param("wechatUser") WechatUser wechatUser);

    /**
     * 查找姓名
     *
     * @param wechatUser
     * @return
     */
    WechatUser selectName(@Param("wechatUser") WechatUser wechatUser);

    /**
     * 删除数据
     *
     * @param wechatUser
     * @return
     */
    int delete(@Param("wechatUser") WechatUser wechatUser);

    /**
     * 查找所有
     *
     * @return
     */
    List<WechatUser> selectAll();
}

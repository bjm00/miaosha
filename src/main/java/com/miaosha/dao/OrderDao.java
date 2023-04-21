package com.miaosha.dao;

import com.miaosha.domain.MiaoshaOrder;
import com.miaosha.domain.OrderInfo;
import com.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDao {
    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodsVo> listGoods();
    @Select("select * from miaosha_order where goods_id = #{goodsId} and user_id = #{userId}")
    MiaoshaOrder getMiaoshaOrderByGoodsIdUserId(@Param("userId")long userId, @Param("goodsId")long goodsId);
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    void insert(OrderInfo orderInfo);
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}

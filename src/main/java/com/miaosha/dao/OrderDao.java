package com.miaosha.dao;

import com.miaosha.domain.MiaoshaOrder;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.OrderDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDao {
    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodsVo> listGoods();

    @Select("select * from miaosha_order where goods_id = #{goodId} and user_id = #{userId}")
    MiaoshaOrder getOrderById(@Param("goodsId") long goodsId, @Param("userId") long userId);
}

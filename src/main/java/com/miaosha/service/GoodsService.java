package com.miaosha.service;

import com.miaosha.dao.GoodsDao;
import com.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoods();
    }

    public GoodsVo getGoodsById(long goodsId) {

        return goodsDao.getGoodsById(goodsId);
    }
}

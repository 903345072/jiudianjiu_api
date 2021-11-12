package com.mapper;

import com.stock.models.Interest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InterestMapper {
    List findInterest();
    Interest findInterestByCase(Map map);
    int setInterest(@Param(value = "interestList")List interestList,@Param(value = "admin_id")int admin_id);
    int deleteAllInterest(@Param(value = "admin_id")int admin_id);
    Map findInterestByIds(@Param(value = "heyue_id") int heyue_id, @Param(value = "leverage_id") int leverage_id, @Param(value = "admin_id") int admin_id);
}

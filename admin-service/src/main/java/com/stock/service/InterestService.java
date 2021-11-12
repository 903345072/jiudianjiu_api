package com.stock.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InterestService {
    List findInterest(int admin_id);
    int setInterest(List list,int admin_id);
    int deleteAllInterest(int admin_id);
    Map findInterestByIds(int heyue_id, int leverage_id,int admin_id);
}



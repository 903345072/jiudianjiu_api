package com.beta.web.aspect;

import com.beta.web.contextHolder.MemberHolder;
import com.beta.web.exception.InsufficientMoneyExceptioin;
import com.mapper.frontend.MemberMapper;
import com.stock.models.frontend.Member;
import com.stock.service.ServiceImpl.HeYueCaculateAdpterInterface;
import com.stock.service.ServiceImpl.MemberServiceImpl;
import com.util.RetResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class HeYueApplyAspect {
    @Autowired
    @Qualifier("HeYueCaculateAdper")
    HeYueCaculateAdpterInterface HeYueCaculateImpl;
    @Autowired
    MemberMapper memberMapper;

    @Pointcut("execution(public * com.beta.web.controller.FrontendApi.HeYueApi.applyHeYue(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        Member member = memberMapper.findMemberById(MemberHolder.getId());

        double deposit = Double.parseDouble(request.getParameter("deposit"));
        int heyue_id = Integer.parseInt(request.getParameter("heyue_id"));
        int leverage_id = Integer.parseInt(request.getParameter("leverage_id"));
        double leverage_money = HeYueCaculateImpl.cal_leverage_money(deposit, leverage_id);

        double interest_rate = HeYueCaculateImpl.cal_interest_rate(heyue_id,leverage_id,member.getInvite_id());
        int capitial_used_time = HeYueCaculateImpl.cal_capitial_used_time(heyue_id);
        double interest = HeYueCaculateImpl.cal_interest(interest_rate,leverage_money,capitial_used_time);
        double repare_capitical = HeYueCaculateImpl.cal_repare_capitical(deposit,interest);
        Date date = new Date();
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd 14:58:00");
        String limit_format = formatter.format(date);

        SimpleDateFormat formatter1  = new SimpleDateFormat("yyyy-MM-dd 08:30:00");
        String limit_format1 = formatter1.format(date);

        SimpleDateFormat formatter_  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cur_format = formatter_.format(date);
        if(limit_format.compareTo(cur_format) <0 || limit_format1.compareTo(cur_format) >0){
            throw new RuntimeException("此时间段无法申请");
        }
        //查询余额对比准备资金
        if(member.getAmount().doubleValue() < repare_capitical){
             throw new InsufficientMoneyExceptioin("余额不足");
        }
    }

}
package com.hengxunda.vike0906.controller;

import com.hengxunda.vike0906.common.response.CommonListResponse;
import com.hengxunda.vike0906.common.response.CommonResponse;
import com.hengxunda.vike0906.config.shiro.OAuth2Token;
import com.hengxunda.vike0906.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "用户管理")
@RequestMapping("user/manager")
public class UserController {

    @ApiOperation("查询用户信息")
    @GetMapping("user-info")
    public CommonListResponse<List> getUserInfo(
            @ApiParam(value = "用户手机号码") @RequestParam(required = false) String mobile,
            Page page
    ){

        List list = new ArrayList<String>();

        list.add(mobile);
        CommonListResponse response = new CommonListResponse();
        response.addAllItem(list);

        return response;
    }

    @ApiOperation("查询推荐人")
    @GetMapping("recommender")
    public CommonResponse getRecommender(
            @ApiParam(value = "用户id") @RequestParam(required = false) String userId,
            Page page
    ){

      return new CommonResponse();
    }

    @ApiOperation("查询推广收益详情")
    @GetMapping("promotion")
    public CommonResponse getPromotion(
            @ApiParam(value = "用户id")@RequestParam(required = false) String userId,
            Page page
    ){
        return null;
    }

    @ApiOperation("查询充币信息")
    @GetMapping("recharge")
    public CommonResponse getRecharge(
            @ApiParam(value = "用户id")@RequestParam(required = false)String userId,
            Page page
    ){
        return null;
    }

    @ApiOperation("查询提币信息")
    @GetMapping("withdrawal")
    public CommonResponse getWithdrawal(
            @ApiParam(value = "用户id")@RequestParam(required = false)String userId,
            Page page ){
         OAuth2Token oAuth2Token = new OAuth2Token("");

        return null;
    }


}



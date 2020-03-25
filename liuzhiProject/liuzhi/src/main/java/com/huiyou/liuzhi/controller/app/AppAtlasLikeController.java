package com.huiyou.liuzhi.controller.app;


import com.huiyou.liuzhi.model.User;
import com.huiyou.liuzhi.service.AtlasLikeService;
import me.fishlord.common.result.BaseResultEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * @author Joe
 * @version 1.0
 * @date 2019/12/19 15:43
 */
@Controller
@RequestMapping("atlasLike")
public class AppAtlasLikeController {
    @Autowired
    private AtlasLikeService atlasLikeService;
    /**
     * 添加点赞
     * 只能点赞一次
     * @return
     */
    @RequestMapping("addAtlasLike")
    @ResponseBody
    public BaseResultEntity addAtlasLike(
    		HttpSession httpSession,
    		@RequestAttribute Long userId,
    		@RequestParam("atlasId") String atlasId ) {
//        User user = (User) httpSession.getAttribute("user");
//        if (user == null) {
//            return BaseResultEntity.fail("请先登录再点赞");
//        }
//        long userId = user.getId();
        if (StringUtils.isBlank(atlasId)) {
            return BaseResultEntity.fail("atlasId不能为空");
        }
        return atlasLikeService.addAtlasLike(Long.parseLong(atlasId), userId);
    }
    /**
     * 取消点赞
     * @return
     */
    @RequestMapping("cancelAtlasLike")
    @ResponseBody
    public BaseResultEntity cancelAtlasLike(
    		@RequestAttribute Long userId,
    		HttpSession httpSession,
    		@RequestParam("atlasId") String atlasId ){
//        User user = (User)httpSession.getAttribute("user");
//        if(user == null ) {
//            return BaseResultEntity.fail("请先登录");
//        }
//        long userId = user.getId();
        if(StringUtils.isBlank(atlasId)) {
            return BaseResultEntity.fail("atlasId不能为空");
        }
        return atlasLikeService.cancelAtlasLike(Long.parseLong(atlasId),userId);
    }

    /**
     * 打赏
     * @return
     */
    @RequestMapping("rewardAtlas")
    @ResponseBody
    public BaseResultEntity rewardAtlas(
    		@RequestAttribute Long userId,
    		HttpSession httpSession,
    		@RequestParam("atlasId") String atlasId){
        String id = httpSession.getId();
        System.out.println("jessonid = "+id);
//        User user = (User)httpSession.getAttribute("user");
//        if(user == null ) {
//            return BaseResultEntity.fail("请先登录再打赏");
//        }
//        long userId = user.getId();
        if(StringUtils.isBlank(atlasId)) {
            return BaseResultEntity.fail("atlasId不能为空");
        }
        return atlasLikeService.rewardAtlas(Long.parseLong(atlasId),userId);
    }

}

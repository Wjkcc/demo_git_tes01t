package com.huiyou.liuzhi.controller.app;

import com.huiyou.liuzhi.service.MessageService;
import me.fishlord.common.result.BaseResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author Joe
 * @version 1.0
 * @date 2020/2/13 15:59
 */
@Controller
@RequestMapping("sys")
public class AppMessageController {
    @Autowired
    private MessageService messageService;
    @RequestMapping("message")
    @ResponseBody
    public BaseResultEntity sysMessage(@RequestAttribute long userId, Integer pageSize, Integer pageNo) {

        return messageService.messageList(userId, pageSize, pageNo);
    }
    @RequestMapping("addtest")
    @ResponseBody
    public String sysTest(Long atlasId, Long userId, BigDecimal money, Long uId, String type, String uNickname, String userNickname, String title) {

        return messageService.addMessage((long)2,(long)2,new BigDecimal(2),(long)3,"测试","123","321","bbb");
    }

    @RequestMapping("read")
    @ResponseBody
    public BaseResultEntity read(@RequestAttribute long userId, @RequestParam("id") long id) {

        return messageService.readMessage(userId,id);
    }

    @RequestMapping("remove")
    @ResponseBody
    public BaseResultEntity remove(@RequestAttribute long userId, @RequestParam("id") long id) {

        return messageService.removeMessage(userId, id);
    }
}

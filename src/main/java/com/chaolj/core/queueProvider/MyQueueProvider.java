package com.chaolj.core.queueProvider;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.TypeReference;
import com.chaolj.core.MyConst;
import com.chaolj.core.MyUser;
import org.springframework.context.ApplicationContext;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.chaolj.core.MyApp;
import com.chaolj.core.commonUtils.myDto.DataResultDto;
import com.chaolj.core.commonUtils.myServer.Interface.IQueueServer;
import com.chaolj.core.commonUtils.myServer.Models.MailMessageDto;
import com.chaolj.core.commonUtils.myServer.Models.QueueMessageDto;
import com.chaolj.core.commonUtils.myServer.Models.WeChatTextCardMessageDto;
import com.chaolj.core.commonUtils.myServer.Models.WeChatTextMessageDto;

public class MyQueueProvider implements IQueueServer {
    private ApplicationContext applicationContext;
    private MyQueueProviderProperties properties;

    public MyQueueProvider(ApplicationContext applicationContext, MyQueueProviderProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    private String getHost() {
        return this.properties.getServerHostUrl();
    }

    private String getSSOUserToken() {
        return MyUser.getCurrentUserToken();
    }

    public DataResultDto<String> PushMessage(QueueMessageDto message) {
        DataResultDto<String> myresult;

        var url = this.getHost() + "/api/message/push/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildPostObject().body(message)
                    .execute().toJavaObject(new TypeReference<>(){});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("QueueServer.PushMessage，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }

    public DataResultDto<String> PushMessageList(List<QueueMessageDto> messages) {
        DataResultDto<String> myresult;

        var url = this.getHost() + "/api/message/pushlist/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildPostObject().body(messages)
                    .execute().toJavaObject(new TypeReference<>(){});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("QueueServer.PushMessageList，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }

    public DataResultDto<String> PushMailMessage(MailMessageDto message) {
        DataResultDto<String> myresult;

        if (message == null) {
            return DataResultDto.error("QueueServer.PushMailMessage，处理失败！" + "message 不能为空！");
        }

        if (StrUtil.isBlank(message.getFrom())) {
            return DataResultDto.error("QueueServer.PushMailMessage，处理失败！" + "发件人不能为空！");
        }

        if (StrUtil.isBlank(message.getTo())) {
            return DataResultDto.error("QueueServer.PushMailMessage，处理失败！" + "收件人不能为空！");
        }

        var url = this.getHost() + "/DataApi/Global/PushMailMessage/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildPostObject().query("sender", applicationContext.getApplicationName()).body(message)
                    .execute().toJavaObject(new TypeReference<>(){});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("QueueServer.PushMailMessage，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }

    public DataResultDto<String> PushWeChatTextMessage(WeChatTextMessageDto message) {
        DataResultDto<String> myresult;

        if (message == null) {
            return DataResultDto.error("QueueServer.PushWeChatTextMessage，处理失败！" + "message 不能为空！");
        }

        if (StrUtil.isBlank(message.getTouser()) && StrUtil.isBlank(message.getToparty())) {
            return DataResultDto.error("QueueServer.PushWeChatTextMessage，处理失败！" + "touser 与 toparty 不能同时为空！");
        }

        if (message.getText() == null || StrUtil.isBlank(message.getText().getContent())) {
            return DataResultDto.error("QueueServer.PushWeChatTextMessage，处理失败！" + "内容不能为空！");
        }

        var url = this.getHost() + "/DataApi/Global/PushWeChatTextMessage/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildPostObject().query("sender", applicationContext.getApplicationName()).body(message)
                    .execute().toJavaObject(new TypeReference<>(){});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("QueueServer.PushWeChatTextMessage，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }

    public DataResultDto<String> PushWeChatTextCardMessage(WeChatTextCardMessageDto message) {
        DataResultDto<String> myresult;

        if (message == null) {
            return DataResultDto.error("QueueServer.PushWeChatTextCardMessage，处理失败！" + "message 不能为空！");
        }

        if (StrUtil.isBlank(message.getTouser()) && StrUtil.isBlank(message.getToparty())) {
            return DataResultDto.error("QueueServer.PushWeChatTextCardMessage，处理失败！" + "touser 与 toparty 不能同时为空！");
        }

        if (message.getTextcard() == null || StrUtil.isBlank(message.getTextcard().getTitle()) || StrUtil.isBlank(message.getTextcard().getDescription())) {
            return DataResultDto.error("QueueServer.PushWeChatTextCardMessage，处理失败！" + "内容不能为空！");
        }

        var url = this.getHost() + "/DataApi/Global/PushWeChatTextCardMessage/";
        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildPostObject().query("sender", applicationContext.getApplicationName()).body(message)
                    .execute().toJavaObject(new TypeReference<>(){});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("QueueServer.PushWeChatTextCardMessage，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }

    public DataResultDto<String> PushBpmMessage(String packageName, String workflowName, String startUserLoginName, String startDeptCode, Boolean finishFirst, HashMap<String, Object> dataitems) {
        DataResultDto<String> myresult;

        if (StrUtil.isBlank(packageName)) {
            return DataResultDto.error("QueueServer.PushBpmMessage，处理失败！" + "流程分类不能为空！");
        }

        if (StrUtil.isBlank(workflowName)) {
            return DataResultDto.error("QueueServer.PushBpmMessage，处理失败！" + "流程模板不能为空！");
        }

        if (StrUtil.isBlank(startUserLoginName)) {
            return DataResultDto.error("QueueServer.PushBpmMessage，处理失败！" + "发起人不能为空！");
        }

        if (StrUtil.isBlank(startDeptCode)) {
            return DataResultDto.error("QueueServer.PushBpmMessage，处理失败！" + "发起部门不能为空！");
        }

        var url = this.getHost() + "/DataApi/Global/PushBpmMessage/";

        var querys = new HashMap<String, Object>();
        querys.put("sender", applicationContext.getApplicationName());
        querys.put("packageName", packageName);
        querys.put("workflowName", workflowName);
        querys.put("startUserLoginName", startUserLoginName);
        querys.put("startDeptCode", startDeptCode);
        querys.put("finishFirst", Optional.ofNullable(finishFirst).orElse(true).toString());

        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildPostObject().query(querys).body(dataitems)
                    .execute().toJavaObject(new TypeReference<>(){});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("QueueServer.PushBpmMessage，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }

    public DataResultDto<String> PushWxRobotMessage(String receivers, String message) {
        DataResultDto<String> myresult;

        if (StrUtil.isBlank(receivers)) {
            return DataResultDto.error("QueueServer.PushWxRobotMessage，处理失败！" + "receivers 不能为空！");
        }

        if (StrUtil.isBlank(message)) {
            return DataResultDto.error("QueueServer.PushWxRobotMessage，处理失败！" + "message 不能为空！");
        }

        var url = this.getHost() + "/DataApi/Global/PushWxRobotMessage/";

        var querys = new HashMap<String, Object>();
        querys.put("sender", applicationContext.getApplicationName());
        querys.put("Receivers", receivers.replace(",", "|"));
        querys.put("Message", message);

        try {
            myresult = MyApp.Http().url(url)
                    .header(MyConst.HEADERKEY_TOKEN, this.getSSOUserToken())
                    .buildGet().query(querys)
                    .execute().toJavaObject(new TypeReference<>(){});
        } catch (Exception ex) {
            myresult = DataResultDto.Empty();
            myresult.setResult(false);
            myresult.setMessage("QueueServer.PushWxRobotMessage，处理失败！" + ex.getMessage());
            myresult.setData("");
        }

        return myresult;
    }
}

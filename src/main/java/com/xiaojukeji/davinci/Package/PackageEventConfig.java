package com.xiaojukeji.davinci.Package;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @description 状态机event配置
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
@WithStateMachine(id = "packageMachine")
public class PackageEventConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 当前状态BLANK_PACKAGE
     */
    @OnTransition(target = "BLANK_PACKAGE")
    public void create() {
        logger.info("---初始空白包---");
    }

    @OnTransition(source = "BLANK_PACKAGE", target = "FULL_PACKAGE")
    public void make(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---制包完成---");
    }

    @OnTransition(source = "FULL_PACKAGE", target = "INTEGRITY_CHECK_CHOICE")
    public void check(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---校验安装包的完整度---");
    }

    //不会执行
    @OnTransition(source = "INTEGRITY_CHECK_CHOICE", target = "SEND_PACKAGE")
    public void checkSuccess(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---完整度校验成功--->待下发(choice true)---");
    }

    //不会执行
    @OnTransition(source = "INTEGRITY_CHECK_CHOICE", target = "DESTROYED_PACKAGE")
    public void checkFail(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---完整度校验失败--->废包(choice false)---");
    }

    @OnTransition(source = "SEND_PACKAGE", target = "ORIGINAL_PACKAGE")
    public void send(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---制包完成---");
    }

    @OnTransition(source = "ORIGINAL_PACKAGE", target = "SIGNATURE_VERIFICATE_CHOICE")
    public void verificate(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---签名校验---");
    }

    //不会执行
    @OnTransition(source = "SIGNATURE_VERIFICATE_CHOICE", target = "UPDATE_PACKAGE")
    public void verificateSuccess(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---签名校验成功--->待更新包(choice true)---");
    }

    //不会执行
    @OnTransition(source = "SIGNATURE_VERIFICATE_CHOICE", target = "DESTROYED_PACKAGE")
    public void verificateFail(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---签名校验失败--->废包(choice false)---");
    }

    @OnTransition(source = "UPDATE_PACKAGE", target = "FINISHED_PACKAGE")
    public void update(Message<PackageEvents> message) {
        System.out.println("传递的参数：" + message.getHeaders().get("package").toString());
        logger.info("---安装包更新成功！！！---");
    }

}

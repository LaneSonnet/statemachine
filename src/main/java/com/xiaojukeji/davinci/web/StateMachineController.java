package com.xiaojukeji.davinci.web;

import com.xiaojukeji.davinci.Package.Package;
import com.xiaojukeji.davinci.Package.PackageEvents;
import com.xiaojukeji.davinci.Package.PackageStateMachineBuilder;
import com.xiaojukeji.davinci.Package.PackageStates;
import javax.annotation.Resource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description demo测试
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
@RestController
@RequestMapping("/statemachine")
public class StateMachineController {

    @Autowired
    private PackageStateMachineBuilder packageStateMachineBuilder;

    @Resource(name = "packageRedisPersister")
    private StateMachinePersister<PackageStates, PackageEvents, String> packageRedisPersister;

    @Autowired
    private BeanFactory beanFactory;

    @RequestMapping("/testPackageState")
    public void testPackageState() throws Exception {

        StateMachine<PackageStates, PackageEvents> stateMachine =
                packageStateMachineBuilder.build(beanFactory);
        System.out.println(stateMachine.getId());

        Package pack1 = new Package()
                .setId(1)
                .setPackageInfo(null)
                .setPackageName("包1")
                .setSignature("DES");

        Package pack2 = new Package()
                .setId(2)
                .setPackageInfo("完整的包")
                .setPackageName("包2")
                .setSignature("AES");

        Package pack3 = new Package()
                .setId(3)
                .setPackageInfo("完整的包")
                .setPackageName("包3")
                .setSignature("MD5");

        // 创建流程
        System.out.println("-------------------pack1------------------");

        stateMachine.start();
        Message message = MessageBuilder.withPayload(PackageEvents.MAKE).setHeader("package", pack1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.CHECK).setHeader("package", pack1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.SEND).setHeader("package", pack1).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.UPDATE).setHeader("package", pack1).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());

        System.out.println("-------------------pack2------------------");
        stateMachine = packageStateMachineBuilder.build(beanFactory);
        stateMachine.start();
        message = MessageBuilder.withPayload(PackageEvents.MAKE).setHeader("package", pack2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.CHECK).setHeader("package", pack2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.SEND).setHeader("package", pack2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.VERIFICATE).setHeader("package", pack2).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.UPDATE).setHeader("package", pack2).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());

        System.out.println("-------------------pack3------------------");
        stateMachine = packageStateMachineBuilder.build(beanFactory);
        stateMachine.start();
        message = MessageBuilder.withPayload(PackageEvents.MAKE).setHeader("package", pack3).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.CHECK).setHeader("package", pack3).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.SEND).setHeader("package", pack3).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.VERIFICATE).setHeader("package", pack3).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());
        message = MessageBuilder.withPayload(PackageEvents.UPDATE).setHeader("package", pack3).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());
    }

    @RequestMapping("/testRedisPersister")
    public void testRedisPersister(String id) throws Exception {
        StateMachine<PackageStates, PackageEvents> stateMachine = packageStateMachineBuilder.build(beanFactory);
        stateMachine.start();
        Package pack = new Package();
        pack.setId(Integer.valueOf(id));
        //发送MAKE事件
        Message<PackageEvents> message =
                MessageBuilder.withPayload(PackageEvents.MAKE).setHeader("package", pack).build();
        stateMachine.sendEvent(message);
        //持久化stateMachine
        packageRedisPersister.persist(stateMachine, pack.getId().toString());
    }

    @RequestMapping("/testRedisPersisterRestore")
    public void testRestore(String id) throws Exception {
        StateMachine<PackageStates, PackageEvents> stateMachine = packageStateMachineBuilder.build(beanFactory);
        packageRedisPersister.restore(stateMachine, id);
        System.out.println("恢复状态机后的状态为：" + stateMachine.getState().getId());
    }
}

package com.xiaojukeji.davinci.Package;

import java.util.EnumSet;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

/**
 * 状态机构建器
 */
@Component
public class PackageStateMachineBuilder {

    private final static String MACHINEID = "packageMachine";

    /**
     * @description 状态机注册
     * @auther duenpu
     * @date 2021/9/26 16:34
     */
    public StateMachine<PackageStates, PackageEvents> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<PackageStates, PackageEvents> builder = StateMachineBuilder.builder();

        System.out.println("构建状态机");

        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINEID)
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial(PackageStates.BLANK_PACKAGE)
                .choice(PackageStates.INTEGRITY_CHECK_CHOICE)
                .choice(PackageStates.SIGNATURE_VERIFICATE_CHOICE)
                .states(EnumSet.allOf(PackageStates.class));

        builder.configureTransitions()
                .withExternal()
                .source(PackageStates.BLANK_PACKAGE).target(PackageStates.FULL_PACKAGE)
                .event(PackageEvents.MAKE)
                .and()
                .withExternal()
                .source(PackageStates.FULL_PACKAGE).target(PackageStates.INTEGRITY_CHECK_CHOICE)
                .event(PackageEvents.CHECK)
                .and()
                .withChoice()
                .source(PackageStates.INTEGRITY_CHECK_CHOICE)
                .first(PackageStates.SEND_PACKAGE, new PackageIntegrityCheckChoiceGuard(), new PackageChoiceAction())
                .last(PackageStates.DESTROYED_PACKAGE, new PackageChoiceAction())
                .and()
                .withExternal()
                .source(PackageStates.SEND_PACKAGE).target(PackageStates.ORIGINAL_PACKAGE)
                .event(PackageEvents.SEND)
                .and()
                .withExternal()
                .source(PackageStates.ORIGINAL_PACKAGE).target(PackageStates.SIGNATURE_VERIFICATE_CHOICE)
                .event(PackageEvents.VERIFICATE)
                .and()
                .withChoice()
                .source(PackageStates.SIGNATURE_VERIFICATE_CHOICE)
                .first(PackageStates.UPDATE_PACKAGE, new PackageSignatureVerificateChoiceGuard(),
                        new PackageChoiceAction())
                .last(PackageStates.DESTROYED_PACKAGE, new PackageChoiceAction())
                .and()
                .withExternal()
                .source(PackageStates.UPDATE_PACKAGE).target(PackageStates.FINISHED_PACKAGE)
                .event(PackageEvents.UPDATE);
        return builder.build();
    }
}


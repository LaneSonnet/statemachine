package com.xiaojukeji.davinci.Package;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

/**
 * @description 状态及持久化
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
@Component
public class PackageStateMachinePersist implements StateMachinePersist<PackageStates, PackageEvents, Package> {

    @Override
    public void write(StateMachineContext<PackageStates, PackageEvents> context, Package contextObj) throws Exception {
        //这里不做任何持久化工作
    }

    @Override
    public StateMachineContext<PackageStates, PackageEvents> read(Package contextObj) throws Exception {
        StateMachineContext<PackageStates, PackageEvents> result = new DefaultStateMachineContext<PackageStates,
                PackageEvents>(PackageStates.valueOf(contextObj.getState()),
                null, null, null, null, "packageMachine");
        return result;
    }
}

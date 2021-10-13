package com.xiaojukeji.davinci.Package;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @description ChoiceAction
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
public class PackageChoiceAction implements Action<PackageStates, PackageEvents> {

    @Override
    public void execute(StateContext<PackageStates, PackageEvents> context) {
        System.out.println("进入 PackageChoiceAction");
        Package pack = context.getMessage().getHeaders().get("package", Package.class);
        System.out.println(pack);
        System.out.println(context.getStateMachine().getState());
    }

}

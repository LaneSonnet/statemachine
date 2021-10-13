package com.xiaojukeji.davinci.Package;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * @description 签名校验choice
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
public class PackageSignatureVerificateChoiceGuard implements Guard<PackageStates, PackageEvents> {

    @Override
    public boolean evaluate(StateContext<PackageStates, PackageEvents> context) {
        System.out.println("PackageSignatureVerificateChoiceGuard!!!!!!!!!!!!!");
        boolean returnValue = false;
        Package pack = context.getMessage().getHeaders().get("package", Package.class);

        if ((pack.getSignature() == null) || (pack.getSignature().indexOf("AES") > -1)) {
            returnValue = false;
        } else {
            returnValue = true;
        }

        System.out.println(pack.toString() + " is " + returnValue);
        return returnValue;
    }

}

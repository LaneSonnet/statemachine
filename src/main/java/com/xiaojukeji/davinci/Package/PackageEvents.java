package com.xiaojukeji.davinci.Package;

/**
 * @description 状态机事件
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
public enum PackageEvents {
    MAKE, // 制作
    CHECK, // 完整度校验
    SEND, // 下发
    VERIFICATE, // 签名校验
    UPDATE,// 更新
}

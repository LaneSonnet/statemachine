package com.xiaojukeji.davinci.Package;

/**
 * @description 状态机节点
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
public enum PackageStates {
    BLANK_PACKAGE, // 空白包
    FULL_PACKAGE, // 完整包
    INTEGRITY_CHECK_CHOICE,// 完整度校验
    SEND_PACKAGE,// 待下发包
    ORIGINAL_PACKAGE,// 原始包
    DESTROYED_PACKAGE,// 废包
    SIGNATURE_VERIFICATE_CHOICE, // 签名校验
    UPDATE_PACKAGE,// 待更新包
    FINISHED_PACKAGE,// 完成包
}

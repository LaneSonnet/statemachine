package com.xiaojukeji.davinci.Package;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description package实体类
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
@Data
@Accessors(chain = true)
public class Package {

    Integer id;

    String packageName;

    String packageInfo;

    String signature;

    String state;
}

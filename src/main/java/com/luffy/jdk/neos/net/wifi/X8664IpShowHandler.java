package com.luffy.jdk.neos.net.wifi;

import com.luffy.jdk.neos.annotation.Platform;
import com.luffy.jdk.neos.enums.Machine;

/**
 *  X86处理
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-09 16:00:13
 */
@Platform(machine = Machine.X86_64)
public class X8664IpShowHandler extends IpShowHandler {

    @Override
    public String[] command(String... p) {
        return new String[]{p[0],"-x86"};
    }

    @Override
    public String[] result() {
        return new String[0];
    }
}

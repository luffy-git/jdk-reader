package com.luffy.jdk.neos.net.wifi;

/**
 * Created by Luffy on 2020/9/8
 */

import com.luffy.jdk.neos.annotation.Platform;
import com.luffy.jdk.neos.enums.Machine;

@Platform(machine = Machine.ARMV7L)
public class ArmIpShowHandler extends IpShowHandler {

    @Override
    public String[] command(String... p) {
        return new String[]{"a","-b",p[0]};
    }
}

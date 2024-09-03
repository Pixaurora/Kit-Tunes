package net.pixaurora.kitten_square.impl;

import net.minecraft.network.chat.MutableComponent;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_square.impl.service.UICompatImpl;

public class FakeComponent implements Component {
    private final MutableComponent parent;

    public FakeComponent(MutableComponent component) {
        this.parent = component;
    }

    public MutableComponent gameVer() {
        return this.parent;
    }

    @Override
    public Component concat(Component component) {
        return new FakeComponent(this.parent.copy().append(UICompatImpl.internalToMinecraftType(component)));
    }
}

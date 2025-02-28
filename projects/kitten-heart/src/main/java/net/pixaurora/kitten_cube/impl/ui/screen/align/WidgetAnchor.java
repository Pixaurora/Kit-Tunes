package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public interface WidgetAnchor {
    public static WidgetAnchor TOP_LEFT = new TopLeft();
    public static WidgetAnchor TOP_MIDDLE = new TopMiddle();
    public static WidgetAnchor TOP_RIGHT = new TopRight();
    public static WidgetAnchor MIDDLE_LEFT = new MiddleLeft();
    public static WidgetAnchor MIDDLE_RIGHT = new MiddleRight();
    public static WidgetAnchor BOTTOM_LEFT = new BottomLeft();
    public static WidgetAnchor BOTTOM_MIDDLE = new BottomMiddle();
    public static WidgetAnchor BOTTOM_RIGHT = new BottomRight();

    public int anchorX(Widget widget);

    public int anchorY(Widget widget);

    public static class TopLeft implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return 0;
        }

        @Override
        public int anchorY(Widget widget) {
            return 0;
        }
    }

    public static class TopMiddle implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.size().x() / 2;
        }

        @Override
        public int anchorY(Widget widget) {
            return 0;
        }
    }

    public static class TopRight implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.size().width();
        }

        @Override
        public int anchorY(Widget widget) {
            return 0;
        }
    }

    public static class MiddleLeft implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return 0;
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.size().height() / 2;
        }
    }

    public static class MiddleRight implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.size().width();
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.size().height() / 2;
        }
    }

    public static class BottomLeft implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return 0;
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.size().height();
        }
    }

    public static class BottomMiddle implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.size().x() / 2;
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.size().height();
        }
    }

    public static class BottomRight implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.size().width();
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.size().height();
        }
    }
}

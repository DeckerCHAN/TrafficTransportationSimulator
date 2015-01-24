package org.procrastinationpatients.tts.core;

import javafx.application.Application;
import org.procrastinationpatients.tts.gui.MainWindow;

import java.util.Collection;

/**
 * 作为所有实例的可达索引
 * 例子：Engine.getA().getB()
 * 可以获得任何一个公开的实例
 *
 * @Author Decker & his father -- Jeffrey
 */
public class Engine {
    /**
     * 单例对象实例
     */
    private static Engine instance;

    /**
     * 获取单例
     *
     * @return 全局唯一单例
     */
    public static Engine getInstance() {
        return instance;
    }


    static {
        //初始化单例
        instance = new Engine();
    }

    /**
     * 窗口的类引用，用于初始化窗口
     */
    private Class<MainWindow> mainWindowClass;
    /**
     * 动态可被绘制的对象集，将随着tick做刷新
     */
    private Collection<Drawable> staticDrawables;
    /**
     * 静态可绘制对象集，将在窗口初始化的时候绘制完毕
     */
    private Collection<Drawable>  dynamicDrawables;

    /**
     * 私有构造函数
     */
    private Engine() {
        this.mainWindowClass=MainWindow.class;

    }

    /**
     * 启动Engine
     */
    public void run() {

        Application.launch(this.mainWindowClass);
        this.stop();
    }

    /**
     * 停止Engine
     */
    public void stop() {
    //TODO:停止所有运算，释放所有资源
    }


    public Collection<Drawable> getStaticDrawables() {
        return staticDrawables;
    }

    public void setStaticDrawables(Collection<Drawable> staticDrawables) {
        this.staticDrawables = staticDrawables;
    }

    public Collection<Drawable> getDynamicDrawables() {
        return dynamicDrawables;
    }

    public void setDynamicDrawables(Collection<Drawable> dynamicDrawables) {
        this.dynamicDrawables = dynamicDrawables;
    }
}

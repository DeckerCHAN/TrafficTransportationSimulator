package org.procrastinationpatients.tts.core;

/**
 * 作为所有实例的可达索引
 * 例子：Engine.getA().getB()
 * 可以获得任何一个公开的实例
 *
 * @Author Decker
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
     * 私有构造函数
     */
    private Engine() {

    }

    /**
     * 启动Engine
     */
    public void run() {

    }


}

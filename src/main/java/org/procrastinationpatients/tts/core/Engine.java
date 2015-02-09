package org.procrastinationpatients.tts.core;

import javafx.application.Application;
import org.procrastinationpatients.tts.entities.Cross;
import org.procrastinationpatients.tts.entities.Link;
import org.procrastinationpatients.tts.entities.Margin;
import org.procrastinationpatients.tts.entities.Visible;
import org.procrastinationpatients.tts.gui.MainApp;
import org.procrastinationpatients.tts.gui.MainStage;

import java.util.ArrayList;
import java.util.Arrays;
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
     * 窗口的类引用，用于初始化窗口
     */
    private Class mainAppClass;
    private Cross[] crosses;
    private Link[] links;
    private Margin[] margins;
    private Processor processor;
    private volatile Boolean isStopped;
    private volatile Boolean isPaused;

    /**
     * 私有构造函数
     */
    private Engine() {
        this.mainAppClass = MainApp.class;
        this.isPaused = false;
        this.isStopped = false;
    }

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
     * 启动Engine
     */
    public void launch() {

        Application.launch(this.mainAppClass);
        this.stop();
    }

    /**
     * 停止Engine
     */
    public void stop() {
        //TODO:停止所有运算，释放所有资源
        this.setIsStopped(true);
        System.out.println("Stopped all!");
    }

    /**
     * 暂停Engine
     */
    public void pause(){
        this.setIsPaused(true);
    }

    public void resume()
    {
        this.setIsPaused(false);
    }


    /**
     * 拥有图形的对象，可以通过其中的函数绘制静态或者动态图像
     */
    public Collection<Visible> getVisualEntities() {
        ArrayList<Visible> visualEntities = new ArrayList<>();
        visualEntities.addAll(Arrays.asList(this.getCrosses()));
        visualEntities.addAll(Arrays.asList(this.getMargins()));
        visualEntities.addAll(Arrays.asList(this.getLinks()));

        return visualEntities;
    }


    public Cross[] getCrosses() {
        return crosses;
    }

    public void setCrosses(Cross[] crosses) {
        this.crosses = crosses;
    }

    public Link[] getLinks() {
        return links;
    }

    public void setLinks(Link[] links) {
        this.links = links;
    }

    public Margin[] getMargins() {
        return margins;
    }

    public void setMargins(Margin[] margins) {
        this.margins = margins;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public Boolean getIsStopped() {
        return isStopped;
    }

    public void setIsStopped(Boolean isStopped) {
        this.isStopped = isStopped;
    }

    public Boolean getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(Boolean isPaused) {
        this.isPaused = isPaused;
    }
}
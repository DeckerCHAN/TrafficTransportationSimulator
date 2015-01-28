package org.procrastinationpatients.tts.core;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Lane {
    Vehicle[] vehicles;
    Lane input;
    Lane output;
	Container container ;
    public Lane()
    {

    }

	public Lane(Container container, Lane input , Lane output)
	{
		this.container = container ;
		this.input = input ;
		this.output = output ;
	}

	public Lane getInput(){
		return this.input ;
	}

	public Lane getOutput(){
		return this.output ;
	}

	public Container getContainer(){
		return this.container ;
	}

	public void setInput(Lane input){ this.input = input ;}

	public void setOutput(Lane output){ this.output = output ;}
}

class Button{
	int[] prop = new int[4];
	boolean state = false;
	PImage[] images = new PImage[2];

	/* create Button() Object */
	Button(PImage[] tempImages, int tempWidth, int tempHeight, int tempX, int tempY){
		images = tempImages;
		prop[0] = tempWidth;
		prop[1] = tempHeight;
		prop[2] = tempX; //center position X of this button
		prop[3] = tempY; //center position Y of this button
	}

	/* display buttonImage */
	void draw(){
		pushMatrix();

		imageMode(CENTER);
		if(state){
			image(images[1], prop[2], prop[3]);
		}else{
			image(images[0], prop[2], prop[3]);
		}

		popMatrix();
	}

	/* onMouse Event */
	boolean onMouse(){
		if(mouseX >= prop[2] - prop[0] / 2 && mouseX <= prop[2] + prop[0] / 2 && mouseY >= prop[3] - prop[1] / 2 && mouseY <= prop[3] + prop[1] / 2){
			return true;
		}else{
			return false;
		}
	}

	/* onClick Event */
	boolean onClick(){
		if(onMouse()){
			return true;
		}else{
			return false;
		}
	}
}
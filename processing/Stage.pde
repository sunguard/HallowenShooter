class Stage{
	int currStage;
	int[] history = new int[1];

	/* create Stage() Object */
	Stage(){
		currStage = 0;
		history[0] = 0;
	}

	/* move to the X stage */
	void move(int nextStage){
		currStage = nextStage;
		history = (int[]) append(history, nextStage);
	}

	/* back to the previous stage */
	void back(){
		currStage = history[history.length - 2];
		history = shorten(history);
	}
}
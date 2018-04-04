import java.util.*;
import java.io.*;


class Question{
	String questionID;
	String tag;
	String difficulty;

	Question(String questionID, String tag, String difficulty){
		this.questionID = questionID;
		this.tag = tag;
		this.difficulty = difficulty;
	}	
}

class QuizGenerator{
	public static void createInput(){ /* Create pool of Questions */
		Random rn = new Random();
		String[] difficulty = new String[]{"HARD","MEDIUM","EASY"};
		for(int i = 1; i <=600; i++){
			int ans = rn.nextInt(6) + 1;
			int index = rn.nextInt(3);
			System.out.println("Q"+i+"|"+difficulty[index]+"|Tag"+ans);
		}				
	}

	public static int findNumberOfQuizes(ArrayList<Question> questionPool, ArrayList<String> requirements){
		ArrayList<ArrayList<String>> quizzesArray = new ArrayList<>();
		boolean isAnotherQuizPossible = true;

		while(isAnotherQuizPossible){
			ArrayList<String> quiz = new ArrayList<>();
			ArrayList<String> newRequirements = new ArrayList<String>(requirements); 
			ArrayList<Question> questionsToRemoveFromIteration1 = new ArrayList<>();			

			/* Iteration 1 : When both tag and difficulty level matches */
			for(Question q : questionPool){
				if(newRequirements.contains(q.tag) && newRequirements.contains(q.difficulty)){ 
					newRequirements.remove(q.tag);
					newRequirements.remove(q.difficulty);
					quiz.add(q.questionID);
					questionsToRemoveFromIteration1.add(q);
				}

				if(newRequirements.isEmpty()){ 
					quizzesArray.add(quiz);
					break;
				}				
			}
			questionPool.removeAll(questionsToRemoveFromIteration1);

			/* Iteration 2 : when either tag and difficulty level matches */
			ArrayList<Question> questionsToRemoveFromIteration2 = new ArrayList<>();
			if(!newRequirements.isEmpty()){
				for(Question q : questionPool){
					if(newRequirements.contains(q.tag)){
						newRequirements.remove(q.tag);
					}
					else if(newRequirements.contains(q.difficulty)){
						newRequirements.remove(q.difficulty);
					} 
					else {
						continue;
					}
					quiz.add(q.questionID);
					questionsToRemoveFromIteration2.add(q);

					if(newRequirements.isEmpty()){
						quizzesArray.add(quiz);
						break;
					}

					if(quiz.size() >= 10){
						break;
					}
				}
			}

			if (quizzesArray.size() >= 60) {
				isAnotherQuizPossible = false;
			} 
			else if(newRequirements.isEmpty()){
				questionPool.removeAll(questionsToRemoveFromIteration2);
			}
			else{
				questionPool.addAll(questionsToRemoveFromIteration1);
				isAnotherQuizPossible = false;
			}
		}

		for(ArrayList<String> ar : quizzesArray){
			while(ar.size() < 10 && !(questionPool.isEmpty())){
				Question sample = questionPool.remove(questionPool.size()-1);
				ar.add(sample.questionID);
			}
		}
		return quizzesArray.size();
	}

	public static void main(String[] args) throws Exception{
		//createInput(); /*Comment it after input file has been made*/

		File inputFile = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(inputFile));

		ArrayList<Question> questionPool = new ArrayList<>();
		String s;
		while((s = br.readLine()) != null){
			String[] parts = s.split("[|]");
			Question q = new Question(parts[0],parts[1],parts[2]);
			questionPool.add(q);
		}

		ArrayList<String> requirements = new ArrayList<>();
		requirements.add("Tag1");
		requirements.add("Tag2");
		requirements.add("Tag3");
		requirements.add("Tag4");
		requirements.add("Tag5");
		requirements.add("Tag6");
		requirements.add("EASY");
		requirements.add("EASY");
		requirements.add("MEDIUM");
		requirements.add("MEDIUM");
		requirements.add("HARD");
		requirements.add("HARD");
		
		int count = findNumberOfQuizes(questionPool, requirements);
		System.out.println(count);				
	}
}
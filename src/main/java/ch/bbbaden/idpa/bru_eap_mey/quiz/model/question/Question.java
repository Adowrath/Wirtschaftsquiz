package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

public abstract class Question<AnswerType> {

	public abstract boolean check(AnswerType answer);

	public abstract AnswerType getAnswer();

	public abstract String[] getAnswers();

	public abstract String getQuestion();

	public abstract String getFilename();

}
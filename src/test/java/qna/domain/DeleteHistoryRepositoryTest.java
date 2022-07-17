package qna.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DeleteHistoryRepositoryTest {

	private final DeleteHistoryRepository deleteHistoryRepository;
	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;

	DeleteHistoryRepositoryTest(DeleteHistoryRepository deleteHistoryRepository,
		QuestionRepository questionRepository, UserRepository userRepository) {
		this.deleteHistoryRepository = deleteHistoryRepository;
		this.questionRepository = questionRepository;
		this.userRepository = userRepository;
	}

	@DisplayName("DeleteHistory를 저장한다.")
	@Test
	void save() {
		User writer = userRepository.save(UserTest.JAVAJIGI);
		Question question = questionRepository.save(QuestionTest.Q1.writeBy(writer));

		DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter());
		deleteHistoryRepository.save(deleteHistory);

		DeleteHistory findHistory = deleteHistoryRepository.findById(deleteHistory.getId()).get();

		assertThat(findHistory).isEqualTo(deleteHistory);
	}
}

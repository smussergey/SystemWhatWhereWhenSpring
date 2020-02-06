//package ua.training.system_what_where_when_spring.service;
//
//import org.springframework.stereotype.Service;
//import ua.training.system_what_where_when_spring.dto.AnsweredQuestionDTO;
//import ua.training.system_what_where_when_spring.entity.Question;
//import ua.training.system_what_where_when_spring.entity.AppealStage;
//import ua.training.system_what_where_when_spring.repository.AnsweredQuestionRepository;
//import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;
//
//import java.util.List;
//
//@Service
//public class AnsweredQuestionService {
//
//    private final AnsweredQuestionRepository answeredQuestionRepository;
//
//    public AnsweredQuestionService(AnsweredQuestionRepository answeredQuestionRepository) {
//        this.answeredQuestionRepository = answeredQuestionRepository;
//    }
//
//    //TODO refactor this method
//    public Question findAnsweredQuestionById(Long id) {
//        return answeredQuestionRepository.findById(id).get();
//    }
//
//    public AnsweredQuestionDTO toAnsweredQuestionDTO(Question question) {
//        AnsweredQuestionDTO answeredQuestionDTO = new AnsweredQuestionDTO();
//        answeredQuestionDTO.setId(question.getId());
//
//        if (question.getUserWhoGotPoint() != null) {
//            answeredQuestionDTO.setNameWhoGotPointUa(question.getUserWhoGotPoint().getNameUa());
//            answeredQuestionDTO.setNameWhoGotPointEn(question.getUserWhoGotPoint().getNameEn());
//        } else {
//            answeredQuestionDTO.setNameWhoGotPointUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//            answeredQuestionDTO.setNameWhoGotPointEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//        }
//
//        if (question.getAppeal() != null) {
//            answeredQuestionDTO.setAppealStage(
//                    ResourceBundleUtil.getBundleStringForAppealStage(
//                            question.getAppeal().getAppealStage().name()));
//
//        } else answeredQuestionDTO.setAppealStage(
//                ResourceBundleUtil.getBundleStringForAppealStage(
//                        AppealStage.NOT_FILED.name()));
//
//
//        return answeredQuestionDTO;
//    }
//
//    public List<Question> saveAll(List<Question> questions) {
//        return answeredQuestionRepository.saveAll(questions);
//    }
//}

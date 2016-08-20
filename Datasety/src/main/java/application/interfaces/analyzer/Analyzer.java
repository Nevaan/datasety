package application.interfaces.analyzer;

import application.LogicSentence;
import application.implementations.checker.Absence;
import application.implementations.checker.Existence;
import application.implementations.checker.Invariance;
import application.implementations.checker.Persistence;
import application.interfaces.checker.Checker;
import enums.PatternType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pawel on 19.08.2016.
 *
 */
public abstract class Analyzer {

    public Logger logger = LogManager.getLogger(Analyzer.class.getName());

    private Checker checker;
    private List<LogicSentence> logicSentences;
    private Map<String, ArrayList<String>> dataMap;
    private List<String> dataHeaders;

    public Analyzer() {
        this.dataHeaders = new ArrayList<>();
        this.dataMap = new HashMap<>() ;
    }

    //TODO: tymczasowo bo nie wiem czym różnią się tryby analizera -> info w implementacjach
    public boolean analyzeList() {
        logger.info("Starting analysing list of logic sentences ... ");

        List<Boolean> outcomes = new ArrayList<>();

        for (LogicSentence logicSentence : logicSentences) {
           switch(logicSentence.getChosenPattern()) {
               case ABSENCE:
                   checker = new Absence();
                   break;
               case EXISTENCE:
                   checker = new Existence();
                   break;
               case INVARIANCE:
                   checker = new Invariance();
                   break;
               case PERSISTENCE:
                   checker = new Persistence();
                   break;
               default:
                    logger.error("Something gone bad and none of checker was chosen!");

           }
           checker.setLogicSentence(logicSentence);
           outcomes.add(checker.checkPattern(dataMap));
        }

        for (int i = 0; i < outcomes.size(); i++) {
            if (!outcomes.get(i)) {
                logger.debug("Process analyzeList, returned with false. First false logic sentence = {}", outcomes.get(i));
                return false;
            }
        }

        logger.info("Finished analyzing sentence list!");
        return true;
    }

    // FIXME: jesli zalezne od trybu analizatora, zmienic na metode abstrakcyjna
    public boolean isReady() {
        if (dataMap != null && dataHeaders != null /* && analyzerWorkType != null */) {
            // TODO Dodać żeby sprawdzał te kolekcje też w głąb
            return true;
        } else {
            return false;
        }
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public void setLogicSentences(List<LogicSentence> logicSentences) {
        this.logicSentences = logicSentences;
    }

    public Map<String, ArrayList<String>> getDataMap() {
        return dataMap;
    }

    public List<String> getDataHeaders() {
        return dataHeaders;
    }
}

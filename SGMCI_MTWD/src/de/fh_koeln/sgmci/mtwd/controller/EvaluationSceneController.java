package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.model.Comment;
import de.fh_koeln.sgmci.mtwd.model.Critic;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import de.fh_koeln.sgmci.mtwd.model.Problem;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used as the controller for the EvaluationScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific to
 * the EvaluationScene.
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class EvaluationSceneController extends AbstractMTWDSceneController {

    public EvaluationSceneController(IScene observer) {
        super(observer);
    }

    public void saveResultsIntoXmlFile() {
        Writer writer = null;
        try {
            writer = new FileWriter(String.format("mtwd_results_%d.xml", new Date().getTime()));
            writer.write("<?xml version='1.0' encoding='UTF-8' ?>\n");
            writer.write("<mtwd_results>\n");
            writer.flush();
            for (Problem problem : getApplication().getAllProblems()) {
                writer.write(String.format("    <problem id='%s' description='%s'>\n", problem.getId(), problem.getDescription()));
                for (Idea idea : problem.getAllIdeas()) {
                    writer.write(String.format("        <idea id='%s' description='%s' visible='%s' realist_likes=%d realist_dislikes=%d criticer_likes=%d criticer_dislikes=%d>\n", idea.getId(), idea.getDescription(), idea.isStillDisplayed(), idea.getTotalRealistLikes(), idea.getTotalRealistDislikes(), idea.getTotalCriticerLikes(), idea.getTotalCriticerDislikes()));
                    for (Comment comment : idea.getAllComments()) {
                        writer.write(String.format("            <comment id='%s' description='%s' />\n", comment.getId(), comment.getDescription()));
                        writer.flush();
                    }
                    for (Critic critic : idea.getAllCritics()) {
                        writer.write(String.format("            <critic id='%s' description='%s' />\n", critic.getId(), critic.getDescription()));
                        writer.flush();
                    }
                    writer.write("        </idea>\n");
                    writer.flush();
                }
                writer.write("    </problem>\n");
                writer.flush();
            }
            writer.write("</mtwd_results>");
            writer.flush();
            writer.close();
        } catch (IOException ioex) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(EvaluationSceneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

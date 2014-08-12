import com.intellij.ide.actions.NewProjectAction;
import com.intellij.ide.impl.NewProjectUtil;
import com.intellij.ide.projectWizard.NewProjectWizard;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;

/**
 * Created by user on 11.08.14.
 */
public class KotPlay extends NewProjectAction {

    public void actionPerformed(AnActionEvent e) {
        NewProjectWizard wizard = new NewProjectWizard(null, ModulesProvider.EMPTY_MODULES_PROVIDER, null);
        NewProjectUtil.createNewProject(getEventProject(e), wizard);

    }
}
